package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .place(user.getPlace())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .place(userDto.getPlace())
                .gender(userDto.getGender())
                .phoneNumber(userDto.getPhoneNumber())
                .dateOfBirth(userDto.getDateOfBirth())
                .build();
    }
}
