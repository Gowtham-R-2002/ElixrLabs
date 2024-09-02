package org.medx.elixrlabs.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.AppointmentsQueryDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.AppointmentMapper;
import org.medx.elixrlabs.model.AppointmentSlot;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Arrays.stream;

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
        return new ResponseEntity<>(sampleCollectorService.createOrUpdateSampleCollector(userDto), HttpStatus.CREATED);
    }

    /**
     * Updates an existing sample collector's details by their unique ID.
     *
     * @param userDto {@link UserDto} The DTO containing sample collector data.
     * @return the updated sample collector DTO with HTTP status 202 ACCEPTED.
     */
    @PutMapping
    public ResponseEntity<SampleCollectorDto> updateSampleCollector(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(sampleCollectorService.createOrUpdateSampleCollector(userDto), HttpStatus.ACCEPTED);
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

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentDto>> getAppointments(@Valid @RequestBody AppointmentsQueryDto appointmentDto) {
        LocationEnum place = sampleCollectorService.getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext()).getUser().getPlace();
        List<AppointmentDto> appointments = appointmentSlotService.getAppointmentsByPlace(place, appointmentDto.getDate());
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PatchMapping("/appointments/{id}")
    public ResponseEntity<Void> assignAppointment(@PathVariable Long id) {
        appointmentSlotService.assignSampleCollectorToAppointment(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/appointments/{id}/status")
    public ResponseEntity<Void> markSampleCollected(@PathVariable Long id) {
        appointmentSlotService.markSampleCollected(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("appointments/assigned")
    public ResponseEntity<List<AppointmentDto>> getAllAssignedAppointments() {
        List<AppointmentDto> appointmentDtos = appointmentSlotService
                .getAppointmentsBySampleCollector();
        return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
    }

    @GetMapping("appointments/assigned/collected")
    public ResponseEntity<List<AppointmentDto>> getCollectedAppointments() {
        List<AppointmentDto> appointmentDtos = appointmentSlotService
                .getCollectedAppointmentsBySampleCollector();
        return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
    }

    @GetMapping("appointments/assigned/pending")
    public ResponseEntity<List<AppointmentDto>> getPendingAppointments() {
        List<AppointmentDto> appointmentDtos = appointmentSlotService
                .getPendingAppointmentsBySampleCollector();
        return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
    }

}
