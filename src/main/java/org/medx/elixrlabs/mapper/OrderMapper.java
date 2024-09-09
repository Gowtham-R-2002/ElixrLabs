package org.medx.elixrlabs.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.model.Order;

/**
 * Mapper class for mapping DTOs and entity related to order.
 *
 * <p>
 * This class provides static methods for converting between User entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */

public class OrderMapper {

    /**
     * Converts an {@link Order} entity to an {@link OrderSuccessDto}.
     *
     * @param order {@link Order} The order entity to be converted.
     * @return {@link OrderSuccessDto} The corresponding DTO of order.
     */

    public static OrderSuccessDto toOrderSuccessDto(Order order) {
        return OrderSuccessDto.builder()
                .id(order.getId())
                .dateTime(LocalDateTime.now(ZoneId.of("GMT+05:30")))
                .build();
    }

    /**
     * Converts an {@link Order} entity to an {@link ResponseOrderDto}.
     *
     * @param order {@link Order} The order entity to be converted.
     * @return {@link ResponseOrderDto} The corresponding DTO of order.
     */

    public static ResponseOrderDto toResponseOrderDto(Order order) {
        return ResponseOrderDto.builder()
                .id(order.getId())
                .tests(order.getTests().stream().map(LabTestMapper::toResponseTestDto).toList())
                .testPackageDto(order.getTestPackage() == null ? null : TestPackageMapper.toTestPackageDto(order.getTestPackage()))
                .testStatus(order.getTestStatus())
                .build();
    }
}
