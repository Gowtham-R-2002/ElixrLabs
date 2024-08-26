package org.medx.elixrlabs.controller;

import java.util.List;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.impl.JwtService;
import org.medx.elixrlabs.service.PatientService;
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

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private CartService cartService;

    @PostMapping("register")
    public ResponseEntity<UserDto> createOrUpdatePatient(@RequestBody UserDto userDto) {
        UserDto savedUser =  patientService.createOrUpdatePatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("appointments")
    public ResponseEntity<Order> bookAppointment() {

    }

    @GetMapping
    public ResponseEntity<List<AppointmentSlot>> getAvailableSlots() {

    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @GetMapping("orders")
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(patientService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("orders/{id}/report")
    public ResponseEntity<TestResult> getTestReport(@PathVariable(name = "id") Long orderId) {
        return new ResponseEntity<>(patientService.getTestReport(orderId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updatePatient(@RequestBody UserDto userDto) {
        UserDto savedUser =  patientService.createOrUpdatePatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus.Series> deletePatient() {
        String email = SecurityContextHelper.extractEmailFromContext();
        patientService.deletePatient(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("carts")
    public ResponseEntity<ResponseCartDto> addTestOrPackagesToCart(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.addTestsOrPackagesToCart(cartDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("carts")
    public ResponseEntity<ResponseCartDto> getCartByPatient(@RequestBody UserDto patient) {
        return new ResponseEntity<>(cartService.getCartByPatient(), HttpStatus.OK);
    }

    @DeleteMapping("carts")
    public ResponseEntity<HttpStatus.Series> removeTestsOrPackageFromCart(@RequestBody CartDto cartDto) {
        cartService.removeTestsOrPackageFromCart(cartDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
