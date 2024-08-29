package org.medx.elixrlabs.service;

import java.util.List;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.stereotype.Service;

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
     * Creates a new order that has been requested by the patient
     *
     * @param order {@link Order} order details that has
     * @return order that has been placed
     */

    OrderSuccessDto createOrder(Order order);

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
     * Updates the current status of an order to completed
     *
     * @param id id of the order whose status has to be updated
     */

    void updateOrderStatus(Long id);

    /**
     * Fetches all the orders from a specific location
     *
     * @param location location from which orders has to fetched
     * @return list of all orders from the specific location
     */

    List<ResponseOrderDto> getOrdersByLocation(LocationEnum location);
}
