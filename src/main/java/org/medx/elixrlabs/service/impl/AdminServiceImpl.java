package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

//    @Override
//    public List<SampleCollectorDto> getAllSampleCollectors() {
//        return List.of();
//    }

    @Override
    public boolean deleteSampleCollector(String id) {
        return false;
    }

}
