package org.medx.elixrlabs.repository;

import java.util.List;

import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing order data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for Role entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByLabLocation(LocationEnum place);

    Order findByAppointmentSlot(AppointmentSlot appointmentSlot);
}
