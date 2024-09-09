package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.service.SuperAdminService;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

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
