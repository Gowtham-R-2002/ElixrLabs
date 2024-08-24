package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.mapper.SampleCollectorMapper;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.SampleCollectorRepository;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SampleCollectorServiceImpl implements SampleCollectorService {

    @Autowired
    private SampleCollectorRepository sampleCollectorRepository;

    @Override
    public SampleCollectorDto createOrUpdateSampleCollector(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        SampleCollector sampleCollector = SampleCollector.builder()
                .user(user)
                .isVerified(false)
                .build();
        if (null != getSampleCollectorByEmail(user.getEmail())) {
            sampleCollector.getUser()
                    .setUUID(getSampleCollectorByEmail
                            (user.getEmail()).getUser()
                            .getUUID());
        }
        SampleCollector savedSampleCollector = sampleCollectorRepository.save(sampleCollector);

        return SampleCollectorMapper.convertToSampleCollectorDto(savedSampleCollector);
    }

    @Override
    public boolean deleteSampleCollector(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        SampleCollectorDto sampleCollectorDto = getSampleCollectorByEmail(user.getEmail());
        if (null == sampleCollectorDto) {
            throw new NoSuchElementException("While deleting there is no Such Sample Collector : " + userDto.getEmail());
        }
        sampleCollectorDto.getUser().setDeleted(true);
        return true;
    }

    @Override
    public SampleCollectorDto getSampleCollectorByEmail(String email) {
        SampleCollector sampleCollector;
        try {
            sampleCollector = sampleCollectorRepository.getSampleCollectorByEmail(email);
        } catch (Exception e) {
            throw new LabException("Error while getting sampleCollector with email : " + email);
        }
        return SampleCollectorMapper.convertToSampleCollectorDto(sampleCollector);
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

    @Override
    public SampleCollectorDto getSampleCollectorById(Long id) {
        return null;
    }

}
