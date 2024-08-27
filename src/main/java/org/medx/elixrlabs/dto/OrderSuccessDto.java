package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderSuccessDto {
    private long id;
    private LocalDateTime dateTime;
}
