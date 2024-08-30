package org.medx.elixrlabs.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTestResultDto {
    @NotNull
    List<RequestTestIdWithResultDto> testIdWithResult;
}
