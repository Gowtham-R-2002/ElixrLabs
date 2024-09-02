package org.medx.elixrlabs.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import io.github.cdimascio.dotenv.Dotenv;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.mapper.AppointmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.exception.SlotException;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.PaymentStatusEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.medx.elixrlabs.util.TimeSlotEnum;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentSlotServiceImpl.class);
    private static Dotenv dotenv;

    public AppointmentSlotServiceImpl() {
        dotenv = Dotenv.load();
    }

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

    @Autowired
    private EmailService emailService;

    @Override
    public Set<String> getAvailableSlots(RequestSlotBookDto slotBookDto) {
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
                                    : Integer.parseInt(dotenv.get("${LAB_SLOT_COUNT}")))).collect(Collectors.toSet());
            logger.info("Available slots fetched successfully for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
            return availableSlots.stream().map(TimeSlotEnum::getTime).collect(Collectors.toSet());
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching available slots for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
            throw new LabException("Unable to fetch available slots", e);
        }
    }

    private boolean isSlotAvailable(SlotBookDto slotBookDto) {
        try {
            logger.debug("Checking slot availability for time slot: {}", slotBookDto.getTimeSlot());
            boolean isAvailable = getAvailableSlots(RequestSlotBookDto.builder()
                    .date(slotBookDto.getDate())
                    .location(slotBookDto.getLocation())
                    .testCollectionPlace(slotBookDto.getTestCollectionPlace())
                    .build()).contains(slotBookDto.getTimeSlot());
            logger.info("Slot availability checked for time slot: {} - Available: {}", slotBookDto.getTimeSlot(), isAvailable);
            return isAvailable;
        } catch (Exception e) {
            logger.warn("Exception occurred while checking slot availability for time slot: {}", slotBookDto.getTimeSlot());
            throw new LabException("Unable to check slot availability",e );
        }
    }

    @Override
    public OrderSuccessDto bookSlot(SlotBookDto slotBookDto) {
        try {
            logger.debug("Attempting to book slot for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            if (isSlotAvailable(slotBookDto)) {
                Patient patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
                ResponseCartDto cart = cartService.getCartByPatient();
                if(cart.getId() == 0 && cart.getTests().isEmpty() && cart.getTestPackage() == null) {
                    throw new NoSuchElementException("Cart is empty!");
                }
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
                        .price(cart.getPrice())
                        .orderDateTime(Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("GMT+05:30"))))
                        .build();
                emailService.sendInvoice(order.getTests(), order.getTestPackage(), order.getPrice(), patient.getUser().getEmail(), order.getOrderDateTime());
                appointmentSlotRepository.save(appointmentSlot);
                cartService.deleteCart();
                logger.info("Slot booked successfully for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
                return orderService.createOrUpdateOrder(order);
            } else {
                logger.warn("Slot booking failed for date: {}, time slot: {} - Slot filled", slotBookDto.getDate(), slotBookDto.getTimeSlot());
                throw new SlotException("Slot filled!");
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while booking slot for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            throw new SlotException("Unable to book slot", e);
        }
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPlace(LocationEnum location, LocalDate date) {
        try {
            logger.debug("Fetching appointments for location: {}, date: {}", location, date);
            List<AppointmentSlot> appointments = appointmentSlotRepository
                    .findByLocationAndTestCollectionPlaceAndDateSlot(location, TestCollectionPlaceEnum.HOME, date);
            logger.info("Appointments fetched successfully for location: {}, date: {}", location, date);
            return appointments.stream().map(AppointmentMapper::convertToDto).toList();
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching appointments for location: {}, date: {}", location, date);
            throw new LabException("Unable to fetch appointments", e);
        }
    }
    @Override
    public void assignSampleCollectorToAppointment(Long id) {
        try {
            logger.debug("Assigning sample collector to appointment with id: {}", id);
            AppointmentSlot appointmentSlot = appointmentSlotRepository.findById(id)
                    .orElseThrow(() -> new LabException("No appointment slot found with id: " + id));
            SampleCollector sampleCollector = sampleCollectorService.getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext());
            appointmentSlot.setSampleCollector(sampleCollector);
            appointmentSlotRepository.save(appointmentSlot);
            logger.info("Sample collector assigned successfully to appointment with id: {}", id);
        } catch (Exception e) {
            logger.warn("Exception occurred while assigning sample collector to appointment with id: {}", id);
            throw new LabException("Unable to assign sample collector", e);
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
            throw new LabException("Unable to mark sample as collected", e);
        }
    }

    @Override
    public List<AppointmentDto> getAppointmentsBySampleCollector() {
        String userName = SecurityContextHelper
                .extractEmailFromContext();
        try {
            logger.debug("Fetching all appointments for SampleCollector : {}", userName);
            SampleCollector sampleCollector = sampleCollectorService
                    .getSampleCollectorByEmail(userName);
            List<AppointmentSlot> appointmentSlots = appointmentSlotRepository.findBySampleCollectorId(sampleCollector.getId());
            if (appointmentSlots == null) {
                logger.warn("There is no appointments for SampleCollector : {}", userName);
                throw new NoSuchElementException("No AppointmentSlot Found for Sample Collector : " + userName);
            } else {
                logger.info("Appointments fetched successfully for SampleCollector: {}", userName);
                return appointmentSlots.stream()
                        .map(AppointmentMapper :: convertToDto).toList();
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching all appointments for SampleCollector: {}", userName);
            throw new LabException("Exception occurred while fetching all appointments for SampleCollector : " + userName, e);
        }
    }

    @Override
    public List<AppointmentDto> getCollectedAppointmentsBySampleCollector() {
        String userName = SecurityContextHelper
                .extractEmailFromContext();
        try {
            SampleCollector sampleCollector = sampleCollectorService.getSampleCollectorByEmail(userName);
            logger.debug("Fetching all appointments for which the sample is collected by the sampleCollector with ID: {}", sampleCollector.getId());
            List<AppointmentSlot> appointmentSlots = appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedTrue(sampleCollector.getId());
            if (appointmentSlots == null) {
                logger.warn("There is no appointments for sample collected by the SampleCollector with Id: {}", sampleCollector.getId());
                throw new NoSuchElementException("No AppointmentSlot Found for ID: " + sampleCollector.getId());
            } else {
                logger.info("Fetching all appointments for which the sample is collected by the sample collector with ID: {}", sampleCollector.getId());
                return appointmentSlots.stream()
                        .map(AppointmentMapper :: convertToDto).toList();
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching all appointments for which the sample is collected by the sampleCollector: {}", userName);
            throw new LabException("Exception occurred while fetching all appointments for which the sample is collected by the SampleCollector: " + userName, e);
        }
    }

    @Override
    public List<AppointmentDto> getPendingAppointmentsBySampleCollector() {
        String userName = SecurityContextHelper
                .extractEmailFromContext();
        try {
            SampleCollector sampleCollector = sampleCollectorService.getSampleCollectorByEmail(userName);
            logger.debug("Fetching all appointments for which the sample is not collected by the sample collector with ID: {}", sampleCollector.getId());
            List<AppointmentSlot> appointmentSlots = appointmentSlotRepository.findBySampleCollectorIdAndIsSampleCollectedFalse(sampleCollector.getId());
            if (appointmentSlots == null) {
                logger.warn("There is no appointments for sample is not collected by the SampleCollector with Id: {}", sampleCollector.getId());
                throw new NoSuchElementException("No AppointmentSlot Found for ID: " + sampleCollector.getId());
            } else {
                logger.info("Fetching all appointments for which the sample is not collected by the sample collector with ID: {}", sampleCollector.getId());
                return appointmentSlots.stream()
                        .map(AppointmentMapper :: convertToDto).toList();
            }
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching all appointments for which the sample is not collected by the SampleCollector : {}", userName);
            throw new LabException("Exception occurred while fetching all appointments for which the sample is collected by the SampleCollector : " + userName, e);
        }
    }

}
