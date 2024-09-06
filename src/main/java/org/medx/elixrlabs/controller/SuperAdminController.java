package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.LoginRequestDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/super-admin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping
    public ResponseEntity<HttpStatus.Series> createAdmin(@RequestBody AdminDto adminDto) {
        superAdminService.createOrUpdateAdmin(adminDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus.Series> updateAdmin(@RequestBody AdminDto adminDto) {
        superAdminService.createOrUpdateAdmin(adminDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllAdmins() {
        return new ResponseEntity<>(superAdminService.getAdmins(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus.Series> deleteAdmin(@RequestBody RequestUserNameDto requestUserNameDto) {
        superAdminService.deleteAdmin(requestUserNameDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus.Series> blockUser(@RequestBody RequestUserNameDto requestUserNameDto) {
        superAdminService.blockUser(requestUserNameDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
