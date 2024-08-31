package org.medx.elixrlabs.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.service.LabTestService;
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

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.service.LabTestService;

/**
 * REST controller for managing LabTest-related operations.
 *
 * <p>
 * This controller handles HTTP requests and provides endpoints for
 * creating, retrieving, updating, and deleting LabTest entities. The
 * controller maps client requests to the appropriate service methods
 * and returns responses in the form of JSON or other supported media types.
 * It is annotated with Spring MVC annotations to define the URL mappings
 * and request handling logic.
 * All responses are returned in a standardized format to ensure consistency across
 * the API.
 * </p>
 */
@RestController
@RequestMapping("api/v1/lab-tests")
public class LabTestController {

    @Autowired
    private LabTestService labTestService;

    @PostMapping
    public ResponseEntity<LabTestDto> createLabTest(@Valid @RequestBody LabTestDto labTestDto) {
        return new ResponseEntity<>(labTestService.createOrUpdateTest(labTestDto),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LabTestDto>> getAllLabTests() {
        return new ResponseEntity<>(labTestService.getAllLabTests(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTestDto> getLabTestById(@PathVariable long id) {
        return new ResponseEntity<>(labTestService.getLabTestById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LabTestDto> updateLabTestById(@Valid @RequestBody LabTestDto labTestDto) {
        return new ResponseEntity<>(labTestService.createOrUpdateTest(labTestDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeLabTestById(@PathVariable long id) {
        return new ResponseEntity<>(labTestService.removeLabTestById(id), HttpStatus.OK);
    }
}
