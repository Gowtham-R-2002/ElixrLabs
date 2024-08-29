package org.medx.elixrlabs.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestResultDto {
    private LocalDate orderDate;
    private Long id;
    private String ageAndGender;
    private String email;
    private List<String> result;
    private LocalDateTime generatedAt;
}
