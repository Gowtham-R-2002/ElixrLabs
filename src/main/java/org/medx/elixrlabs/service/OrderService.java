package org.medx.elixrlabs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p>
 * Interface for OrderService, defining the business operations related to orders.
 * This interface is implemented by OrderServiceImpl and defines the contract for
 * managing order entities.
 * </p>
 */

@Service
public interface OrderService {

    /**
     * Creates or updates an order that has been requested by the patient
     *
     * @param order {@link Order} order details that has to be created or updated
     * @return order that has been placed
     */

    OrderSuccessDto createOrUpdateOrder(Order order);

    /**
     * Fetches all the orders
     *
     * @return list of all orders
     */

    List<Order> getOrders();

    /**
     * Fetches a specific order
     *
     * @param id id of the order to be fetched
     * @return order that has to be fetched
     */

    Order getOrder(Long id);

    /**
     * Updates the current status of an order to TestStatusEnum_COMPLETED
     *
     * @param id id of the order whose status has to be updated
     */

    void updateOrderStatus(Long id);

    /**
     * Fetches all the orders from a specific location
     *
     * @param location location from which orders has to be fetched
     * @return list of all orders from the specific location
     */

    List<ResponseOrderDto> getOrdersByLocation(LocationEnum location);

    /**
     * fetches the order of the specific appointment slot
     *
     * @param appointmentSlot appointment slot for which order has to be fetched
     * @return order of the specific appointment slot
     */

    Order getOrderByAppointment(AppointmentSlot appointmentSlot);
}
