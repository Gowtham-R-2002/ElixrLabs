package org.medx.elixrlabs.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.dto.RequestTestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.service.TestPackageService;

/**
 * <p>Handles operations related to test packages, including creation, retrieval,
 * updating, and deletion of test package records. Provides endpoints for managing
 * test package data.</p>
 *
 * @author Deolin Jaffens
 */
@RestController
@RequestMapping("api/v1/test-packages")
public class TestPackageController {

    @Autowired
    private TestPackageService testPackageService;

    /**
     * <p>Creates a new test package with the provided details.</p>
     *
     * @param requestTestPackageDto Contains the data for the test package to be created or updated.
     * @return The created or updated {@link ResponseTestPackageDto}.
     */
    @PostMapping
    public ResponseEntity<ResponseTestPackageDto> createTestPackage(@Valid @RequestBody RequestTestPackageDto requestTestPackageDto) {
        return new ResponseEntity<>(testPackageService.createOrUpdateTestPackage(requestTestPackageDto), HttpStatus.CREATED);
    }

    /**
     * <p>Retrieves a list of all test packages.</p>
     *
     * @return A list of {@link ResponseTestPackageDto} representing all test packages.
     */
    @GetMapping
    public ResponseEntity<List<ResponseTestPackageDto>> getAllTestPackages() {
        return new ResponseEntity<>(testPackageService.getAllTestPackages(), HttpStatus.OK);
    }

    /**
     * <p>Retrieves a specific test package identified by its ID.</p>
     *
     * @param id The unique identifier of the test package to be retrieved.
     * @return The {@link ResponseTestPackageDto} associated with the specified ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTestPackageDto> getTestPackageById(@PathVariable long id) {
        return new ResponseEntity<>(testPackageService.getTestPackageById(id), HttpStatus.OK);
    }

    /**
     * <p>Updates an existing test package with the provided details.</p>
     *
     * @param requestTestPackageDto Contains the updated data for the test package.
     * @return The updated {@link ResponseTestPackageDto}.
     */
    @PutMapping
    public ResponseEntity<ResponseTestPackageDto> updateTestPackageById(@Valid @RequestBody RequestTestPackageDto requestTestPackageDto) {
        return new ResponseEntity<>(testPackageService.createOrUpdateTestPackage(requestTestPackageDto), HttpStatus.ACCEPTED);
    }

    /**
     * <p>Deletes a specific test package identified by its ID.</p>
     *
     * @param id The unique identifier of the test package to be deleted.
     * @return A boolean indicating whether the deletion was successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeTestPackageById(@PathVariable long id) {
        return new ResponseEntity<>(testPackageService.deleteTestPackageById(id), HttpStatus.OK);
    }
}
