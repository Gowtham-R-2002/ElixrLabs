package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    ResponseCartDto addTestsOrPackagesToCart(CartDto cartDto);

    ResponseCartDto getCartByPatient();

    void deleteCart();
}
