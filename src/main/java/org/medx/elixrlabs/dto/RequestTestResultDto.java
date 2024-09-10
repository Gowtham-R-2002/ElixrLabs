package org.medx.elixrlabs.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>Data transfer object for managing test results associated with test IDs.</p>
 *
 * <p>This class encapsulates a list of test ID and result pairs.</p>
 *
 * @author Gowtham R
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTestResultDto {
    @NotNull
    private List<RequestTestIdWithResultDto> testIdsWithResults;
}
