package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.OrderLocationDto;
import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.repository.OrderRepository;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderSuccessDto createOrder(Order order) {
        return OrderMapper.toOrderSuccessDto(orderRepository.save(order));
    }

    @Override
    public List<Order> getOrders() {
        return List.of();
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public void updateOrderStatus(Long id) {
        Order order = getOrderById(id);
        order.setTestStatus(TestStatusEnum.COMPLETED);
        orderRepository.save(order);
    }

    private Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<OrderLocationDto> getOrdersByLocation(LocationEnum location) {
        return orderRepository.findByLabLocation(location)
                .stream()
                .map(order ->
                        OrderLocationDto.builder()
                                .id(order.getId())
                                .labLocation(order.getLabLocation())
                                .build()).toList();
    }
}
