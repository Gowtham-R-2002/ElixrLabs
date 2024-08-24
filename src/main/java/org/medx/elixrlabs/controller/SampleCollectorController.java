package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing SampleCollector-related operations.
 *
 * <p>
 * This controller handles HTTP requests and provides endpoints for
 * creating, retrieving, updating, and deleting SampleCollector entities. The
 * controller maps client requests to the appropriate service methods
 * and returns responses in the form of JSON or other supported media types.
 * It is annotated with Spring MVC annotations to define the URL mappings
 * and request handling logic.
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
    @PostMapping
    public ResponseEntity<UserDto> createSampleCollector(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.createSampleCollector(userDto), HttpStatus.CREATED);
    }

    /**
     * Retrieves a list of all sample collector.
     *
     * @return the list of all sample collectors as sample collector DTOs with HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllSampleCollector() {
        List<UserDto> userDtos = sampleCollectorService.getAllSampleCollector();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    /**
     * Retrieves an sample collector by their unique ID.
     *
     * @param id the unique sample collector ID
     * @return the sample collector DTO if found with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSampleCollectorById(@PathVariable Long id) {
        return new ResponseEntity<>(sampleCollectorService.getSampleCollectorById(id), HttpStatus.OK);
    }

    /**
     * Updates an existing sample collector's details by their unique ID.
     *
     * @param userDto {@link UserDto} The DTO containing sample collector data.
     * @return the updated sample collector DTO with HTTP status 202 ACCEPTED.
     */
    @PutMapping
    public ResponseEntity<UserDto> updateSampleCollector(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.updateSampleCollector(userDto), HttpStatus.ACCEPTED);
    }

    /**
     * Deletes a sample collector by their unique ID.
     *
     * @param id the unique employee ID
     * @return boolean value with HTTP status 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSampleCollector(Long id) {
        return new ResponseEntity<>(sampleCollectorService.deleteSampleCollector(id), HttpStatus.NO_CONTENT);
    }
}
