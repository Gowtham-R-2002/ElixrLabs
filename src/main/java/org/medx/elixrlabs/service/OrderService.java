package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();

    Order getOrder();

    void updateOrderStatus();
}
