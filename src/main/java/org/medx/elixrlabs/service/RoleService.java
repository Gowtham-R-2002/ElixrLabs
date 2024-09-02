package org.medx.elixrlabs.service;

import java.util.List;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.util.RoleEnum;

/**
 * <p>
 * Interface for RoleService, defining the business operations related to Role.
 * This interface is implemented by RoleServiceImpl and defines the contract for
 * managing Role entities.
 * </p>
 */
public interface RoleService {

    void setupInitialData();

    /**
     * Retrieves all Roles.
     *
     * @return A list of roles.
     * @throws LabException if the role is not found.
     */
    List<Role> getAllRoles();

    /**
     * Retrieves a role by name.
     *
     * @param name {@link RoleEnum} The name of the role.
     * @return The role.
     * @throws LabException if the role is not found.
     */
    Role getRoleByName(RoleEnum name);
}
