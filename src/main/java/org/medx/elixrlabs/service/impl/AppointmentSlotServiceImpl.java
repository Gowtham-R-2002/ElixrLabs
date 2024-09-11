package org.medx.elixrlabs.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.RequestSlotBookDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.exception.AntecedentDateException;
import org.medx.elixrlabs.exception.SlotException;
import org.medx.elixrlabs.mapper.AppointmentMapper;
import org.medx.elixrlabs.mapper.TestPackageMapper;
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

/**
 * <p>
 * Service implementation for managing AppointmentSlotService-related operations.
 * This {@code AppointmentSlotServiceImpl} contains business logic for handling AppointmentSlot
 * operations. It acts as a bridge between the controller layer and the repository
 * layer, ensuring that business rules are applied before interacting with the database.
 * </p>
 */
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
        if (slotBookDto.getDate().isBefore(LocalDate.now())) {
            logger.warn("Date must be today's date or upcoming dates !");
            throw new AntecedentDateException("Date must be today's date or upcoming dates !");
        }
        logger.debug("Fetching available slots for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
        try {
            List<AppointmentSlot> appointments = appointmentSlotRepository
                    .findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(slotBookDto.getLocation(), slotBookDto.getTestCollectionPlace(), slotBookDto.getDate());
            List<String> bookedTimeSlots = appointments.stream()
                    .map(AppointmentSlot::getTimeSlot).toList();
            Set<TimeSlotEnum> availableSlots = Arrays.stream(TimeSlotEnum.values())
                    .filter(timeSlotEnum ->
                            Collections.frequency(bookedTimeSlots, timeSlotEnum.getTime())
                                    < (slotBookDto.getTestCollectionPlace().equals(TestCollectionPlaceEnum.HOME)
                                    ? sampleCollectorService.getSampleCollectorByPlace(slotBookDto.getLocation()).size()
                                    : Integer.parseInt(dotenv.get("LAB_SLOT_COUNT")))).collect(Collectors.toSet());
            logger.info("Available slots fetched successfully for location: {}, date: {}", slotBookDto.getLocation(), slotBookDto.getDate());
            if(!LocalDate.now().equals(slotBookDto.getDate())) {
                return availableSlots.stream().map(TimeSlotEnum::getTime).collect(Collectors.toSet());
            }
            return filterTime(availableSlots.stream().map(TimeSlotEnum::getTime).collect(Collectors.toSet()));
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
            throw new LabException("Unable to check slot availability", e);
        }
    }

    @Override
    public OrderSuccessDto bookSlot(SlotBookDto slotBookDto) {
        OrderSuccessDto orderSuccessDto;
        logger.debug("Attempting to book slot for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
        if (!isSlotAvailable(slotBookDto)) {
            logger.warn("Slot booking failed for date: {}, time slot: {} - Slot filled", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            throw new SlotException("Slot filled!");
        }
        Patient patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        ResponseCartDto cart = cartService.getCartByPatient();
        if (cart.getTests().isEmpty() && cart.getTestPackage() == null) {
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
                .testPackage(cart.getTestPackage() == null ? null : TestPackageMapper.toTestPackageFromResponseDto(cart.getTestPackage()))
                .testStatus(TestStatusEnum.PENDING)
                .price(cart.getPrice())
                .address(slotBookDto.getAddress())
                .orderDateTime(Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("GMT+05:30"))))
                .build();
        try {
            emailService.sendInvoice(order.getTests(), order.getTestPackage(), order.getPrice(), patient.getUser().getEmail(), order.getOrderDateTime());
            AppointmentSlot savedAppointment = appointmentSlotRepository.save(appointmentSlot);
            logger.info("Slot booked successfully for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            orderSuccessDto = orderService.createOrUpdateOrder(order);
            cartService.deleteCart();
            savedAppointment.setOrder(orderService.getOrder(orderSuccessDto.getId()));
            appointmentSlotRepository.save(appointmentSlot);
        } catch (Exception e) {
            logger.error("Exception occurred while booking slot for date: {}, time slot: {}", slotBookDto.getDate(), slotBookDto.getTimeSlot());
            throw new SlotException("Unable to book slot", e);
        }
        return orderSuccessDto;
    }

    @Override
    public List<AppointmentDto> getAppointmentsByPlace(LocationEnum location, LocalDate date) {
        try {
            logger.debug("Fetching appointments for location: {}, date: {}", location, date);
            List<AppointmentSlot> appointments = appointmentSlotRepository
                    .findByLocationAndTestCollectionPlaceAndDateSlotAndSampleCollectorNull(location, TestCollectionPlaceEnum.HOME, date);
            logger.info("Appointments fetched successfully for location: {}, date: {}", location, date);
            return appointments.stream().map(AppointmentMapper::convertToDto).toList();
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching appointments for location: {}, date: {}", location, date);
            throw new LabException("Unable to fetch appointments", e);
        }
    }

    @Override
    public void assignSampleCollectorToAppointment(Long id) {
        logger.debug("Assigning sample collector to appointment with id: {}", id);
        SampleCollector sampleCollector = sampleCollectorService.getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext());
        AppointmentSlot appointmentSlot = appointmentSlotRepository.findByIdAndTestCollectionPlace(id, TestCollectionPlaceEnum.HOME)
                .orElseThrow(() -> new NoSuchElementException("No appointment slot found with id: " + id));
        if (appointmentSlot.getSampleCollector() != null) {
            logger.warn("Appointment already assigned to another sample collcetor");
            throw new NoSuchElementException("Appointment already assigned to another sample collcetor");
        }
        appointmentSlot.setSampleCollector(sampleCollector);
        try {
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
        if(!isSampleCanBeMarked(appointmentSlot.getDateSlot(), appointmentSlot.getTimeSlot())) {
            logger.warn("Cannot mark appointment as collected before the appointment time!");
            throw new AntecedentDateException("Cannot mark appointment as collected before the appointment time!");
        }
        if (!appointmentSlot.getSampleCollector().equals(sampleCollectorService.getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext()))) {
            throw new NoSuchElementException("No appointment slot found with id: " + id);
        }
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
                logger.error("There is no appointments for SampleCollector : {}", userName);
                throw new NoSuchElementException("No AppointmentSlot Found for Sample Collector : " + userName);
            }
            logger.info("Appointments fetched successfully for SampleCollector: {}", userName);
            return appointmentSlots.stream()
                    .map(AppointmentMapper::convertToDto).toList();

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
            }
            logger.info("Fetching all appointments for which the sample is collected by the sample collector with ID: {}", sampleCollector.getId());
            return appointmentSlots.stream()
                    .map(AppointmentMapper::convertToDto).toList();
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
            }
            logger.info("Fetching all appointments for which the sample is not collected by the sample collector with ID: {}", sampleCollector.getId());
            return appointmentSlots.stream()
                    .map(AppointmentMapper::convertToDto).toList();
        } catch (Exception e) {
            logger.warn("Exception occurred while fetching all appointments for which the sample is not collected by the SampleCollector : {}", userName);
            throw new LabException("Exception occurred while fetching all appointments for which the sample is collected by the SampleCollector : " + userName, e);
        }
    }

    /**
     * <p>
     * Filters available time slots based on the current time and meridian (AM/PM).
     * If the current time is in the PM and exactly 12:00 PM, only PM slots after 12:00 PM
     * are considered. If the current time is in the AM, both AM slots and PM slots starting
     * from 12:00 PM are considered.
     * </p>
     *
     * @param availableSlots the set of available time slots.
     * @return a filtered set of available time slots.
     */
    private Set<String> filterTime(Set<String> availableSlots) {
        logger.debug("Starting to filter available time slots based on the current time.");
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String currentTime = dateFormat.format(new java.util.Date());
        String meridian = currentTime.substring(currentTime.length() - 2, currentTime.length()).toUpperCase();
        if (meridian.equals("PM") && currentTime.substring(0, 2).equals("12")) {
            return filterByMeridian(availableSlots, meridian, "00:00 MM");
        } else if (meridian.equals("PM")) {
            return filterByMeridian(availableSlots, meridian, currentTime);
        }
        Set<String> newAvailableSlots = filterByMeridian(availableSlots, meridian, currentTime);
        newAvailableSlots.addAll(filterByMeridian(availableSlots, "PM", "00:00 MM"));
        newAvailableSlots.add("12PM");
        logger.info("Filtered slots after merging AM and PM slots: {}", newAvailableSlots);
        return newAvailableSlots;
    }

    /**
     * <p>
     * Filters available time slots based on the specified meridian and current time.
     * This method filters time slots based on the given meridian (AM/PM) and ensures that
     * only times greater than the current time are included in the resulting set.
     * </p>
     *
     * @param availableSlots the set of available time slots.
     * @param meridian the meridian (AM/PM) to filter the slots by.
     * @param currentTime the current time to compare against.
     * @return a filtered set of available time slots.
     */
    private Set<String> filterByMeridian(Set<String> availableSlots, String meridian, String currentTime) {
        logger.debug("Filtering available slots by meridian: {} and current time: {}", meridian, currentTime);
        availableSlots = availableSlots.stream().filter(time -> time.contains(meridian)).collect(Collectors.toSet());
        int currentHour = Integer.parseInt(currentTime.substring(0, 2));
        availableSlots = availableSlots.stream().filter(time -> {
            String[] splittedTime = time.split(Character.toString(meridian.charAt(0)));
            int incomingTime = Integer.parseInt(splittedTime[0]);
            if (incomingTime == 12 && meridian.equals("PM")) return false;
            return incomingTime > currentHour;
        }).collect(Collectors.toSet());
        logger.info("Filtered slots after comparison: {}", availableSlots);
        return availableSlots;
    }

    /**
     * <p>
     * Checks whether the sample can be marked based on the appointment date and time slot.
     * The method compares the current time with the given appointment time slot and
     * determines whether the sample can be marked based on if the appointment time has passed.
     * </p>
     *
     * @param dateSlot the appointment date.
     * @param timeSlot the time slot of the appointment.
     * @return {@code true} if the sample can be marked, {@code false} otherwise.
     */
    private boolean isSampleCanBeMarked(LocalDate dateSlot, String timeSlot) {
        logger.debug("Checking whether the sample can be marked for the date: {} and time slot: {}", dateSlot, timeSlot);
        LocalDate date = LocalDate.now();
        int hours = 0;
        if (timeSlot.equals("12PM")) {
            hours = 12;
        } else if (timeSlot.substring(timeSlot.length() - 2, timeSlot.length()).equals("PM")) {
            hours = Integer.parseInt(Character.toString(timeSlot.charAt(0))) + 12;
        } else {
            String[] splitTime = timeSlot.split("A");
            hours = Integer.parseInt(splitTime[0]);
        }
        LocalDateTime appointmentDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hours, 0);
        logger.debug("Appointment date and time: {}", appointmentDateTime);
        return appointmentDateTime.isBefore(LocalDateTime.now());
    }
}
