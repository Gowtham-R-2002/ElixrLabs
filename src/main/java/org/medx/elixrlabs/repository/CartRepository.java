package org.medx.elixrlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.medx.elixrlabs.model.Cart;
import org.medx.elixrlabs.model.Patient;

/**
 * Repository interface for accessing cart data from the database.
 *
 * <p>
 * This interface extends JpaRepository, providing CRUD operations for Role entities.
 * Custom queries can be defined by adding method signatures that follow the naming
 * convention understood by Spring Data JPA.
 * </p>
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("FROM Cart cart JOIN FETCH cart.patient")
    Cart findCartByUser(Patient patient);
}
