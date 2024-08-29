package org.medx.elixrlabs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.util.RoleEnum;

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

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void setupInitialData() {
        try {
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_ADMIN).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_PATIENT).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_SAMPLE_COLLECTOR).build());
            logger.info("Initial roles setup successfully.");
        } catch (Exception e) {
            logger.warn("Roles already exist or could not be created: {}", e.getMessage());
        }
    }

    @Override
    public List<Role> getAllRoles() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            logger.warn("Error while getting all roles: {}", e.getMessage());
            throw new LabException("Error while getting all roles.");
        }
    }

    @Override
    public Role getRoleByName(RoleEnum name) {
        try {
            logger.debug("Fetching role: {}", name);
            return roleRepository.findByName(name);
        } catch (Exception e) {
            logger.warn("Error while getting role with name {}: {}", name, e.getMessage());
            throw new LabException("Error while getting role with name: " + name);
        }
    }
}
