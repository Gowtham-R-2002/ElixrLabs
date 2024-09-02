package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.ResponsePatientDto;
import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.mapper.PatientMapper;
import org.medx.elixrlabs.mapper.TestResultMapper;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.PatientRepository;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.service.TestResultService;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.RoleEnum;

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
    private PatientRepository patientRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponsePatientDto createOrUpdatePatient(UserDto userDto) {
        Patient patient = getPatientByEmail(userDto.getEmail());
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)));
        if (patient != null) {
            user.setUUID(patient.getUser().getUUID());
            patient.setUser(user);
            return PatientMapper.toPatientDto(patientRepository.save(patient));
        }
        Patient newPatient = Patient.builder().user(user).build();
        Patient savedPatient;
        try {
            savedPatient = patientRepository.save(newPatient);
        } catch (Exception e) {
            throw new LabException("Error while saving Patient with email: " + userDto.getEmail(), e);
        }
        return PatientMapper.toPatientDto(savedPatient);
    }

    @Override
    public List<ResponsePatientDto> getAllPatients() {
        return patientRepository.fetchAllPatients().stream()
                .map(PatientMapper::toPatientDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ResponseOrderDto> getOrders() {
        return patientRepository.getPatientOrders(SecurityContextHelper.extractEmailFromContext())
                .getOrders().stream()
                .map(OrderMapper::toResponseOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public TestResultDto getTestReport(Long orderId) {
        return TestResultMapper.toTestResultDto(orderService.getOrder(orderId).getTestResult());
    }

    @Override
    public void deletePatient(String email) {
        Patient patient = getPatientByEmail(email);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found with email: " + email);
        }
        patient.setDeleted(true);
        patientRepository.save(patient);
    }

    @Override
    public List<ResponseOrderDto> getOrdersByPatient(RequestUserNameDto patient) {
        Patient existingPatient = patientRepository.getPatientOrders(patient.getEmail());
        return existingPatient.getOrders().stream()
                .map(OrderMapper::toResponseOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Patient getPatientByEmail(String email) {
        try {
            return patientRepository.findByEmailAndIsDeletedFalse(email);
        } catch (Exception e) {
            throw new LabException("Error while getting patient with email: " + email, e);
        }
    }

    @Override
    public void setupInitialData() {
        User user = User.builder()
                .gender(GenderEnum.M)
                .place(LocationEnum.MARINA)
                .password("gow@123")
                .email("ergowthamramesh@gmail.com")
                .roles(List.of(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)))
                .phoneNumber("8531911113")
                .build();
        Patient patient = Patient.builder()
                .user(user)
                .build();
        try {
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException("Error in initializing patient data" + e.getMessage());
        }
    }
}
