package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestResultDto {

    private Long id;
    private Long orderId;
    private String email;
    private String result;
    private LocalDateTime generatedAt;
}
