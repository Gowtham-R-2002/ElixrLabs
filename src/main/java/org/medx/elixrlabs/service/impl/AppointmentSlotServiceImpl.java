package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;
import org.medx.elixrlabs.service.*;
import org.medx.elixrlabs.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

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
        return availableSlots.stream().map(TimeSlotEnum::getTime).collect(Collectors.toSet());
    }

    @Override
    public boolean isSlotAvailable(SlotBookDto slotBookDto) {
        return getAvailableSlots(slotBookDto).contains(slotBookDto.getTimeSlot());
    }

    @Override
    public OrderSuccessDto bookSlot(SlotBookDto slotBookDto) {
        Order order = null;
        if (isSlotAvailable(slotBookDto)) {
            User patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
            ResponseCartDto cart = cartService.getCartByPatient();
            AppointmentSlot appointmentSlot = AppointmentSlot.builder()
                    .dateSlot(slotBookDto.getDate())
                    .user(patient)
                    .timeSlot(slotBookDto.getTimeSlot())
                    .location(slotBookDto.getLocation())
                    .testCollectionPlace(slotBookDto.getTestCollectionPlace())
                    .build();
            order = Order.builder()
                    .slot(appointmentSlot)
                    .tests(cart.getTests().stream().map(LabTestMapper::toLabTest).collect(Collectors.toList()))
                    .paymentStatus(PaymentStatusEnum.PAID)
                    .user(patient)
                    .sampleCollectionPlace(slotBookDto.getTestCollectionPlace())
                    .labLocation(slotBookDto.getLocation())
                    .testPackage(cart.getTestPackage())
                    .testStatus(TestStatusEnum.PENDING)
                    .build();
            appointmentSlotRepository.save(appointmentSlot);
            cartService.deleteCart();
        } else {
            throw new LabException("Slot filled!");
        }
        return orderService.createOrder(order);
    }

    @Override
    public List<AppointmentSlot> getAppointmentsByPlace(LocationEnum location, LocalDate date) {
        return appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlot(location, TestCollectionPlaceEnum.LAB, date);
    }

    @Override
    public AppointmentSlot createOrUpdateAppointment(AppointmentSlot appointmentSlot) {
        return appointmentSlotRepository.save(appointmentSlot);
    }


}
