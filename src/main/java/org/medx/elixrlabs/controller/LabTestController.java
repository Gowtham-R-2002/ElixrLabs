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

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.service.LabTestService;
/**
 * <p>Handles operations related to lab tests, including creation, retrieval,
 * updating, and deletion of lab test records. Provides endpoints for managing
 * lab test data.</p>
 *
 * @author Deolin Jaffens
 */

@RestController
@RequestMapping("api/v1/lab-tests")
public class LabTestController {

    @Autowired
    private LabTestService labTestService;

    /**
     * <p>Creates a new lab test record with the provided details.</p>
     *
     * @param labTestDto Contains the data for the lab test to be created or updated.
     * @return The created or updated lab test record.
     */

    @PostMapping
    public ResponseEntity<LabTestDto> createLabTest(@Valid @RequestBody LabTestDto labTestDto) {
        return new ResponseEntity<>(labTestService.createOrUpdateTest(labTestDto),HttpStatus.CREATED);
    }

    /**
     * <p>Retrieves a list of all lab test records.</p>
     *
     * @return A list of all lab test records.
     */
    @GetMapping
    public ResponseEntity<List<LabTestDto>> getAllLabTests() {
        return new ResponseEntity<>(labTestService.getAllLabTests(), HttpStatus.OK);
    }

    /**
     * <p>Retrieves a specific lab test record identified by its ID.</p>
     *
     * @param id The unique identifier of the lab test to be retrieved.
     * @return The lab test record associated with the specified ID.
     */

    @GetMapping("/{id}")
    public ResponseEntity<LabTestDto> getLabTestById(@PathVariable long id) {
        return new ResponseEntity<>(labTestService.getLabTestById(id), HttpStatus.OK);
    }

    /**
     * <p>Updates an existing lab test record with the provided details.</p>
     *
     * @param labTestDto Contains the updated data for the lab test.
     * @return The updated lab test record.
     */

    @PutMapping
    public ResponseEntity<LabTestDto> updateLabTestById(@Valid @RequestBody LabTestDto labTestDto) {
        return new ResponseEntity<>(labTestService.createOrUpdateTest(labTestDto), HttpStatus.ACCEPTED);
    }

    /**
     * <p>Deletes a specific lab test record identified by its ID.</p>
     *
     * @param id The unique identifier of the lab test to be deleted.
     * @return A boolean indicating whether the deletion was successful.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeLabTestById(@PathVariable long id) {
        return new ResponseEntity<>(labTestService.removeLabTestById(id), HttpStatus.OK);
    }
}
