package org.medx.elixrlabs.service.impl;

import java.util.List;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.User;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public void setupInitialData() {
        User user = User.builder()
                .email("admin@gmail.com")
                .password("admin@123")
                .build();
        user.setRoles(roleRepository.findAll());
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Admin already present...Skipping" + e.getMessage());
        }
    }

    @Override
    public boolean verifySampleCollector(long id) {
        return false;
    }

    @Override
    public List<SampleCollectorDto> getAllSampleCollectors() {
        return List.of();
    }

    @Override
    public boolean deleteSampleCollector(String id) {
        return false;
    }

}
