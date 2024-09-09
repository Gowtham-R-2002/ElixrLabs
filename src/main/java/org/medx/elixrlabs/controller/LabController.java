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

}
