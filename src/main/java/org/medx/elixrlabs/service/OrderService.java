package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.OrderDto;
import org.medx.elixrlabs.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDto createOrder(Order order);

    List<Order> getOrders();

    Order getOrder();

    void updateOrderStatus();
}
