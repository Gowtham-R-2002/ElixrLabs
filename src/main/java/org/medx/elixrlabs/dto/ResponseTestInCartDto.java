package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Represents a test item included in a cart.</p>
 *
 * <p>Contains details about the test such as its identifier, name, description, and price.</p>
 *
 * @author Gowtham R
 */
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
