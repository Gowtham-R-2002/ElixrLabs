package org.medx.elixrlabs.controller;

import java.util.List;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.service.SampleCollectorService;
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

/**
 * REST controller for managing SampleCollector-related operations.
 *
 * <p>
 * This controller handles HTTP requests and provides endpoints for
 * creating, retrieving, updating, and deleting SampleCollector entities.
 * All responses are returned in a standardized format to ensure consistency across
 * the API.
 * </p>
 */
@RestController
@RequestMapping("api/v1/sample-collectors")
public class SampleCollectorController {

    @Autowired
    private SampleCollectorService sampleCollectorService;

    /**
     * Creates a new sample collector.
     *
     * @param userDto {@link UserDto} The DTO containing employee data.
     * @return The created sample collector DTO with HTTP status 201 CREATED.
     */
    @PostMapping("register")
    public ResponseEntity<SampleCollectorDto> createSampleCollector(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.createOrUpdateSampleCollector(userDto), HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all sample collector.
     *
     * @return the list of all sample collectors as sample collector DTOs with HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<SampleCollectorDto>> getAllSampleCollector() {
        List<SampleCollectorDto> SampleCollectorDtos = sampleCollectorService.getAllSampleCollector();
        return new ResponseEntity<>(SampleCollectorDtos, HttpStatus.OK);
    }

    /**
     * Retrieves an sample collector by their unique ID.
     *
     * @param id The unique sample collector ID
     * @return The sample collector DTO if found with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SampleCollectorDto> getSampleCollectorById(@PathVariable Long id) {
        return new ResponseEntity<>(sampleCollectorService.getSampleCollectorById(id), HttpStatus.OK);
    }

    /**
     * Updates an existing sample collector's details by their unique ID.
     *
     * @param userDto {@link UserDto} The DTO containing sample collector data.
     * @return the updated sample collector DTO with HTTP status 202 ACCEPTED.
     */
    @PutMapping
    public ResponseEntity<SampleCollectorDto> updateSampleCollector(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.createOrUpdateSampleCollector(userDto), HttpStatus.ACCEPTED);
    }

    /**
     * Deletes a sample collector by their unique ID.
     *
     * @return boolean value with HTTP status 204 NO CONTENT.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteSampleCollector() {
        return new ResponseEntity<>(sampleCollectorService.deleteSampleCollector(), HttpStatus.NO_CONTENT);
    }
}
