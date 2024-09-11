package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.util.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.repository.AdminRepository;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.service.AdminService;

/**
 * <p>
 * Service implementation for managing Admin-related operations.
 * This {@code AdminServiceImpl} contains business logic for handling User operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Admin getAdminByEmail(String email) {
        Admin admin;
        try {
            admin = adminRepository.findByEmail(email);
        } catch (Exception e) {
            logger.warn("Error while fetching admin from the given mail : {}", email);
            throw new LabException("Error while fetching admin from the given mail : " + email);
        }
        return admin;
    }

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        User user = User.builder()
                .email(adminDto.getEmail())
                .password(bCryptPasswordEncoder.encode(adminDto.getPassword()))
                .place(adminDto.getPlace())
                .roles(List.of(roleService.getRoleByName(RoleEnum.ROLE_ADMIN)))
                .build();
        Admin admin = Admin.builder()
                .user(user)
                .build();
        logger.info("Attempting to create Admin with email : {}", adminDto.getEmail());
        Admin savedAdmin = null;
        try {
            savedAdmin = adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Admin already exists with username : {}", adminDto.getEmail());
            throw new DataIntegrityViolationException("Admin already exists with the email id : " + adminDto.getEmail());
        } catch (Exception e) {
            logger.error("Error occurred while saving admin with email : {}", adminDto.getEmail());
            throw new LabException("Error occurred while saving admin with email : " + adminDto.getEmail(), e);
        }
        return AdminDto.builder().email(savedAdmin.getUser().getEmail()).place(savedAdmin.getUser().getPlace()).build();
    }

    @Override
    public AdminDto updateAdmin(AdminDto adminDto) {
        Admin admin = getAdminByEmail(adminDto.getEmail());
        User user = User.builder()
                .email(adminDto.getEmail())
                .password(bCryptPasswordEncoder.encode(adminDto.getPassword()))
                .place(adminDto.getPlace())
                .roles(List.of(roleService.getRoleByName(RoleEnum.ROLE_ADMIN)))
                .build();
        admin.setUser(user);
        logger.info("Attempting to update Admin with email : {}", adminDto.getEmail());
        Admin savedAdmin;
        try {
            savedAdmin = adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Admin already exists with username : {}", adminDto.getEmail());
            throw new DataIntegrityViolationException("Admin already exists with the email id : " + adminDto.getEmail());
        } catch (Exception e) {
            logger.error("Error occurred while updating admin with email : {}", adminDto.getEmail());
            throw new LabException("Error occurred while updating admin with email : " + adminDto.getEmail(), e);
        }
        return AdminDto.builder().email(savedAdmin.getUser().getEmail()).place(savedAdmin.getUser().getPlace()).build();
    }

    @Override
    public void deleteAdmin(RequestUserNameDto userNameDto) {
        Admin admin = getAdminByEmail(userNameDto.getEmail());
        admin.setDeleted(true);
        adminRepository.save(admin);
    }


    @Override
    public Map<String, String> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().collect(Collectors
                .toMap(
                        x -> x.getUser().getEmail(),
                        x -> (x.isDeleted() ? "Deleted" : "Not Deleted")
                )
        );
    }


}
