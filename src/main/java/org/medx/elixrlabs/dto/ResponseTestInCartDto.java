package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTestInCartDto {
    private Long id;
    private String name;
    private String description;
    private double price;
}
