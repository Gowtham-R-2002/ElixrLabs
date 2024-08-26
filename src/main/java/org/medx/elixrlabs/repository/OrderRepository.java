package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
