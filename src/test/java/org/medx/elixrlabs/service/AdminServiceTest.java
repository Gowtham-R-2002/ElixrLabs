package org.medx.elixrlabs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.AdminRepository;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.util.RoleEnum;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private RoleRepository roleRepository;

    private Admin admin;
    private User user;
    private Role firstRole;
    private Role secondRole;
    private Role thirdRole;
    private List<Role> roles;

    @BeforeEach
    void setUp() {
        firstRole = Role.builder()
                .name(RoleEnum.ROLE_ADMIN)
                .id(1)
                .build();
        secondRole = Role.builder()
                .id(2)
                .name(RoleEnum.ROLE_SAMPLE_COLLECTOR)
                .build();
        thirdRole = Role.builder()
                .id(3)
                .name(RoleEnum.ROLE_PATIENT)
                .build();
        roles = List.of(firstRole, secondRole, thirdRole);
        user = User.builder()
                .email("admin@gmail.com")
                .password("admin@123")
                .roles(roles)
                .build();
        admin = Admin.builder()
                .user(user)
                .build();
    }
}
