package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.dto.CreateAndRetrieveLabTestDto;
import org.medx.elixrlabs.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/lab-tests")
public class LabTestController {

    @Autowired
    private LabTestService labTestService;

    @PostMapping
    public ResponseEntity<CreateAndRetrieveLabTestDto> createLabTest(@RequestBody CreateAndRetrieveLabTestDto labTestDto) {
        return new ResponseEntity<>(labTestService.createOrUpdateTest(labTestDto),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CreateAndRetrieveLabTestDto>> getAllLabTests() {
        return new ResponseEntity<>(labTestService.getAllLabTests(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateAndRetrieveLabTestDto> getLabTestById(@PathVariable long id) {
        return new ResponseEntity<>(labTestService.getLabTestById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CreateAndRetrieveLabTestDto> updateLabTestById(@RequestBody CreateAndRetrieveLabTestDto labTestDto) {
        return new ResponseEntity<>(labTestService.createOrUpdateTest(labTestDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeLabTestById(@PathVariable long id) {
        return new ResponseEntity<>(labTestService.removeLabTestById(id), HttpStatus.OK);
    }
}
