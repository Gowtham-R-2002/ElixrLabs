package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.GenderEnum;

import java.time.LocalDate;

/**
 * <p>
 * This class is responsible for transferring user data as Data Transfer Object (DTO)
 * between layers.
 * This class is used to encapsulate the data related to an user and
 * transfer it between the client and the server. DTOs are typically used to
 * decouple the internal representation of data (employee entity class) from
 * the API, providing a simpler and more controlled way to expose data.
 * </p>
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private GenderEnum gender;
    private LocationEnum place;
}
