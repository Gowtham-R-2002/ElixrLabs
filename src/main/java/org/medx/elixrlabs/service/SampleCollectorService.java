package org.medx.elixrlabs.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p> Interface for SampleCollectorService, defining the business operations related to SampleCollector.
 * This interface is implemented by SampleCollectorServiceImpl and defines the contract for
 * managing SampleCollector entities. </p>
 */
@Service
public interface SampleCollectorService {

    /**
     * Creates a SampleCollector with the given data.
     *
     * @param userDto {@link UserDto} The DTO containing SampleCollector data.
     * @return The created SampleCollector Dto.
     */
    SampleCollectorDto createSampleCollector(UserDto userDto);

    /**
     * Updates a SampleCollector with the updated data.
     *
     * @param userDto {@link UserDto} The DTO containing SampleCollector data.
     * @return The updated SampleCollector Dto.
     */
    SampleCollectorDto updateSampleCollector(UserDto userDto);

    /**
     * Retrieves all the verified SampleCollector.
     *
     * @return A list of SampleCollector DTOs.
     */
    List<SampleCollectorDto> getSampleCollectors();

    /**
     * Deletes an SampleCollector by user id (soft deletion).
     *
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

    /**
     * Fetches all the verified sample collectors from a specific place
     *
     * @param place location of the sample collectors associated with a specific place
     * @return list of sample collectors associated with the location
     */
    List<SampleCollector> getSampleCollectorByPlace(LocationEnum place);

    /**
     * Changes the verification status of a specific sample collector
     *
     * @param email - email of the sample collector who has to be verified
     */
    void verifySampleCollector(String email);

    /**
     * Fetches all the sample collectors
     *
     * @return list of all the sample collectors
     */
    List<SampleCollectorDto> getAllSampleCollectors();

}
