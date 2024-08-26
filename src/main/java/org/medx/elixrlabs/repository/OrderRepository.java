package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPlace(LocationEnum place);

    List<Order> findByEmail(String email);
}
