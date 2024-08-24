package org.medx.elixrlabs.service.implementation;

import org.medx.elixrlabs.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SampleCollectorService {

    UserDto createSampleCollector(UserDto userDto);

    List<UserDto> getAllSampleCollector();

    UserDto getSampleCollectorById(Long id);

    UserDto updateSampleCollector(UserDto userDto);

    boolean deleteSampleCollector(Long id);
}
