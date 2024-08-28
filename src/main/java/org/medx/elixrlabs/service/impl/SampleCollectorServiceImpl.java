package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.AppointmentDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.SampleCollectorMapper;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.AppointmentSlot;
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
    private JwtService jwtService;

    @Autowired
    private AppointmentSlotServiceImpl appointmentSlotService;

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
        } catch (Exception e) {
            throw new NoSuchElementException("Error while getting sampleCollector with email : " + email);
        }
        return sampleCollector;
    }

    @Override
    public List<SampleCollectorDto> getSampleCollectors(){
        List<SampleCollectorDto> SampleCollectorDtos= new ArrayList<>();
        try {
            for (SampleCollector sampleCollector : sampleCollectorRepository.getAllSampleCollector()) {
                SampleCollectorDtos.add(SampleCollectorMapper.convertToSampleCollectorDto(sampleCollector));
            }
        } catch (Exception e) {
            throw new NoSuchElementException("There is no Sample Collectors");
        }
        return SampleCollectorDtos;
    }

    public List<SampleCollector> getSampleCollectorByPlace(LocationEnum place) {
        List<SampleCollector> sampleCollector;
        try {
            sampleCollector = sampleCollectorRepository.getSampleCollectorsByPlace(place);
        } catch (Exception e) {
            throw new NoSuchElementException("Error while getting sampleCollector with place : " + place);
        }
        return sampleCollector;
    }

    @Override
    public SampleCollectorDto getSampleCollectorById(Long id) {
        return null;
    }

    public List<AppointmentDto> getAppointmentByPlace(AppointmentDto appointmentDto) {
        String place = jwtService.getAddress();
        List<AppointmentSlot> appointmentSlots = appointmentSlotService.getAppointmentsByPlace(LocationEnum.valueOf(place), appointmentDto.getAppointmentDate());

        return appointmentSlots.stream()
                .map(slot -> AppointmentDto.builder()
                        .appointmentDate(slot.getDateSlot())
                        .userName(slot.getUser().getUsername())
                        .timeSlot(slot.getTimeSlot())
                        .appointmentId(slot.getId())
                        .build()).toList();
    }

    @Override
    public void verifySampleCollector(String email) {
        SampleCollector sampleCollector = sampleCollectorRepository.getSampleCollectorByEmail(email);
        sampleCollector.setVerified(true);
        sampleCollectorRepository.save(sampleCollector);
    }

    @Override
    public List<SampleCollectorDto> getAllSampleCollectors() {
        List<SampleCollectorDto> SampleCollectorDtos= new ArrayList<>();
        try {
            for (SampleCollector sampleCollector : sampleCollectorRepository.findAll()) {
                SampleCollectorDtos.add(SampleCollectorMapper.convertToSampleCollectorDto(sampleCollector));
            }
        } catch (Exception e) {
            throw new NoSuchElementException("There is no Sample Collectors");
        }
        return SampleCollectorDtos;
    }

    public void assignSampleCollectorToAppointment() {

    }

}
