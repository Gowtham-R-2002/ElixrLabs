package org.medx.elixrlabs.controller;

import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.RequestSlotBookDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.ResponsePatientDto;
import org.medx.elixrlabs.dto.SlotBookDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.PatientService;

/**
 * <p>Handles operations related to patients, including patient registration,
 * appointment slot booking, order retrieval, and cart management. Provides endpoints
 * for managing patient information and associated actions.</p>
 *
 * @author Gowtham R
 */
@RestController
@RequestMapping("api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    /**
     * <p>Registers a new patient with the provided details.</p>
     *
     * @param userDto Contains the data for the patient to be created or updated.
     * @return The created or updated {@link ResponsePatientDto}.
     */
    @PostMapping("register")
    public ResponseEntity<ResponsePatientDto> createPatient(@Valid @RequestBody UserDto userDto) {
        ResponsePatientDto savedUser = patientService.createPatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * <p>Retrieves available appointment slots based on the provided slot booking request.</p>
     *
     * @param slotBookDto Contains the data for retrieving available slots.
     * @return A set of available appointment slots.
     */
    @GetMapping("slots")
    public ResponseEntity<Set<String>> getAvailableSlots(@Valid @RequestBody RequestSlotBookDto slotBookDto) {
        return new ResponseEntity<>(appointmentSlotService.getAvailableSlots(slotBookDto), HttpStatus.OK);
    }

    /**
     * <p>Books an appointment slot based on the provided slot booking details.</p>
     *
     * @param slotBookDto Contains the data for the slot to be booked.
     * @return Details of the successfully booked {@link OrderSuccessDto}.
     */
    @PostMapping("slots/book")
    public ResponseEntity<OrderSuccessDto> bookSlot(@Valid @RequestBody SlotBookDto slotBookDto) {
        return new ResponseEntity<>(appointmentSlotService.bookSlot(slotBookDto), HttpStatus.OK);
    }


    /**
     * <p>Updates an existing patient's details with the provided information.</p>
     *
     * @param userDto Contains the updated data for the patient.
     * @return The updated {@link ResponsePatientDto}.
     */
    @PutMapping
    public ResponseEntity<ResponsePatientDto> updatePatient(@Valid @RequestBody UserDto userDto) {
        ResponsePatientDto savedUser = patientService.updatePatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    /**
     * <p>Deletes the patient record associated with the currently authenticated user.</p>
     *
     * @return A response indicating the deletion status.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus.Series> deletePatient() {
        patientService.deletePatient();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * <p>Adds test(s) or package(s) to the cart for the patient.</p>
     *
     * @param cartDto Contains the data for the tests or packages to be added to the cart.
     * @return The updated {@link ResponseCartDto}.
     */
    @PutMapping("carts")
    public ResponseEntity<ResponseCartDto> addTestOrPackagesToCart(@Valid @RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.addTestsOrPackagesToCart(cartDto), HttpStatus.ACCEPTED);
    }

    /**
     * <p>Retrieves the cart details for the currently authenticated patient.</p>
     *
     * @return The {@link ResponseCartDto} for the patient.
     */
    @GetMapping("carts")
    public ResponseEntity<ResponseCartDto> getCart() {
        return new ResponseEntity<>(cartService.getCartByPatient(), HttpStatus.OK);
    }

    /**
     * <p>Removes all tests or packages from the cart for the currently authenticated patient.</p>
     *
     * @return A response indicating the removal status.
     */
    @DeleteMapping("carts")
    public ResponseEntity<HttpStatus.Series> removeTestsOrPackageFromCart() {
        cartService.deleteCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
