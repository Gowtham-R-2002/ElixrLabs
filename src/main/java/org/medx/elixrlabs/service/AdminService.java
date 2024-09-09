package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.Admin;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * Interface for AdminService, defining the business operations related to Admin.
 * This interface is implemented by AdminServiceImpl and defines the contract for
 * managing Admin entities.
 * </p>
 */
@Service
public interface AdminService {
    /**
     *Fetches admin with the help of email
     *
     * @param email email of the admin
     * @return admin
     */

    Admin getAdminByEmail(String email);

    void createOrUpdateAdmin(Admin admin);

    Map<String, String> getAllAdmins();
}

