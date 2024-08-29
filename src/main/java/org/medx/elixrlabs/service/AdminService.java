package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.Admin;
import org.springframework.stereotype.Service;

import java.util.List;

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

    Admin getAdminByEmail(String email);
}

