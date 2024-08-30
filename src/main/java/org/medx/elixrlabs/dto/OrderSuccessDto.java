package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderSuccessDto {
    @NotNull
    private long id;
    @NotNull
    private LocalDateTime dateTime;
}
