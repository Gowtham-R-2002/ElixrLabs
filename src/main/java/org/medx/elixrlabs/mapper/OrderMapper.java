package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.model.Order;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class OrderMapper {
    public static OrderSuccessDto toOrderSuccessDto(Order order) {
        return OrderSuccessDto.builder()
                .id(order.getId())
                .dateTime(LocalDateTime.now(ZoneId.of("GMT+05:30")))
                .build();
    }

    public static ResponseOrderDto toResponseOrderDto(Order order) {
        return ResponseOrderDto.builder()
                .id(order.getId())
                .tests(order.getTests().stream().map(LabTestMapper::toRetrieveLabTestDto).toList())
                .testPackageDto(order.getTestPackage() == null ? null : TestPackageMapper.toTestPackageDto(order.getTestPackage()))
                .testStatus(order.getTestStatus())
                .build();
    }
}
