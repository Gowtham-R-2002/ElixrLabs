package org.medx.elixrlabs.controller;

import java.util.List;

import jakarta.validation.Valid;
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
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;


/**
 * <p>Handles requests related to laboratory operations, including
 * managing orders, updating statuses, and processing test results.
 * Provides endpoints for interacting with labs, sample collectors,
 * and patient orders.</p>
 *
 * @author Gowtham R
 */
@RestController
@RequestMapping("api/v1/labs")
public class LabController {

    private static final Logger logger = LoggerFactory.getLogger(LabController.class);

    @Autowired
    private LabService labService;

    @Autowired
    private SampleCollectorService sampleCollectorService;

    @Autowired
    private PatientService patientService;

    /**
     * <p>Retrieves a list of all orders.</p>
     *
     * @return A list of {@link ResponseOrderDto} containing order details.
     */
    @GetMapping
    public ResponseEntity<List<ResponseOrderDto>> getOrders() {
        logger.debug("Getting the orders");
        return new ResponseEntity<>(labService.getOrders(), HttpStatus.OK);
    }

    /**
     * <p>Updates the status of a specific order identified by its ID.</p>
     *
     * @param id The unique identifier of the order whose status needs to be updated.
     * @return HTTP status indicating the result of the update operation.
     */
    @PatchMapping("orders/{id}")
    public ResponseEntity<HttpStatus.Series> updateOrderStatus(@PathVariable Long id) {
        logger.debug("Updating the order status for the ID: {}", id);
        labService.updateStatus(id);
        logger.info("Successfully updated the status of order for the ID: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>Updates the test report for a specific order with the provided result details.</p>
     *
     * @param resultDto Contains the test result data to be updated.
     * @param id The unique identifier of the order for which the report is being updated.
     * @return HTTP status indicating the acceptance of the report update.
     */
    @PutMapping("orders/{id}/results")
    public ResponseEntity<HttpStatus.Series> updateReport(@Valid @RequestBody RequestTestResultDto resultDto, @PathVariable(name = "id") long id) {
        labService.assignReport(id, resultDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * <p>Verifies the sample collector using the provided email address.</p>
     *
     * @param sampleCollector Contains the email of the sample collector to be verified.
     * @return HTTP status indicating the acceptance of the verification request.
     */
    @PatchMapping("sample-collectors")
    public ResponseEntity<HttpStatus.Series> verifySampleCollector(@Valid @RequestBody RequestUserNameDto sampleCollector){
        sampleCollectorService.verifySampleCollector(sampleCollector.getEmail());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * <p>Retrieves a list of all sample collectors.</p>
     *
     * @return A list of {@link SampleCollectorDto} with their details.
     */
    @GetMapping("sample-collectors")
    public ResponseEntity<List<SampleCollectorDto>> getAllSampleCollectors() {
        return new ResponseEntity<>(sampleCollectorService.getAllSampleCollectors(), HttpStatus.OK);
    }

    /**
     * <p>Retrieves a list of orders associated with a specific patient.</p>
     *
     * @param patient Contains the details of the patient for whom orders are to be retrieved.
     * @return A list of {@link ResponseOrderDto} related to the specified patient.
     */
    @GetMapping("patients/orders")
    public ResponseEntity<List<ResponseOrderDto>> getOrdersByPatient(@Valid @RequestBody RequestUserNameDto patient) {
        return new ResponseEntity<>(patientService.getOrdersByPatient(patient), HttpStatus.OK);
    }

    /**
     * <p>Retrieves the test result for a specific order identified by its ID.</p>
     *
     * @param orderId The unique identifier of the order for which the test result is being retrieved.
     * @return The {@link TestResultDto} associated with the specified order.
     */
    @GetMapping("orders/{id}/results")
    public ResponseEntity<TestResultDto> getTestResult(@PathVariable(name = "id") Long orderId) {
        return new ResponseEntity<>(labService.getTestResultByOrder(orderId), HttpStatus.OK);
    }
}
