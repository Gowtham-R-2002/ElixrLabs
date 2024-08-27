package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TimeSlotEnum;
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
    private JwtService jwtService;

    @Override
    public Set<TimeSlotEnum> getAvailableSlots(LocationEnum location, LocalDate date, TestCollectionPlaceEnum testCollectionPlace) {
        List<AppointmentSlot> appointments = appointmentSlotRepository.findByLocationAndTestCollectionPlaceAndDateSlot(location, testCollectionPlace, date);

        List<String> bookedTimeSlots = new ArrayList<>(appointments.stream().map(AppointmentSlot::getTimeSlot).toList());
        bookedTimeSlots.add("TWELVE_PM");
        bookedTimeSlots.add("TWELVE_PM");
        bookedTimeSlots.add("TWELVE_PM");
        bookedTimeSlots.add("TWELVE_PM");
        Set<TimeSlotEnum> availableSlots = Arrays.stream(TimeSlotEnum.values())
                .filter(timeSlotEnum -> Collections.frequency(bookedTimeSlots, timeSlotEnum.name()) < sampleCollectorService.getSampleCollectorByPlace(location).size())
                        .collect(Collectors.toSet());
        System.out.println(availableSlots.stream().map(TimeSlotEnum::getTime).collect(Collectors.toSet()));
        return availableSlots;
    }
}
