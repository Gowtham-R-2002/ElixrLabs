package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.User;
import org.springframework.stereotype.Service;

@Service
public class SampleCollectorServiceImpl {

    public UserDto createSampleCollector(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        SampleCollector sampleCollector = SampleCollector.builder()
                .user(user)
                .isVerified(false)
                .build();
    }

}
