package org.medx.elixrlabs.service;

import java.util.Map;

import org.medx.elixrlabs.model.Admin;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;

/**
 * <p>
 * The SuperAdminService interface defines the operations that a Super Admin can perform.
 * These operations include managing admins, retrieving admin data, and blocking user accounts.
 * This service is intended to be used by the Super Admin role to perform high-level
 * administrative tasks across the system, ensuring the proper management of users and admins.
 * </p>
 */
@Service
public interface SuperAdminService {
    AdminDto createAdmin(AdminDto adminDto);

    AdminDto updateAdmin(AdminDto adminDto);

    /**
     * Soft deletes an admin by marking them as deleted. This method does not
     * permanently remove the admin from the database, but instead flags them as deleted.
     *
     * @param requestUserNameDto a DTO containing the email of the admin to be deleted.
     */
    void deleteAdmin(RequestUserNameDto requestUserNameDto);

    /**
     * Retrieves a list of all admins in the system and returns it as a map
     * where the key is the admin's email and the value is their name.
     *
     * @return a map containing the emails and names of all admins.
     */
    Map<String, String> getAdmins();

    /**
     * Blocks a user by setting their 'blocked' status to true. This prevents the
     * user from logging in or performing any actions within the system.
     *
     * @param requestUserNameDto a DTO containing the email of the user to be blocked.
     */
    void blockUser(RequestUserNameDto requestUserNameDto);
}
