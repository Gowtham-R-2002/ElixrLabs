package org.medx.elixrlabs.dto;

import lombok.Builder;

import org.medx.elixrlabs.util.LocationEnum;

@Builder
public class OrderLocationDto {

    private Long id;
    private LocationEnum labLocation;
}
