package org.medx.elixrlabs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.OrderRepository;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestStatusEnum;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private AppointmentSlot appointmentSlot;
    private LocationEnum location;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = User.builder()
                .place(LocationEnum.GUINDY)
                .build();
        Patient patient = Patient.builder()
                .user(user)
                .build();
        LabTest test = LabTest.builder()
                .id(1L)
                .name("BLOOD TEST")
                .build();
        order = Order.builder()
                .id(1L)
                .labLocation(LocationEnum.GUINDY)
                .sampleCollectionPlace(TestCollectionPlaceEnum.HOME)
                .testStatus(TestStatusEnum.PENDING)
                .tests(List.of(test))
                .patient(patient)
                .build();
        appointmentSlot = new AppointmentSlot();
        location = LocationEnum.GUINDY;
    }

    @Test
    void createOrUpdateOrder_success() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderSuccessDto result = orderService.createOrUpdateOrder(order);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void createOrUpdateOrder_failure() {
        when(orderRepository.save(any(Order.class))).thenThrow(new LabException("Error while creating order"));

        assertThrows(LabException.class, () -> orderService.createOrUpdateOrder(order));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void getOrder_success() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1L);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrder_failure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LabException.class, () -> orderService.getOrder(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrder_exception() {
        when(orderRepository.findById(anyLong())).thenThrow(new LabException("Error while fetching order"));

        assertThrows(LabException.class, () -> orderService.getOrder(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void updateOrderStatus_success() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.updateOrderStatus(1L);

        assertEquals(TestStatusEnum.COMPLETED, order.getTestStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void updateOrderStatus_failure() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LabException.class, () -> orderService.updateOrderStatus(1L));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void updateOrderStatus_exception() {
        when(orderRepository.findById(anyLong())).thenThrow(new LabException("Error while fetching order"));

        assertThrows(LabException.class, () -> orderService.updateOrderStatus(1L));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getOrdersByLocation_success() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepository.findByLabLocation(any(LocationEnum.class))).thenReturn(orders);

        List<ResponseOrderDto> result = orderService.getOrdersByLocation(location);

        assertNotNull(result);
        verify(orderRepository, times(1)).findByLabLocation(location);
    }

    @Test
    void getOrdersByLocation_failure() {
        when(orderRepository.findByLabLocation(any(LocationEnum.class))).thenReturn(new ArrayList<>());

        List<ResponseOrderDto> result = orderService.getOrdersByLocation(location);

        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findByLabLocation(location);
    }

    @Test
    void getOrdersByLocation_exception() {
        when(orderRepository.findByLabLocation(any(LocationEnum.class))).thenThrow(new LabException("Error while fetching orders"));

        assertThrows(LabException.class, () -> orderService.getOrdersByLocation(location));
        verify(orderRepository, times(1)).findByLabLocation(location);
    }

    @Test
    void getOrderByAppointment_success() {
        when(orderRepository.findBySlot(any(AppointmentSlot.class))).thenReturn(order);

        Order result = orderService.getOrderByAppointment(appointmentSlot);

        assertNotNull(result);
        verify(orderRepository, times(1)).findBySlot(appointmentSlot);
    }

    @Test
    void getOrderByAppointment_failure() {
        when(orderRepository.findBySlot(any(AppointmentSlot.class))).thenReturn(null);

        Order result = orderService.getOrderByAppointment(appointmentSlot);

        assertNull(result);
        verify(orderRepository, times(1)).findBySlot(appointmentSlot);
    }

    @Test
    void getOrderByAppointment_exception() {
        when(orderRepository.findBySlot(any(AppointmentSlot.class))).thenThrow(new LabException("Error while fetching order by appointment"));

        assertThrows(LabException.class, () -> orderService.getOrderByAppointment(appointmentSlot));
        verify(orderRepository, times(1)).findBySlot(appointmentSlot);
    }
}
