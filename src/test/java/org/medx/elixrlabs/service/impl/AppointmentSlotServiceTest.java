package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.repository.AppointmentSlotRepository;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AppointmentSlotServiceTest {

    @Mock
    private AppointmentSlotRepository appointmentSlotRepository;

    @Mock
    private SampleCollectorService sampleCollectorService;

    @Mock
    private CartService cartService;

    @Mock
    private PatientService patientService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private AppointmentSlotServiceImpl appointmentSlotService;

    private SlotBookDto slotBookDto;
    private AppointmentSlot slot;
    private List<AppointmentSlot> slots;

    @BeforeEach
    void setUp() {

    }


    @Test
    void testIsSlotAvailable() {

    }

    @Test
    void testBookSlot() {

    }

    @Test
    void testGetAppointmentsByPlace() {

    }

    @Test
    void testCreateOrUpdateAppointment() {

    }

    @Test
    void testAssignSampleCollectorsToAppointment() {

    }

    @Test
    void testMarkSampleCollected() {

    }

    @Test
    void testGetAppointmentsBySampleCollector() {

    }

    @Test
    void testGetCollectedAppointmentsBySampleCollector() {

    }

    @Test
    void testGetPendingAppointmentsBySampleCollector() {

    }
}
