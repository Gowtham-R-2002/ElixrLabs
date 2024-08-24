package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public List<Order> getOrders() {
        return List.of();
    }

    @Override
    public Order getOrder() {
        return null;
    }

    @Override
    public void updateOrderStatus() {

    }
}
