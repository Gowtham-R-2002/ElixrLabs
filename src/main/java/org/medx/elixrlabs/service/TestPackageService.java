package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * <p>
 * Interface for TestPackageService, defining the business operations related to TestPackage.
 * This interface is implemented by TestPackageServiceImpl and defines the contract for
 * managing TestPackage entities.
 * </p>
 */
@Service
public interface TestPackageService {

    /**
     * Creates or updates a TestPackage.
     *
     * @param testPackageDto {@link TestPackageDto} The DTO containing testPackage data.
     * @return The created testPackage Dto.
     */
    ResponseTestPackageDto createOrUpdateTest(TestPackageDto testPackageDto);

    /**
     * Retrieves all TestPackages.
     *
     * @return A list of testPackage DTOs.
     */
    List<ResponseTestPackageDto> getAllTestPackages();

    /**
     * Retrieves an testPackage by ID.
     *
     * @param id {@link Long} The ID of the testPackage.
     * @return The testPackage DTO.
     * @throws NoSuchElementException if the testPackage is not found.
     */
    ResponseTestPackageDto getTestPackageById(long id);

    /**
     * Deletes an testPackage by ID (soft deletion).
     *
     * @param id {@link Long} The ID of the testPackage to delete.
     * @throws RuntimeException if the testPackage is not found.
     */
    boolean deleteTestPackageById(long id);
}
