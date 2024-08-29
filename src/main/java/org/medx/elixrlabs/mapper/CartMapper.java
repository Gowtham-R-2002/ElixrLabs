package org.medx.elixrlabs.mapper;

import java.util.List;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.model.Cart;

public class CartMapper {

//    public static Cart toCart(CartDto cartDto) {
//        return Cart.builder()
//                .testPackage(cartDto.getTestPackageId())
//                .build();
//    }

    public static ResponseCartDto toCartDto(Cart cart) {
        List<LabTestDto> tests = cart.getTests()
                .stream()
                .map(LabTestMapper::toRetrieveLabTestDto).toList();
        return ResponseCartDto.builder()
                .id(cart.getId())
                .tests(tests)
                .testPackage(cart.getTestPackage())
                .build();
    }
}
