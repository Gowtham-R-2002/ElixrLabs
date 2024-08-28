package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.OrderLocationDto;
import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderSuccessDto createOrder(Order order);

    List<Order> getOrders();

    Order getOrder(Long id);

    void updateOrderStatus(Long id);

    List<OrderLocationDto> getOrdersByLocation(LocationEnum location);
}
