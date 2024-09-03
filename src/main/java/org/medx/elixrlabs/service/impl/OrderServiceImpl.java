package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.repository.OrderRepository;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestStatusEnum;

/**
 * <p>
 * Service implementation for managing Order-related operations.
 * This class contains business logic for handling Order entities, including
 * creation, retrieval, update, and location-based retrieval operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderSuccessDto createOrUpdateOrder(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            logger.info("Order created successfully with id: {}", savedOrder.getId());
            return OrderMapper.toOrderSuccessDto(savedOrder);
        } catch (Exception e) {
            logger.warn("Error while creating order: {}", order.getId());
            throw new LabException("Error while creating order: " , e);
        }
    }

    @Override
    public Order getOrder(Long id) {
        Order order;
        try {
            order = orderRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.warn("Error while getting order with id: {}", id);
            throw new LabException("Error while fetching order with id: " + id, e);
        }
        return order;
    }

    @Override
    public void updateOrderStatus(Long id) {
        try {
            Order order = getOrderById(id);
            if (order != null) {
                order.setTestStatus(TestStatusEnum.COMPLETED);
                orderRepository.save(order);
                logger.info("Order status updated to COMPLETED for id: {}", id);
            } else {
                logger.warn("Order not found with id: {}", id);
                throw new NoSuchElementException("Order not found with id: " + id);
            }
        } catch (Exception e) {
            logger.warn("Error while updating order status with id: {}", id);
            throw new LabException("Error while updating order status with id: " + id, e);
        }
    }

    private Order getOrderById(Long id) {
        try {
            return orderRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.warn("Error while fetching order with id: {}", id);
            throw new LabException("Error while fetching order with id: " + id, e);
        }
    }

    @Override
    public List<ResponseOrderDto> getOrdersByLocation(LocationEnum location) {
        try {
            List<ResponseOrderDto> orderLocationDtos = orderRepository.findByLabLocation(location)
                    .stream()
                    .map(OrderMapper::toResponseOrderDto).toList();
            logger.info("Fetched {} orders by location: {}", orderLocationDtos.size(), location);
            return orderLocationDtos;
        } catch (Exception e) {
            logger.warn("Error while fetching orders by location: {}", location);
            throw new LabException("Error while fetching orders by location: " + location, e);
        }
    }

    @Override
    public Order getOrderByAppointment(AppointmentSlot appointmentSlot) {
        return orderRepository.findBySlot(appointmentSlot);
    }
}
