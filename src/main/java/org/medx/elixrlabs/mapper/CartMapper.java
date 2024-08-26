package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {

    public static Cart toCart(CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.getId())
                .build();
    }

    public static ResponseCartDto toCartDto(Cart cart) {
        cart.setTests(new ArrayList<>());
        List<LabTestDto> tests = cart.getTests()
                .stream()
                .map(LabTestMapper::toRetrieveLabTestDto).toList();
        return ResponseCartDto.builder()
                .id(cart.getId())
                .tests(tests)
                .testPackage(cart.getTestPackage())
                .user(cart.getPatient())
                .build();
    }
}
