package org.medx.elixrlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.medx.elixrlabs.util.AddressEnum;
import org.medx.elixrlabs.util.GenderEnum;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAndLoginUserDto {
    private String email;
    private String password;
    private LocalDate dob;
    private String phoneNumber;
    private GenderEnum gender;
    private AddressEnum place;
}
