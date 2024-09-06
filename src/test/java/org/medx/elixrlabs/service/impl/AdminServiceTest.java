package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.AdminRepository;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.util.RoleEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private RoleRepository roleRepository;

    private Admin admin;
    private User user;
    private Role firstRole;
    private Role secondRole;
    private Role thirdRole;
    private Role role;

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
        role = (firstRole);
        user = User.builder()
                .email("admin@gmail.com")
                .password("admin@123")
                .role(role)
                .build();
        admin = Admin.builder()
                .id(1L)
                .user(user)
                .build();
    }

    @Test
    public void testGetAdminByEmail() {
        when(adminRepository.findByEmail(user.getEmail())).thenReturn(admin);
        Admin foundAdmin = adminService.getAdminByEmail(user.getEmail());
        assertNotNull(foundAdmin);
        assertEquals(foundAdmin.getId(), 1L);
    }

    @Test
    public void testGetAdminByEmail_exception() {
        when(adminRepository.findByEmail(user.getEmail())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> adminService.getAdminByEmail(user.getEmail()));
    }
}
