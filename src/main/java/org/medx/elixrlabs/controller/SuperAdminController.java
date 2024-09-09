package org.medx.elixrlabs.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.service.SuperAdminService;

/**
 * <p>
 * The SuperAdminController class handles HTTP requests related to Super Admin operations.
 * It provides RESTful APIs to manage admin users, including creating, updating, retrieving,
 * deleting, and blocking users.
 * This controller interacts with the {@link SuperAdminService} to perform the actual
 * business logic.
 * </p>
 */
@RestController
@RequestMapping("api/v1/super-admin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    /**
     * Creates a new admin by accepting the details in the request body.
     *
     * @param adminDto the DTO containing the admin's details (email, password, etc.).
     * @return a {@link ResponseEntity} with an HTTP status of CREATED (201) when the admin
     *         is successfully created.
     */
    @PostMapping
    public ResponseEntity<HttpStatus.Series> createAdmin(@RequestBody AdminDto adminDto) {
        superAdminService.createOrUpdateAdmin(adminDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Updates an existing admin's details by accepting the updated information in the request body.
     *
     * @param adminDto the DTO containing the admin's updated details.
     * @return a {@link ResponseEntity} with an HTTP status of ACCEPTED (202) when the
     *         update is successful.
     */
    @PutMapping
    public ResponseEntity<HttpStatus.Series> updateAdmin(@RequestBody AdminDto adminDto) {
        superAdminService.createOrUpdateAdmin(adminDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * Retrieves all admin users in the system as a map, where the key is the admin's email
     * and the value is their name.
     *
     * @return a {@link ResponseEntity} containing a map of admins and an HTTP status of OK (200).
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> getAllAdmins() {
        return new ResponseEntity<>(superAdminService.getAdmins(), HttpStatus.OK);
    }

    /**
     * Soft deletes an admin by marking them as deleted. The email of the admin to be deleted
     * is passed in the request body.
     *
     * @param requestUserNameDto a DTO containing the email of the admin to be deleted.
     * @return a {@link ResponseEntity} with an HTTP status of OK (200) when the deletion
     *         is successful.
     */
    @DeleteMapping
    public ResponseEntity<HttpStatus.Series> deleteAdmin(@RequestBody RequestUserNameDto requestUserNameDto) {
        superAdminService.deleteAdmin(requestUserNameDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Blocks a user from accessing the system by setting their 'blocked' status to true.
     * The email of the user to be blocked is passed in the request body.
     *
     * @param requestUserNameDto a DTO containing the email of the user to be blocked.
     * @return a {@link ResponseEntity} with an HTTP status of OK (200) when the user is
     *         successfully blocked.
     */
    @PatchMapping
    public ResponseEntity<HttpStatus.Series> blockUser(@RequestBody RequestUserNameDto requestUserNameDto) {
        superAdminService.blockUser(requestUserNameDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
