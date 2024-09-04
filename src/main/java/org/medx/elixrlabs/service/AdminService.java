package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.Admin;
import org.springframework.stereotype.Service;

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
     * <p>
     *
     * </p>
     */

    void setupInitialData();

    /**
     *Fetches admin with the help of email
     *
     * @param email email of the admin
     * @return admin
     */

    Admin getAdminByEmail(String email);
}

