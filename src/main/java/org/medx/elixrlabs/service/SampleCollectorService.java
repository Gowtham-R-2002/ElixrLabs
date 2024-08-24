package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.SampleCollector;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Interface for SampleCollectorService, defining the business operations related to SampleCollector.
 * This interface is implemented by SampleCollectorServiceImpl and defines the contract for
 * managing SampleCollector entities.
 * </p>
 */
@Service
public interface SampleCollectorService {

    SampleCollectorDto createOrUpdateSampleCollector(UserDto userDto);

    List<SampleCollectorDto> getAllSampleCollector();

    SampleCollectorDto getSampleCollectorById(Long id);

    boolean deleteSampleCollector(UserDto userDto);

    SampleCollectorDto getSampleCollectorByEmail(String email);

}
