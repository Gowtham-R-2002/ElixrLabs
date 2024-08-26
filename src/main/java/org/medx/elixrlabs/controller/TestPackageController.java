package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.service.TestPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RequestMapping("api/v1/test-packages")
public class TestPackageController {
    
    @Autowired
    private TestPackageService testPackageService;

    @PostMapping
    public ResponseEntity<ResponseTestPackageDto> createTestPackage(@RequestBody TestPackageDto testPackageDto) {
        return new ResponseEntity<>(testPackageService.createOrUpdateTest(testPackageDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTestPackageDto>> getAllTestPackages() {
        return new ResponseEntity<>(testPackageService.getAllTestPackages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTestPackageDto> getTestPackageById(@PathVariable long id) {
        return new ResponseEntity<>(testPackageService.getTestPackageById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseTestPackageDto> updateTestPackageById(@RequestBody TestPackageDto testPackageDto) {
        return new ResponseEntity<>(testPackageService.createOrUpdateTest(testPackageDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> removeTestPackageById(@PathVariable long id) {
        return new ResponseEntity<>(testPackageService.deleteTestPackageById(id), HttpStatus.OK);
    }
}
