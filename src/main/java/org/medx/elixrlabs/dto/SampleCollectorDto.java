package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.model.User;

/**
 * <p>
 * This class is responsible for transferring Employee data as Data Transfer Object (DTO)
 * between layers.
 * This class is used to encapsulate the data related to an Employee and
 * transfer it between the client and the server. DTOs are typically used to
 * decouple the internal representation of data (employee entity class) from
 * the API, providing a simpler and more controlled way to expose data.
 * </p>
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleCollectorDto {
    private Long id;
    private User user;
    private boolean isVerified;

}
