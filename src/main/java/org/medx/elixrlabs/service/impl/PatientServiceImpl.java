package org.medx.elixrlabs.service.impl;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.medx.elixrlabs.dto.OrderSuccessDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Service implementation for managing Patient-related operations.
 * This class contains business logic for handling Patient entities, including
 * creation, retrieval, update, deletion, and some more operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleServiceimpl;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createOrUpdatePatient(UserDto userDto) {
        User existingUser = getPatientByEmail(userDto.getEmail());
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleServiceimpl.getRoleByName(RoleEnum.ROLE_PATIENT)));
        if (null != existingUser) {
            user.setUUID(existingUser.getUUID());
        }
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new LabException("Error while saving Patient of email : " + userDto.getEmail());
        }
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public List<UserDto> getAllPatients() {
        return userRepository.fetchAllPatients().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookSlotAndReturnStatus() {
        return false;
    }

    @Override
    public List<ResponseOrderDto> getOrders() {
        System.out.println(userRepository.getPatientOrders(SecurityContextHelper.extractEmailFromContext()).getOrders());
        return userRepository.getPatientOrders(SecurityContextHelper.extractEmailFromContext()).getOrders().stream().map(OrderMapper::toResponseOrderDto).toList();
    }

    @Override
    public TestResult getTestReport(Long orderId) {
        return null;
    }

    @Override
    public void deletePatient(String email) {
        User user = getPatientByEmail(email);
        if (null == user) {
            throw new NoSuchElementException("Patient not found with email : " + email);
        }
        user.setDeleted(true);
        userRepository.save(user);
    }

    public List<OrderSuccessDto> getOrdersByPatient(UserDto patientDto) {
        User patient =userRepository.getPatientOrders(patientDto.getEmail());
        return patient.getOrders().stream().map(OrderMapper::toOrderSuccessDto).toList();
    }

    @Override
    public User getPatientByEmail(String email) {
        User user;
        try {
            user = userRepository.findByEmailAndIsDeletedFalse(email);
        } catch (Exception e) {
            throw new LabException("Error while getting patient with email : " + email);
        }
        return user;
    }
}
