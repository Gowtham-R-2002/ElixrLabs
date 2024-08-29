package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.Cart;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("FROM Cart cart JOIN FETCH cart.patient")
    Cart findCartByUser(Patient patient);
}
