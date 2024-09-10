package org.medx.elixrlabs.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * REST controller for managing SampleCollector-related operations.
 *
 * <p>
 * This controller handles HTTP requests and provides endpoints for
 * creating, retrieving, updating, and deleting SampleCollector entities.
 * All responses are returned in a standardized format to ensure consistency across
 * the API.
 * </p>
 *
 * @author Sabarinathan K
 */
@RestController
@RequestMapping("api/v1/sample-collectors")
public class SampleCollectorController {

    @Autowired
    private SampleCollectorService sampleCollectorService;

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @Autowired
    private JwtService jwtService;

    /**
     * Creates a new sample collector.
     *
     * @param userDto {@link UserDto} The DTO containing employee data.
     * @return The created sample collector DTO with HTTP status 201 CREATED.
     */
    @PostMapping("register")
    public ResponseEntity<SampleCollectorDto> createSampleCollector(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.createSampleCollector(userDto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing sample collector's details by their unique ID.
     *
     * @param userDto {@link UserDto} The DTO containing sample collector data.
     * @return the updated sample collector DTO with HTTP status 202 ACCEPTED.
     */
    @PutMapping
    public ResponseEntity<SampleCollectorDto> updateSampleCollector(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.updateSampleCollector(userDto), HttpStatus.ACCEPTED);
    }

    /**
     * Deletes a sample collector by their unique ID.
     *
     * @return boolean value with HTTP status 204 NO CONTENT.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteSampleCollector() {
        return new ResponseEntity<>(sampleCollectorService.deleteSampleCollector(), HttpStatus.NO_CONTENT);
    }

    /**
     * Assigns the appointment to the currently logged in sample collector.
     *
     * @param id The ID of the appointment to be assigned
     * @return The status code 202 Accepted
     */
    @PatchMapping("/appointments/{id}")
    public ResponseEntity<Void> assignAppointment(@PathVariable Long id) {
        appointmentSlotService.assignSampleCollectorToAppointment(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Updates the status of the appointment as sample collected.
     *
     * @param id The ID of the appointment
     * @return The status code of 202 Accepted
     */
    @PatchMapping("/appointments/{id}/status")
    public ResponseEntity<Void> markSampleCollected(@PathVariable Long id) {
        appointmentSlotService.markSampleCollected(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Gets all assigned appointments of the currently logged in sample collector
     *
     * @return {@link List<AppointmentDto>} Appointments that are assigned to the currently logged in sample collector
     */
    @GetMapping("appointments")
    public ResponseEntity<List<AppointmentDto>> getAppointments(@RequestParam(required = false, name = "assigned") Boolean isAssigned, @RequestParam(required = false, name = "collected") Boolean isCollected, @RequestParam(required = false, name = "date") LocalDate date) {
        if((null == isAssigned) && (null == isCollected) && (null == date)) {
            throw new NoSuchElementException("URI Not found ! Check the URI entered !");
        }
        if (isAssigned == null && isCollected == null && date != null) {
            LocationEnum place = null;
            try {
                place = sampleCollectorService.getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext()).getUser().getPlace();
            } catch (Exception e) {
                throw new NullPointerException("Sample Collecor is Not verified !");
            }
            List<AppointmentDto> appointments = appointmentSlotService.getAppointmentsByPlace(place, date);
            return new ResponseEntity<>(appointments, HttpStatus.OK);
        } else if (isAssigned && isCollected == null) {
            List<AppointmentDto> appointmentDtos = appointmentSlotService
                    .getAppointmentsBySampleCollector();
            return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
        } else if (isAssigned && !isCollected) {
            List<AppointmentDto> appointmentDtos = appointmentSlotService
                    .getPendingAppointmentsBySampleCollector();
            return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
        } else if (isAssigned && isCollected) {
            List<AppointmentDto> appointmentDtos = appointmentSlotService
                    .getCollectedAppointmentsBySampleCollector();
            return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
        } else {
            throw new NoSuchElementException("URI Not found ! Check the URI entered !");
        }
    }
}
