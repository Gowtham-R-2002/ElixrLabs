package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/labs")
public class LabController {
    @Autowired
    private LabService labService;

    @Autowired
    private SampleCollectorService sampleCollectorService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<OrderLocationDto>> getOrders() {
        return new ResponseEntity<>(labService.getOrders(), HttpStatus.OK);
    }

    @PatchMapping("orders/{id}")
    public ResponseEntity<HttpStatus.Series> updateOrderStatus(@PathVariable Long id) {
        labService.updateStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus.Series> updateReport(TestResult testResult) {
        labService.assignReport(testResult);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("sample-collectors")
    public ResponseEntity<HttpStatus.Series> verifySampleCollector(@RequestBody UserDto sampleCollector){
        sampleCollectorService.verifySampleCollector(sampleCollector.getEmail());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("sample-collectors")
    public ResponseEntity<List<SampleCollectorDto>> getAllSampleCollectors() {
        return new ResponseEntity<>(sampleCollectorService.getAllSampleCollectors(), HttpStatus.OK);
    }

    @GetMapping("patients/orders")
    public ResponseEntity<List<OrderSuccessDto>> getOrdersByPatient(UserDto patientDto) {
        return new ResponseEntity<>(patientService.getOrdersByPatient(patientDto), HttpStatus.OK);
    }

    @GetMapping("patients/orders/{id}/test-results")
    public ResponseEntity<TestResultDto> getTestResult(@PathVariable Long orderId, @RequestBody UserDto patientDto) {
        return null;
    }
}
