package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.LabTestRepository;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.*;
import org.medx.elixrlabs.util.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetupInitializerService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private LabTestRepository labTestRepository;

    @Autowired
    private AdminService adminService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(SetupInitializerService.class);

    public void initializeData() {
        setupRoleData();
        setupSuperAdminData();
        setupAdminData();
        setupLabTestData();
    }

    private void setupAdminData() {
        User user = User.builder()
                .email("sabarisha0622@gmail.com")
                .password("admin@123")
                .build();
        user.setRoles(List.of(roleService.getRoleByName
                (RoleEnum.ROLE_ADMIN)));
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Admin admin = Admin.builder()
                .user(user)
                .build();
        User anotherUser = User.builder()
                .email("deomuja@gmail.com")
                .password("admin@123")
                .build();
        anotherUser.setRoles(List.of(roleService.getRoleByName(RoleEnum.ROLE_ADMIN)));
        String anotherPassword = bCryptPasswordEncoder.encode(anotherUser.getPassword());
        anotherUser.setPassword(anotherPassword);
        Admin anotherAdmin = Admin.builder()
                .user(anotherUser)
                .build();
        try {
            adminService.createOrUpdateAdmin(admin);
            adminService.createOrUpdateAdmin(anotherAdmin);
        } catch (Exception e) {
            System.out.println("Admin already present...Skipping" + e.getMessage());
        }
    }

    private void setupLabTestData() {
        try {
            LabTest bloodTest = LabTest.builder()
                    .name("Blood test")
                    .description("Simple Blood test")
                    .price(150)
                    .defaultValue("BPC : 1000")
                    .build();

            LabTest HIVTest = LabTest.builder()
                    .name("HIV Test")
                    .description("Test to identify HIV status")
                    .price(500)
                    .defaultValue("Status : negative")
                    .build();

            LabTest cancerTest = LabTest.builder()
                    .name("Cancer Test")
                    .description("Test to detect Cancer presence")
                    .price(1000)
                    .defaultValue("Cell count : 500")
                    .build();

            labTestRepository.save(bloodTest);
            labTestRepository.save(HIVTest);
            labTestRepository.save(cancerTest);

            logger.info("Initial lab test data setup completed.");
        } catch (Exception e) {
            logger.warn("Error during initial data setup: {}", e.getMessage());
        }
    }

    private void setupRoleData() {
        try {
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_ADMIN).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_PATIENT).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_SAMPLE_COLLECTOR).build());
            roleRepository.save(Role.builder().name(RoleEnum.ROLE_SUPER_ADMIN).build());
            logger.info("Initial roles setup successfully.");
        } catch (Exception e) {
            logger.warn("Roles already exist or could not be created: {}", e.getMessage());
        }
    }

    private void setupSuperAdminData() {
        try {
            User superAdmin = User.builder()
                    .roles(List.of(roleRepository.findByName(RoleEnum.ROLE_SUPER_ADMIN)))
                    .email("ergowthamramesh@gmail.com")
                    .password(bCryptPasswordEncoder.encode("sup@123"))
                    .build();
            userRepository.save(superAdmin);
            logger.info("Initial Super Admin setup successfully.");
        } catch (Exception e) {
            logger.warn("Super Admin Already exists !: {}", e.getMessage());
        }
    }
}
