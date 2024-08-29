package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.springframework.stereotype.Service;

/**
 * Interface for CartService, manages all the operations that are related to the
 * cart module. This interface is implemented by CartServiceImpl
 */

@Service
public interface CartService {

    /**
     * Adds and removes test or test package in a specific cart
     *
     * @param cartDto contains details of test and test packages to be added to
     *                the cart
     * @return details that has been added to the cart
     */

    ResponseCartDto addTestsOrPackagesToCart(CartDto cartDto);

    /**
     * Fetches the cart that has been related to a specific patient
     *
     * @return details that are present in the cart
     */

    ResponseCartDto getCartByPatient();

    /**
     * Removes all the details that are associated with the cart
     */

    void deleteCart();
}
