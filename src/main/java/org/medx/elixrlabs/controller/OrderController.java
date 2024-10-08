package org.medx.elixrlabs.controller;

import jakarta.validation.Valid;
import org.medx.elixrlabs.dto.RequestTestResultDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private PatientService patientService;

    @Autowired
    private LabService labService;
    /**
     * <p>Retrieves all orders associated with the patient.</p>
     *
     * @return A list of {@link ResponseOrderDto} associated with the patient.
     */
    @GetMapping()
    public ResponseEntity<List<ResponseOrderDto>> getOrdersOfPatient() {
        return new ResponseEntity<>(patientService.getOrders(), HttpStatus.OK);
    }

    /**
     * <p>Retrieves the test report for a specific order identified by its ID.</p>
     *
     * @param orderId The unique identifier of the order for which the test report is requested.
     * @return The {@link TestResultDto} associated with the specified order ID.
     */
    @GetMapping("{id}/report")
    public ResponseEntity<TestResultDto> getTestReport(@PathVariable(name = "id") Long orderId) {
        return new ResponseEntity<>(patientService.getTestReport(orderId), HttpStatus.OK);
    }

    /**
     * <p>Retrieves a list of orders associated with a specific patient.</p>
     *
     * @param patient Contains the details of the patient for whom orders are to be retrieved.
     * @return A list of {@link ResponseOrderDto} related to the specified patient.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("patients")
    public ResponseEntity<List<ResponseOrderDto>> getOrdersByPatient(@Valid @RequestBody RequestUserNameDto patient) {
        return new ResponseEntity<>(patientService.getOrdersByPatient(patient), HttpStatus.OK);
    }
    /**
     * <p>Retrieves a list of all orders.</p>
     *
     * @return A list of {@link ResponseOrderDto} containing order details.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("all")
    public ResponseEntity<List<ResponseOrderDto>> getOrders() {
        logger.debug("Getting the orders");
        return new ResponseEntity<>(labService.getOrders(), HttpStatus.OK);
    }

    /**
     * <p>Updates the test report for a specific order with the provided result details.</p>
     *
     * @param resultDto Contains the test result data to be updated.
     * @param id The unique identifier of the order for which the report is being updated.
     * @return HTTP status indicating the acceptance of the report update.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}/results")
    public ResponseEntity<HttpStatus.Series> updateReport(@Valid @RequestBody RequestTestResultDto resultDto, @PathVariable(name = "id") long id) {
        labService.assignReport(id, resultDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    /**
     * <p>Retrieves the test result for a specific order identified by its ID.</p>
     *
     * @param orderId The unique identifier of the order for which the test result is being retrieved.
     * @return The {@link TestResultDto} associated with the specified order.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}/results")
    public ResponseEntity<TestResultDto> getTestResult(@PathVariable(name = "id") Long orderId) {
        return new ResponseEntity<>(labService.getTestResultByOrder(orderId), HttpStatus.OK);
    }
}
