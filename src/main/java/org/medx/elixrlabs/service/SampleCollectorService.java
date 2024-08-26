package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.SampleCollector;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>
 * Interface for SampleCollectorService, defining the business operations related to SampleCollector.
 * This interface is implemented by SampleCollectorServiceImpl and defines the contract for
 * managing SampleCollector entities.
 * </p>
 */
@Service
public interface SampleCollectorService {

    /**
     * Creates or updates a SampleCollector.
     *
     * @param userDto {@link UserDto} The DTO containing User data.
     * @return The created SampleCollector Dto.
     */
    SampleCollectorDto createOrUpdateSampleCollector(UserDto userDto);

    /**
     * Retrieves all SampleCollector.
     *
     * @return A list of SampleCollector DTOs.
     */
    List<SampleCollectorDto> getAllSampleCollector();

    /**
     * Retrieves an SampleCollector by ID.
     *
     * @param id {@link Long} The ID of the SampleCollector.
     * @return The SampleCollector DTO.
     * @throws NoSuchElementException if the SampleCollector is not found.
     */
    SampleCollectorDto getSampleCollectorById(Long id);

    /**
     * Deletes an SampleCollector by user id (soft deletion).
     *
     * @param userDto {@link UserDto} The ID of the SampleCollector to delete.
     * @throws RuntimeException if the SampleCollector is not found.
     */
    boolean deleteSampleCollector();

    /**
     * Retrieves an SampleCollector by email.
     *
     * @param email {@link String} The ID of the SampleCollector.
     * @return The SampleCollector DTO.
     * @throws NoSuchElementException if the SampleCollector is not found.
     */
    SampleCollector getSampleCollectorByEmail(String email);

}
