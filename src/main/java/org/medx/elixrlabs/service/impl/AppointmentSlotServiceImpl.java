package org.medx.elixrlabs.service.impl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.PaymentStatusEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.medx.elixrlabs.util.TimeSlotEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentSlotServiceImpl.class);

    @Autowired
    private AppointmentSlotRepository appointmentSlotRepository;

    @Autowired
    private SampleCollectorService sampleCollectorService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private OrderService orderService;

    @Override
    public Set<String> getAvailableSlots(SlotBookDto slotBookDto) {
        try {
            logger.debug("Fetching available slots for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
            List<AppointmentSlot> appointments = appointmentSlotRepository
                    .findByLocationAndTestCollectionPlaceAndDateSlot(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate());
            List<String> bookedTimeSlots = appointments.stream()
                    .map(AppointmentSlot::getTimeSlot).toList();
            Set<TimeSlotEnum> availableSlots = Arrays.stream(TimeSlotEnum.values())
                    .filter(timeSlotEnum ->
                            Collections.frequency(bookedTimeSlots, timeSlotEnum.getTime())
                                    < (slotBookDto.getTestCollectionPlace().equals(TestCollectionPlaceEnum.HOME)
                                    ? sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation()).size()
                                    : 5)).collect(Collectors.toSet());
            logger.info("Available slots fetched successfully for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
            return availableSlots.stream().map(TimeSlotEnum::getTime).collect(Collectors.toSet());
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching available slots for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
            throw new LabException("Unable to fetch available slots");
        }
    }

    @Override
    public boolean isSlotAvailable(SlotBookDto slotBookDto) {
        try {
            logger.debug("Checking slot availability for time slot: {}", slotBookDto.getTimeSlot());
            boolean isAvailable = getAvailableSlots(slotBookDto).contains(slotBookDto.getTimeSlot());
            logger.info("Slot availability checked for time slot: {} - Available: {}", slotBookDto.getTimeSlot(), isAvailable);
            return isAvailable;
        } catch (Exception e) {
            logger.warn("Exception occurred while checking slot availability for time slot: {}", slotBookDto.getTimeSlot());
            throw new LabException("Unable to check slot availability");
        }
    }

    @Override
    public OrderSuccessDto bookSlot(SlotBookDto slotBookDto) {
        try {
            logger.debug("Attempting to book slot for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            if (isSlotAvailable(slotBookDto)) {
                Patient patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
                ResponseCartDto cart = cartService.getCartByPatient();
                AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                        .dateSlot(slotBookDto.getDate())
                        .patient(patient)
                        .timeSlot(slotBookDto.getTimeSlot())
                        .location(slotBookDto.getLocation())
                        .testCollectionPlace(slotBookDto.getTestCollectionPlace())
                        .build();
                Order order = Order.builder()
                        .slot(appointmentSlot)
                        .tests(cart.getTests().stream().map(LabTestMapper::toLabTest).collect(Collectors.toList()))
                        .paymentStatus(PaymentStatusEnum.PAID)
                        .patient(patient)
                        .sampleCollectionPlace(slotBookDto.getTestCollectionPlace())
                        .labLocation(slotBookDto.getLocation())
                        .testPackage(cart.getTestPackage())
                        .testStatus(TestStatusEnum.PENDING)
                        .build();
                appointmentSlotRepository.save(appointmentSlot);
                cartService.deleteCart();
                logger.info("Slot booked successfully for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
                return orderService.createOrUpdateOrder(order);
            } else {
                logger.warn("Slot booking failed for date: {}, time slot: {} - Slot filled", slotBookDto.getDate(), slotBookDto.getTimeSlot());
                throw new LabException("Slot filled!");
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while booking slot for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            throw new LabException("Unable to book slot");
        }
    }

    @Override
    public List<AppointmentSlot> getAppointmentsByPlace(LocationEnum location, LocalDate date) {
        try {
            logger.debug("Fetching appointments for location: {}, date: {}", location, date);
            List<AppointmentSlot> appointments = appointmentSlotRepository
                    .findByLocationAndTestCollectionPlaceAndDateSlot(location, TestCollectionPlaceEnum.HOME, date);
            logger.info("Appointments fetched successfully for location: {}, date: {}", location, date);
            return appointments;
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching appointments for location: {}, date: {}", location, date);
            throw new LabException("Unable to fetch appointments");
        }
    }

    @Override
    public AppointmentSlot createOrUpdateAppointment(AppointmentSlot appointmentSlot) {
        try {
            logger.debug("Creating or updating appointment for date: {}, time slot: {}", appointmentSlot.getDateSlot(), appointmentSlot.getTimeSlot());
            AppointmentSlot savedAppointmentSlot = appointmentSlotRepository.save(appointmentSlot);
            logger.info("Appointment created or updated successfully for date: {}, time slot: {}", appointmentSlot.getDateSlot(), appointmentSlot.getTimeSlot());
            return savedAppointmentSlot;
        } catch (Exception e) {
            logger.warn("Exception occurred while creating or updating appointment for date: {}, time slot: {}", appointmentSlot.getDateSlot(), appointmentSlot.getTimeSlot());
            throw new LabException("Unable to create or update appointment");
        }
    }

    @Override
    public void assignSampleCollectorToAppointment(Long id, SampleCollector sampleCollector) {
        try {
            logger.debug("Assigning sample collector to appointment with id: {}", id);
            AppointmentSlot appointmentSlot = appointmentSlotRepository.findById(id)
                    .orElseThrow(() -> new LabException("No appointment slot found with id: " + id));
            appointmentSlot.setSampleCollector(sampleCollector);
            appointmentSlotRepository.save(appointmentSlot);
            logger.info("Sample collector assigned successfully to appointment with id: {}", id);
        } catch (Exception e) {
            logger.warn("Exception occurred while assigning sample collector to appointment with id: {}", id);
            throw new LabException("Unable to assign sample collector");
        }
    }

    @Override
    public void markSampleCollected(Long id) {
            logger.debug("Marking sample as collected for appointment with id: {}", id);
            AppointmentSlot appointmentSlot = appointmentSlotRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("No appointment slot found with id: " + id));
            appointmentSlot.setSampleCollected(true);
            Order order = orderService.getOrderByAppointment(appointmentSlot);
            order.setTestStatus(TestStatusEnum.IN_PROGRESS);
            orderService.createOrUpdateOrder(order);
        try {
            appointmentSlotRepository.save(appointmentSlot);
            logger.info("Sample marked as collected successfully for appointment with id: {}", id);
        } catch (Exception e) {
            logger.warn("Exception occurred while marking sample as collected for appointment with id: {}", id);
            throw new LabException("Unable to mark sample as collected");
        }
    }

    @Override
    public List<AppointmentSlot> getAppointmentsBySampleCollector(Long id) {
        try {
            logger.debug("Fetching all appointments for SampleCollector Id: {}", id);
            List<AppointmentSlot> appointmentSlots = appointmentSlotRepository.findBySampleCollectorId(id);
            if(appointmentSlots == null) {
                logger.warn("There is no appointments for SampleCollector Id: {}", id);
                return null;
            } else {
                logger.info("Appointments fetched successfully for SampleCollector Id: {}", id);
                return appointmentSlots;
            }
        } catch (Exception e) {

            logger.warn("Exception occurred while fetching all appointments for SampleCollector Id: {}", id);
            throw new LabException("Exception occurred while fetching all appointments for SampleCollector Id: " + id);
        }
    }

    @Override
    public List<AppointmentSlot> getCollectedAppointmentsBySampleCollector(Long id) {
        try {
            logger.debug("Fetching all appointments for which the sample is collected by the sampleCollector with ID: {}", id);
            List<AppointmentSlot> appointmentSlots = appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedTrue(id);
            if(appointmentSlots == null) {
                logger.warn("There is no appointments for sample collected by the SampleCollector with Id: {}", id);
                return null;
            } else {
                logger.info("Fetching all appointments for which the sample is collected by the sample collector with ID: {}", id);
                return appointmentSlots;
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching all appointments for which the sample is collected by the sampleCollector ID: {}", id);
            throw new LabException("Exception occurred while fetching all appointments for which the sample is collected by the SampleCollector ID: " + id);
        }
    }

    @Override
    public List<AppointmentSlot> getPendingAppointmentsBySampleCollector(Long id) {
        try {
            logger.debug("Fetching all appointments for which the sample is not collected by the sample collector with ID: {}", id);
            List<AppointmentSlot> appointmentSlots = appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedTrue(id);
            if(appointmentSlots == null) {
                logger.warn("There is no appointments for sample is not collected by the SampleCollector with Id: {}", id);
                return null;
            } else {
                logger.info("Fetching all appointments for which the sample is not collected by the sample collector with ID: {}", id);
                return appointmentSlots;
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching all appointments for which the sample is not collected by the SampleCollector ID: {}", id);
            throw new LabException("Exception occurred while fetching all appointments for which the sample is collected by the SampleCollector ID: " + id);
        }
    }

}
