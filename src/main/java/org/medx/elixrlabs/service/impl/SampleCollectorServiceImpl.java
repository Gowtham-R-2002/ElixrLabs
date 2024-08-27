package org.medx.elixrlabs.service.impl;

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
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Autowired
    private SampleCollectorRepository sampleCollectorRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public SampleCollectorDto createOrUpdateSampleCollector(UserDto userDto) {
        SampleCollector existingSampleCollector = getSampleCollectorByEmail(userDto.getEmail());
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(
                List.of(
                        roleService.getRoleByName(RoleEnum.ROLE_SAMPLE_COLLECTOR),
                        roleService.getRoleByName(RoleEnum.ROLE_PATIENT)
                )
        );
        if (null != existingSampleCollector) {
            user.setUUID(existingSampleCollector.getUser().getUUID());
            existingSampleCollector.setUser(user);
            return SampleCollectorMapper.convertToSampleCollectorDto(sampleCollectorRepository.save(existingSampleCollector));
        }
        SampleCollector sampleCollector = SampleCollector.builder()
                .user(user)
                .isVerified(false)
                .build();
        SampleCollector savedSampleCollector = sampleCollectorRepository.save(sampleCollector);
        return SampleCollectorMapper.convertToSampleCollectorDto(savedSampleCollector);
    }

    @Override
    public boolean deleteSampleCollector() {
        SampleCollector sampleCollector = getSampleCollectorByEmail(SecurityContextHelper.extractEmailFromContext());
        sampleCollector.getUser().setDeleted(true);
        sampleCollectorRepository.save(sampleCollector);
        return true;
    }

    @Override
    public SampleCollector getSampleCollectorByEmail(String email) {
        SampleCollector sampleCollector;
        try {
            sampleCollector = sampleCollectorRepository.getSampleCollectorByEmail(email);
            if (sampleCollector == null) {
                throw new NoSuchElementException("No sampleCollector found with email : " + email);
            }
        } catch (Exception e) {
            throw new NoSuchElementException("Error while getting sampleCollector with email : " + email);
        }
        return sampleCollector;
    }

    @Override
    public List<SampleCollectorDto> getAllSampleCollector(){
        List<SampleCollector> sampleCollectors;
        List<SampleCollectorDto> SampleCollectorDtos= new ArrayList<>();
        try {
            sampleCollectors = sampleCollectorRepository.getAllSampleCollector();
            for (SampleCollector sampleCollector : sampleCollectors) {
                SampleCollectorDtos.add(SampleCollectorMapper.convertToSampleCollectorDto(sampleCollector));
            }
        } catch (Exception e) {
            throw new NoSuchElementException("There is no Sample Collectors");
        }
        return SampleCollectorDtos;
    }

    public List<SampleCollector> getSampleCollectorByPlace(LocationEnum place) {
        List<SampleCollector> sampleCollectors;
        try {
            sampleCollectors = sampleCollectorRepository.getSampleCollectorByPlace(place);
        } catch (Exception e) {
            throw new NoSuchElementException("Error while getting sampleCollector with place : " + place);
        }
        return sampleCollectors;
    }

    @Override
    public SampleCollectorDto getSampleCollectorById(Long id) {
        return null;
    }

}
