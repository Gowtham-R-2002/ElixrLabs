package org.medx.elixrlabs.service;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;

/**
 * <p>
 * Interface for CartService, manages all the operations that are related to the
 * cart module. This interface is implemented by CartServiceImpl
 * </p>
 */

@Service
public interface CartService {

    /**
     * Adds and removes test or test package in a specific patient's cart
     *
     * @param cartDto contains details of test and test packages to be added to
     *                the cart
     * @return cart in which tests or test package is added
     */

    ResponseCartDto addTestsOrPackagesToCart(CartDto cartDto);

    /**
     * Fetches the cart that has been related to a specific patient
     *
     * @return cart of a specific patient
     */

    ResponseCartDto getCartByPatient();

    /**
     * Removes the cart associated with the patient
     */

    void deleteCart();
}
