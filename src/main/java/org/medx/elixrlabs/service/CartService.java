package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    ResponseCartDto addTestsOrPackagesToCart(CartDto cartDto);

    ResponseCartDto getCartByPatient();

    ResponseCartDto removeTestsOrPackageFromCart(CartDto cartDto);
}
