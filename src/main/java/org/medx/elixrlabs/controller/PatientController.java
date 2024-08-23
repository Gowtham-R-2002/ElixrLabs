package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.dto.RegisterAndLoginUserDto;
import org.medx.elixrlabs.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("register")
    public ResponseEntity<RegisterAndLoginUserDto> createPatient(@RequestBody RegisterAndLoginUserDto userDto) {
        System.out.println("email : " + userDto.getEmail() + "DOB : " + userDto.getDateOfBirth());
        RegisterAndLoginUserDto savedUser =  patientService.createPatient(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RegisterAndLoginUserDto>> getAllPatients() {
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }
}
