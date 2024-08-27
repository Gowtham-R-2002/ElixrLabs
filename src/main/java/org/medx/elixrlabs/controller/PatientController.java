package org.medx.elixrlabs.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TimeSlotEnum;
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
    private PatientService patientService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @PostMapping("register")
    public ResponseEntity<UserDto> createOrUpdatePatient(@RequestBody UserDto userDto) {
        UserDto savedUser =  patientService.createOrUpdatePatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

//    @PostMapping("appointments")
//    public ResponseEntity<Order> bookAppointment() {
//
//    }

    @GetMapping("slots")
    public ResponseEntity<Set<TimeSlotEnum>> getAvailableSlots() {
        return new ResponseEntity<>(appointmentSlotService
                .getAvailableSlots(
                        LocationEnum.MARINA, LocalDate.of(2024,9, 10),
                        TestCollectionPlaceEnum.HOME), HttpStatus.OK);
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
