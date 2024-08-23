package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.RegisterAndLoginUserDto;
import org.medx.elixrlabs.model.User;

public class UserMapper {
    public static RegisterAndLoginUserDto toUserDto(User user) {
        return RegisterAndLoginUserDto.builder()
                .email(user.getEmail())
                .dob(user.getDateOfBirth())
                .place(user.getPlace())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .build();
    }

    public static User toUser(RegisterAndLoginUserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .place(userDto.getPlace())
                .gender(userDto.getGender())
                .phoneNumber(userDto.getPhoneNumber())
                .dateOfBirth(userDto.getDob())
                .build();
    }
}
