package org.medx.elixrlabs.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.RequestTestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;

/**
 * <p> Interface for TestPackageService, defining the business operations related to TestPackage.
 * This interface is implemented by TestPackageServiceImpl and defines the contract for
 * managing TestPackage entities. </p>
 *
 * @author Deolin Jaffens
 */
@Service
public interface TestPackageService {

    /**
     * Creates or updates a TestPackage.
     *
     * @param requestTestPackageDto {@link RequestTestPackageDto} The DTO containing testPackage data.
     * @return The created testPackage Dto.
     */
    ResponseTestPackageDto createOrUpdateTestPackage(RequestTestPackageDto requestTestPackageDto);

    /**
     * Retrieves all TestPackages.
     *
     * @return list of all testPackages.
     */
    List<ResponseTestPackageDto> getAllTestPackages();

    /**
     * Retrieves an testPackage by ID.
     *
     * @param id {@link Long} ID of the testPackage.
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
