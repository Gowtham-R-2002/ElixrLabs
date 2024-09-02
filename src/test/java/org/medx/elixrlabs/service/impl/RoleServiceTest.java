package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.util.RoleEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role1;
    private Role role2;
    private Role role3;
    private List<Role> roles;

    @BeforeEach
    void setUp() {
        role1 = Role.builder()
                .id(1)
                .name(RoleEnum.ROLE_ADMIN)
                .build();

        role2 = Role.builder()
                .id(2)
                .name(RoleEnum.ROLE_SAMPLE_COLLECTOR)
                .build();

        role3 = Role.builder()
                .id(3)
                .name(RoleEnum.ROLE_PATIENT)
                .build();

        roles = Arrays.asList(role1, role2, role3);
    }

    @Test
    void testGetAllRoles_positive() {
        when(roleRepository.findAll()).thenReturn(roles);
        List<Role> result = roleService.getAllRoles();
        assertEquals(roles, result);
    }

    @Test
    void testGetAllRoles_exception() {
        when(roleRepository.findAll()).thenThrow(RuntimeException.class);
        assertThrows(LabException.class, () -> roleService.getAllRoles());
    }
}
