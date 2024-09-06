package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.AdminRepository;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Service implementation for managing Admin-related operations.
 * This class contains business logic for handling User operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Admin getAdminByEmail(String email) {
        Admin admin;
        try {
            admin = adminRepository.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching admin from the given mail : " + email);
        }
        return admin;
    }

    @Override
    public void createOrUpdateAdmin(Admin admin) {
        try {
            adminRepository.save(admin);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving admin from the given mail : " + admin.getUser().getEmail());
        }
    }

    @Override
    public Map<String, String> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().collect(Collectors
                .toMap(
                        x -> x.getUser().getEmail(),
                        x -> (x.isDeleted() ? "Deleted" : "Not Deleted")
                )
        );
    }


}
