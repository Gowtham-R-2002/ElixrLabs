package org.medx.elixrlabs.mapper;

import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.model.User;

/**
 * Mapper class for mapping between User and UserDto.
 *
 * <p>
 * This class provides static methods for converting between User entities
 * and their corresponding Data Transfer Objects (DTOs). It facilitates the
 * conversion process needed for interacting with the service and controller layers
 * while keeping the domain model and DTOs separate.
 * </p>
 */
public class UserMapper {

    /**
     * Converts an {@link User} entity to an {@link UserDto}.
     *
     * @param user {@link User} The User entity to be converted.
     * @return {@link UserDto} The corresponding User DTO.
     */
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

    /**
     * Converts an {@link UserDto} to an {@link User} entity.
     *
     * @param userDto {@link UserDto} The User DTO to be converted.
     * @return {@link User} The corresponding User entity.
     */
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
