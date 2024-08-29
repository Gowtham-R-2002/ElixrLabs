package org.medx.elixrlabs.mapper;

import java.util.List;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.model.Cart;

/**
 * Mapper class for mapping DTOs and entity related to cart.
 *
 * <p>
 * This class provides static methods for converting between User entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */

public class CartMapper {

    /**
     * Converts an {@link Cart} entity to an {@link ResponseCartDto}.
     *
     * @param cart {@link Cart} The cart entity to be converted.
     * @return {@link ResponseCartDto} The corresponding DTO of cart..
     */

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
