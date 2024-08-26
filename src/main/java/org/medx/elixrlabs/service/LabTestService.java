package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>
 * Interface for LabTestService, defining the business operations related to LabTest.
 * This interface is implemented by LabTestServiceImpl and defines the contract for
 * managing LabTest entities.
 * </p>
 */
@Service
public interface LabTestService {

    /**
     * Creates or updates a Lab Test.
     *
     * @param labTestDto {@link LabTestDto} The DTO containing Test data.
     * @return The created LabTestDto Dto.
     */
    LabTestDto createOrUpdateTest(LabTestDto labTestDto);

    /**
     * Retrieves all LabTestDto.
     *
     * @return A list of LabTest DTOs.
     */
    List<LabTestDto> getAllLabTests();

    /**
     * Retrieves an LabTest by ID.
     *
     * @param id The ID of the LabTest.
     * @return The LabTest DTO.
     * @throws NoSuchElementException if the LabTest is not found.
     */
    LabTestDto getLabTestById(long id);

    /**
     * Deletes an LabTest by user id (soft deletion).
     *
     * @param id The ID of the LabTest to delete.
     * @throws RuntimeException if the LabTest is not found.
     */
    boolean removeLabTestById(long id);

    /**
     * Loads the initial test data
     */
    void setupInitialData();
}
