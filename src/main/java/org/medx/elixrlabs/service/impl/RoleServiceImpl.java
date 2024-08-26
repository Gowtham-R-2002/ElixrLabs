package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Service implementation for managing RoleService-related operations.
 * This class contains business logic for handling RoleService entities, including
 * retrieval and some more operations. It acts as a bridge between the controller layer
 * and the repository layer, ensuring that business rules are applied before
 * interacting with the database.
 * </p>
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void setupInitialData() {
        try {
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_ADMIN).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_PATIENT).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_SAMPLE_COLLECTOR).build());
        } catch (Exception e) {
            System.out.println("Roles already exists.." + e.getMessage());
        }
    }

    public List<Role> getAllRoles() {
        List<Role> roles;
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            throw new LabException("Error while getting all roles");
        }
        return roles;
    }

    public Role getRoleByName(RoleEnum name) {
        Role role;
        try {
            System.out.println("ROLE : "+ name);
            role = roleRepository.findByName(name);
        } catch (Exception e) {
            throw new LabException("Error while getting role with name : " + name + e.getMessage());
        }
        return role;
    }
}
