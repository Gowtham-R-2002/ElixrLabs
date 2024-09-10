package org.medx.elixrlabs.service;

import java.util.Map;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.model.Admin;

/**
 * <p> Interface for AdminService, defining the business operations related to Admin.
 * This interface is implemented by AdminServiceImpl and defines the contract for
 * managing Admin entities. </p>
 */
@Service
public interface AdminService {
    /**
     * Fetches the admin details using the provided email address
     *
     * @param email The email of the admin to be fetched
     * @return {@link Admin} which contains the admin details
     */

    Admin getAdminByEmail(String email);

    /**
     * Creates a new admin or updates an existing admin
     *
     * @param adminDto {@link AdminDto} contains details of admin to be created or
     *                           updated
     */

    AdminDto createAdmin(AdminDto adminDto);

    /**
     * Updates the existing admin with the updated details
     * @param adminDto The Updated Admin details.
     * @return The Updated adminDetails
     */
    AdminDto updateAdmin(AdminDto adminDto);

    /**
     * Soft deletes the admin
     *
     * @param userNameDto The username of the admin to be deleted.
     */
    void deleteAdmin(RequestUserNameDto userNameDto);

    /**
     * Retrieves all the admins
     *
     * @return a map which contains admin emails as keys and admin names as values
     */

    Map<String, String> getAllAdmins();
}

