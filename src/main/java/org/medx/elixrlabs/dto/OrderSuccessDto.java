package org.medx.elixrlabs.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 * <p>Encapsulates details of a successful order placement.</p>
 *
 * <p>This class includes the unique identifier and timestamp of the successful order.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
public class OrderSuccessDto {
    @NotNull
    private long id;

    @NotNull
    private LocalDateTime dateTime;
}
