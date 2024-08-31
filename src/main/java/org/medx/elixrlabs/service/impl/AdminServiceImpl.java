package org.medx.elixrlabs.service.impl;

import java.util.List;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.AdminRepository;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.AdminService;
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

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void setupInitialData() {
        User user = User.builder()
                .email("sabarisha0622@gmail.com")
                .password("admin@123")
                .build();
        user.setRoles(roleRepository.findAll());
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Admin admin = Admin.builder()
                .user(user)
                .build();
        User anotherUser = User.builder()
                .email("deomuja@gmail.com")
                .password("admin@123")
                .build();
        user.setRoles(roleRepository.findAll());
        String anotherPassword = bCryptPasswordEncoder.encode(anotherUser.getPassword());
        anotherUser.setPassword(password);
        Admin anotherAdmin = Admin.builder()
                .user(anotherUser)
                .build();
        try {
            adminRepository.save(admin);
            adminRepository.save(anotherAdmin);
        } catch (Exception e) {
            System.out.println("Admin already present...Skipping" + e.getMessage());
        }
    }

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


}
