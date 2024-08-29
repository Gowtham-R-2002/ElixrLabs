package org.medx.elixrlabs.controller;

import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;
import org.medx.elixrlabs.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.PatientService;

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @PostMapping("register")
    public ResponseEntity<ResponsePatientDto> createOrUpdatePatient(@RequestBody UserDto userDto) {
        ResponsePatientDto savedUser =  patientService.createOrUpdatePatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("slots")
    public ResponseEntity<Set<String>> getAvailableSlots(@RequestBody SlotBookDto slotBookDto) {
        System.out.println(appointmentSlotService
                .getAvailableSlots(slotBookDto));
        return new ResponseEntity<>(appointmentSlotService
                .getAvailableSlots(slotBookDto), HttpStatus.OK);
    }

    @PostMapping("slots/book")
    public ResponseEntity<OrderSuccessDto> bookSlot(@Valid @RequestBody SlotBookDto slotBookDto) {
        return new ResponseEntity<>(appointmentSlotService.bookSlot(slotBookDto), HttpStatus.OK);
    }

    @GetMapping("orders")
    public ResponseEntity<List<ResponseOrderDto>> getOrders() {
        return new ResponseEntity<>(patientService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("orders/{id}/report")
    public ResponseEntity<TestResult> getTestReport(@PathVariable(name = "id") Long orderId) {
        return new ResponseEntity<>(patientService.getTestReport(orderId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponsePatientDto> updatePatient(@RequestBody UserDto userDto) {
        ResponsePatientDto savedUser =  patientService.createOrUpdatePatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus.Series> deletePatient() {
        patientService.deletePatient(SecurityContextHelper.extractEmailFromContext());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("carts")
    public ResponseEntity<ResponseCartDto> addTestOrPackagesToCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.addTestsOrPackagesToCart(cartDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("carts")
    public ResponseEntity<ResponseCartDto> getCartByPatient() {
        return new ResponseEntity<>(cartService.getCartByPatient(), HttpStatus.OK);
    }

    @DeleteMapping("carts")
    public ResponseEntity<HttpStatus.Series> removeTestsOrPackageFromCart() {
        cartService.deleteCart();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
