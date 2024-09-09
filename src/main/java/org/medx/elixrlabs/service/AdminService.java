package org.medx.elixrlabs.service;

import java.util.Map;

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
     * @param admin {@link Admin} contains details of admin to be created or
     *                           updated
     */

    void createOrUpdateAdmin(Admin admin);

    /**
     * Retrieves all the admins
     *
     * @return a map which contains admin emails as keys and admin names as values
     */

    Map<String, String> getAllAdmins();
}

