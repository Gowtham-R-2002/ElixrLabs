package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.service.SuperAdminService;
import org.medx.elixrlabs.util.RoleEnum;

/**
 * <p>
 * The SuperAdminServiceImpl class provides the implementation of administrative
 * operations specifically for the Super Admin role. These operations include
 * creating or updating an admin, deleting an admin, retrieving a list of all admins,
 * and blocking a user.
 * This service interacts with the {@link RoleService}, {@link AdminService},
 * and {@link UserService} to perform these tasks, ensuring that appropriate
 * business logic is applied for each operation.
 * </p>
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    /**
     * Creates or updates an admin based on the provided {@link AdminDto}.
     * If the admin already exists, it updates the existing admin record;
     * otherwise, it creates a new admin.
     *
     * @param adminDto the data transfer object containing the admin's email, password,
     *                 and other relevant information.
     */
    @Override
    public void createOrUpdateAdmin(AdminDto adminDto) {
        Admin admin = Admin.builder()
                .user(User.builder()
                        .email(adminDto.getEmail())
                        .password(adminDto.getPassword())
                        .roles(List.of(roleService.getRoleByName(RoleEnum.ROLE_ADMIN)))
                        .build())
                .build();
        Admin existingAdmin = adminService.getAdminByEmail(adminDto.getEmail());
        if (null != existingAdmin) {
            admin.getUser().setUUID(existingAdmin.getUser().getUUID());
            admin.setId(existingAdmin.getId());
        }
        adminService.createOrUpdateAdmin(admin);
    }

    @Override
    public void deleteAdmin(RequestUserNameDto requestUserNameDto) {
        Admin admin = adminService.getAdminByEmail(requestUserNameDto.getEmail());
        admin.setDeleted(true);
        adminService.createOrUpdateAdmin(admin);
    }

    @Override
    public Map<String, String> getAdmins() {
        return adminService.getAllAdmins();
    }

    @Override
    public void blockUser(RequestUserNameDto requestUserNameDto) {
        User user = userService.loadUserByUsername(requestUserNameDto.getEmail());
        user.setBlocked(true);
        userService.saveUser(user);
    }
}
