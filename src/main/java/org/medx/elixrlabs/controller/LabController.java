package org.medx.elixrlabs.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.dto.RequestTestResultDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.service.impl.SampleCollectorServiceImpl;

/**
 * REST controller for managing Lab-related operations.
 *
 * <p>
 * This controller handles HTTP requests and provides endpoints for
 * retrieves orders, updates order status, updates the report for order,
 * verifies the sampleCollector, retrieves all sampleCollector and some more operations.
 * It is annotated with Spring MVC annotations to define the URL mappings
 * and request handling logic.
 * All responses are returned in a standardized format to ensure consistency across
 * the API.
 * </p>
 */
@RestController
@RequestMapping("api/v1/labs")
public class LabController {

    private static final Logger logger = LoggerFactory.getLogger(SampleCollectorServiceImpl.class);

    @Autowired
    private LabService labService;

    @Autowired
    private SampleCollectorService sampleCollectorService;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<ResponseOrderDto>> getOrders() {
        logger.debug("Getting the orders");
        return new ResponseEntity<>(labService.getOrders(), HttpStatus.OK);
    }

    @PatchMapping("orders/{id}")
    public ResponseEntity<HttpStatus.Series> updateOrderStatus(@PathVariable Long id) {
        logger.debug("Updating the order status for the ID: {}", id);
        labService.updateStatus(id);
        logger.info("Successfully updated the status of order for the ID: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("orders/{id}/results")
    public ResponseEntity<HttpStatus.Series> updateReport(@RequestBody RequestTestResultDto resultDto, @PathVariable(name = "id") long id) {
        labService.assignReport(id, resultDto);
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
    public ResponseEntity<List<ResponseOrderDto>> getOrdersByPatient(@RequestBody UserDto patientDto) {
        return new ResponseEntity<>(patientService.getOrdersByPatient(patientDto), HttpStatus.OK);
    }

    @GetMapping("orders/{id}/results")
    public ResponseEntity<TestResultDto> getTestResult(@PathVariable(name = "id") Long orderId) {
        return new ResponseEntity<>(labService.getTestResultByOrder(orderId), HttpStatus.OK);
    }
}
