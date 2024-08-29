package org.medx.elixrlabs.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultDto {

    private Long id;
    private Long orderId;
    private String email;
    private String result;
    private LocalDateTime generatedAt;
}
