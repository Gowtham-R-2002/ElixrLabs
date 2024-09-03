package org.medx.elixrlabs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.medx.elixrlabs.util.RoleEnum;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.SampleCollectorMapper;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.SampleCollectorRepository;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.LocationEnum;

/**
 * <p>
 * Service implementation for managing SampleCollector-related operations.
 * This class contains business logic for handling SampleCollector entities, including
 * creation, retrieval, update, deletion, and assignment operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class SampleCollectorServiceImpl implements SampleCollectorService {

    private static final Logger logger = LoggerFactory.getLogger(SampleCollectorServiceImpl.class);

    @Autowired
    private SampleCollectorRepository sampleCollectorRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public SampleCollectorDto createOrUpdateSampleCollector(UserDto userDto) {
        logger.info("Attempting to create or update SampleCollector for email: {}", userDto.getEmail());
        SampleCollector existingSampleCollector = getSampleCollectorByEmail(userDto.getEmail());
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(
                List.of(
                        roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR),
                        roleService.getRoleByName(RoleEnum.ROLE_PATIENT)
                )
        );
        if (existingSampleCollector != null) {
            user.setUUID(existingSampleCollector.getUser().getUUID());
            existingSampleCollector.setUser(user);
            SampleCollector result;
            try {
                result = sampleCollectorRepository.save(existingSampleCollector);
                logger.info("Successfully Updated existing SampleCollector with email: {}", userDto.getEmail());
            } catch (Exception e) {
                logger.warn("Error while updating SampleCollector with email: {}", userDto.getEmail());
                throw new LabException("Error while updating SampleCollector with email: " + userDto.getEmail(), e);
            }
            return SampleCollectorMapper.convertToSampleCollectorDto(result);
        }
        SampleCollector sampleCollector = SampleCollector.builder()
                .user(user)
                .isVerified(false)
                .build();
        SampleCollector savedSampleCollector;
        try {
            savedSampleCollector = sampleCollectorRepository.save(sampleCollector);
            logger.info("Successfully Created SampleCollector with email: {}", userDto.getEmail());
        } catch (Exception e) {
            logger.warn("Error while creating SampleCollector with email: {}", userDto.getEmail());
            throw new LabException("Error while saving SampleCollector with email: " + userDto.getEmail(), e);
        }
        return SampleCollectorMapper.convertToSampleCollectorDto(savedSampleCollector);
    }

    @Override
    public boolean deleteSampleCollector() {
        logger.info("Attempting to delete SampleCollector for current user");
        SampleCollector sampleCollector = getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext());
        sampleCollector.setDeleted(true);
        try {
            sampleCollectorRepository.save(sampleCollector);
            logger.info("Successfully marked SampleCollector as deleted for user email: {}", SecurityContextHelper.extractEmailFromContext());
        } catch (Exception e) {
            logger.warn("Error while deleting SampleCollector with email: {}", SecurityContextHelper.extractEmailFromContext());
            throw new LabException("Error while deleting SampleCollector with email: " + SecurityContextHelper.extractEmailFromContext());
        }
        return true;
    }

    @Override
    public SampleCollector getSampleCollectorByEmail(String email) {
        logger.debug("Retrieving SampleCollector by email: {}", email);
        SampleCollector sampleCollector;
        try {
            sampleCollector = sampleCollectorRepository.getSampleCollectorByEmail(email);
        } catch (Exception e) {
            logger.warn("Error occurred while retrieving SampleCollector by email: {}", email);
            throw new NoSuchElementException("Error while getting sampleCollector with email: " + email);
        }
        return sampleCollector;
    }

    @Override
    public List<SampleCollectorDto> getSampleCollectors() {
        logger.debug("Retrieving all SampleCollectors which is verified by admin");
        List<SampleCollectorDto> sampleCollectorDtos = new ArrayList<>();
        try {
            for (SampleCollector sampleCollector : sampleCollectorRepository.getAllSampleCollector()) {
                sampleCollectorDtos.add(SampleCollectorMapper
                        .convertToSampleCollectorDto(sampleCollector));
            }
        } catch (Exception e) {
            logger.warn("Error occurred while retrieving all SampleCollectors", e);
            throw new NoSuchElementException("There is no Sample Collectors");
        }
        return sampleCollectorDtos;
    }

    @Override
    public List<SampleCollector> getSampleCollectorByPlace(LocationEnum place) {
        logger.debug("Retrieving SampleCollectors by place: {}", place);
        List<SampleCollector> sampleCollectors;
        try {
            sampleCollectors = sampleCollectorRepository.getSampleCollectorsByPlace(place);
        } catch (Exception e) {
            logger.warn("Error occurred while retrieving SampleCollectors by place: {}", place);
            throw new NoSuchElementException("Error while getting SampleCollector with place: " + place);
        }
        return sampleCollectors;
    }

    @Override
    public void verifySampleCollector(String email) {
        logger.info("Verifying SampleCollector with email: {}", email);
        SampleCollector sampleCollector = sampleCollectorRepository.getSampleCollectorByEmail(email);
        sampleCollector.setVerified(true);
        sampleCollectorRepository.save(sampleCollector);
        logger.info("Successfully verified SampleCollector with email: {}", email);
    }

    @Override
    public List<SampleCollectorDto> getAllSampleCollectors() {
        logger.debug("Retrieving all SampleCollectors");
        List<SampleCollectorDto> sampleCollectorDtos = new ArrayList<>();
        try {
            for (SampleCollector sampleCollector : sampleCollectorRepository.findAll()) {
                sampleCollectorDtos.add(SampleCollectorMapper.convertToSampleCollectorDto(sampleCollector));
            }
        } catch (Exception e) {
            logger.warn("Error occurred while retrieving all SampleCollectors", e);
            throw new NoSuchElementException("There is no Sample Collectors");
        }
        return sampleCollectorDtos;
    }
}
