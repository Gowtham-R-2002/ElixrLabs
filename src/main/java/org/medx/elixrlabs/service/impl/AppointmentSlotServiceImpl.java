//package org.medx.elixrlabs.service.impl;
//
//import org.medx.elixrlabs.dto.AvailableSlotsDto;
//import org.medx.elixrlabs.model.AppointmentSlot;
//import org.medx.elixrlabs.repository.AppointmentSlotRepository;
//import org.medx.elixrlabs.service.AppointmentSlotService;
//import org.medx.elixrlabs.service.SampleCollectorService;
//import org.medx.elixrlabs.util.LocationEnum;
//import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
//import org.medx.elixrlabs.util.TimeSlotEnum;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class AppointmentSlotServiceImpl implements AppointmentSlotService {
//
//    @Autowired
//    private AppointmentSlotRepository appointmentSlotRepository;
//
//    @Autowired
//    private SampleCollectorService sampleCollectorService;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Override
//    public List<AvailableSlotsDto> getAvailableSlots(LocationEnum location, LocalDate date, TestCollectionPlaceEnum testCollectionPlace) {
//        List<AppointmentSlot> appointments = appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlot(location, testCollectionPlace, date);
//
//        List<String> bookedTimeSlots = appointments.stream().map(AppointmentSlot::getTimeSlot).toList();
//        Map<TimeSlotEnum, Boolean> availableSlots = new HashMap<>();
//        Arrays.stream(TimeSlotEnum.values()).map(timeSlot -> availableSlots.put(timeSlot, Collections.frequency(bookedTimeSlots, timeSlot.getTime()) < ));
//
//        return List.of();
//    }
//}
