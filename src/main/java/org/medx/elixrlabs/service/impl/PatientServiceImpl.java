package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

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
    public ResponsePatientDto createPatient(UserDto userDto) {
        logger.debug("Attempting to create Patient for email: {}", userDto.getEmail());
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)));
        Patient newPatient = Patient.builder().user(user).build();
        Patient savedPatient;
        try {
            savedPatient = patientRepository.save(newPatient);
            logger.info("Successfully created new Patient for email: {}", userDto.getEmail());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("User with same mail already exists!");
        } catch (Exception e) {
            logger.error("Error while creating Patient with email:{}", userDto.getEmail());
            throw new LabException("Error while saving Patient with email: " + userDto.getEmail(), e);
        }
        return PatientMapper.toPatientDto(savedPatient);
    }

    @Override
    public ResponsePatientDto updatePatient(UserDto userDto) {
        logger.debug("Attempting to update Patient for email: {}", userDto.getEmail());
        Patient patient = getPatientByEmail(userDto.getEmail());
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUUID(patient.getUser().getUUID());
        patient.setUser(user);
        Patient resultPatient;
        try {
            resultPatient = patientRepository.save(patient);
            logger.info("Successfully updated Patient for email: {}", userDto.getEmail());
        } catch (Exception e) {
            logger.error("Error while updating Patient with email:{}", userDto.getEmail());
            throw new LabException("Error while saving Patient with email: " + userDto.getEmail(), e);
        }
        return PatientMapper.toPatientDto(resultPatient);
    }

    @Override
    public List<ResponsePatientDto> getAllPatients() {
        List<ResponsePatientDto> patientDtos;
        logger.debug("Attempting to fetch all patients");
        try {
            patientDtos = patientRepository.fetchAllPatients().stream()
                    .map(PatientMapper::toPatientDto)
                    .toList();
            logger.info("Successfully fetched all patients");
        } catch (Exception e) {
            logger.error("Error while fetching all patients");
            throw new LabException("Error while fetching all patients", e);
        }
        return patientDtos;
    }


    @Override
    public List<ResponseOrderDto> getOrders() {
        List<ResponseOrderDto> orderDtos;
        logger.debug("Attempting to fetch all orders");
        try {
            orderDtos = patientRepository.getPatientOrders(SecurityContextHelper.extractEmailFromContext())
                    .getOrders().stream()
                    .map(OrderMapper::toResponseOrderDto)
                    .toList();
            logger.info("Successfully fetched all orders");
        } catch (Exception e) {
            logger.error("Error while fetching all orders");
            throw new LabException("Error while fetching all orders", e);
        }
        return orderDtos;
    }

    @Override
    public TestResultDto getTestReport(Long orderId) {
        logger.debug("Attempting to fetch Test report with order Id: {}", orderId);
        TestResultDto testResultDto;
        if (isOrderOwnedByPatient(orderId)) {
            testResultDto = TestResultMapper.toTestResultDto(orderService.getOrder(orderId).getTestResult());
            logger.info("Successfully fetched Test report with order Id: {}", orderId);
        } else {
            logger.warn("Invalid order ID entered: {}", orderId);
            throw new NoSuchElementException("Invalid order ID entered: " + orderId);
        }
        return testResultDto;
    }

    @Override
    public void deletePatient() {
        String email = SecurityContextHelper.extractEmailFromContext();
        Patient patient = getPatientByEmail(email);
        logger.debug("Attempting to delete patient with Id: {}", email);
        if (patient == null) {
            logger.warn("Patient not found with email: {}", email);
            throw new NoSuchElementException("Patient not found with email: " + email);
        }
        patient.setDeleted(true);
        try {
            patientRepository.save(patient);
            logger.info("Successfully deleted the patient with Id: {}", email);
        } catch (Exception e) {
            logger.error("Error while deleting Patient with email: {}", email);
            throw new LabException("Error while deleting Patient with email: " + email);
        }
    }

    @Override
    public List<ResponseOrderDto> getOrdersByPatient(RequestUserNameDto patient) {
        String email = patient.getEmail();
        List<ResponseOrderDto> orderDtos;
        logger.debug("Attempting to get orders by patient with email: {}", email);
        try {
            orderDtos = patientRepository.getPatientOrders(email).getOrders().stream()
                    .map(OrderMapper::toResponseOrderDto)
                    .toList();
            logger.info("Successfully fetched orders of patient with email: {}", email);
        } catch (Exception e) {
            logger.error("Error while getting orders of patient with email: {}", email);
            throw new LabException("Error while getting orders of patient with email: " + email, e);
        }
        return orderDtos;
    }

    @Override
    public Patient getPatientByEmail(String email) {
        logger.debug("Attempting to get patient with email: {}", email);
        Patient patient;
        try {
            patient = patientRepository.findByEmailAndIsDeletedFalse(email);
        } catch (Exception e) {
            logger.error("Error while getting patient with email: {}", email);
            throw new LabException("Error while getting patient with email: " + email, e);
        }
        if (null == patient) {
            logger.warn("Patient not found while retrieving with email: {}", email);
            throw new NoSuchElementException("Patient not found while retrieving with email: " + email);
        }
        logger.info("Successfully fetched patient with email: {}", email);
        return patient;
    }

    private boolean isOrderOwnedByPatient(Long orderId) {
        String email = SecurityContextHelper.extractEmailFromContext();
        logger.debug("Checking that order is owned by patient using order Id {} and patient email {}", orderId, email);
        return orderService.getOrder(orderId).getPatient().getUser().getUsername().equals(email);
    }
}
