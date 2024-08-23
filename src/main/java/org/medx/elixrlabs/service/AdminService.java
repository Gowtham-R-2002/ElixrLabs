package org.medx.elixrlabs.service;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User createAdmin(User user) {
        roleRepository.findById(1).ifPresent(role -> {
            user.setRoles(List.of(role));
        });
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User createSampleCollector(User user) {
        roleRepository.findById(3).ifPresent(role -> user.setRoles(List.of(role)));
        return userRepository.save(user);
    }

    public List<User> getAllSampleCollectors() {
        return userRepository.findAll();
    }

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

    public User getSampleCollectorByEmail(String email) {
        User user;
        user = userRepository.findByEmailAndIsDeletedFalse(email);
        if (null == user) {
            throw new LabException("Sample collector not found with Email : " + email);
        }
        return user;
    }

//    public User verifySampleCollector(User user) {
//        return
//    }
}
