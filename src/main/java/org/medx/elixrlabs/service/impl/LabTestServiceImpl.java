package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.Mapper.LabTestMapper;
import org.medx.elixrlabs.dto.CreateAndRetrieveLabTestDto;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.repository.LabTestRepository;
import org.medx.elixrlabs.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabTestServiceImpl implements LabTestService {

    @Autowired
    LabTestRepository labTestRepository;

    @Override
    public CreateAndRetrieveLabTestDto createLabTest(CreateAndRetrieveLabTestDto labTestDto) {
        LabTest labTest = LabTestMapper.toLabTest(labTestDto);
        return LabTestMapper.toRetrieveLabTestDto(labTestRepository.save(labTest));
    }

    @Override
    public List<CreateAndRetrieveLabTestDto> getAllLabTests() {
        List<CreateAndRetrieveLabTestDto> labTestDtos = new ArrayList<>();
        List<LabTest> labTests = labTestRepository.findAllLabTests();
        if (null == labTests) {
            return labTestDtos;
        }
        for (LabTest labTest : labTestRepository.findAllLabTests()) {
            labTestDtos.add(LabTestMapper.toRetrieveLabTestDto(labTest));
        }
        return labTestDtos;
    }

    @Override
    public CreateAndRetrieveLabTestDto getLabTestById(long id) {
        LabTest labTest = labTestRepository.findLabTestById(id);
        if (null == labTest) {
            throw new NullPointerException("Lab Test Not Found");
        }
        return LabTestMapper.toRetrieveLabTestDto(labTest);
    }

    @Override
    public CreateAndRetrieveLabTestDto updateLabTestById(long id, CreateAndRetrieveLabTestDto labTestDto) {
        LabTest labTest = labTestRepository.findLabTestById(id);
        if (null == labTest) {
            throw new NullPointerException("Lab test Not Found");
        }
        labTest = LabTest.builder()
                .name(labTestDto.getName())
                .price(labTestDto.getPrice())
                .defaultValue(labTestDto.getDefaultValue())
                .description(labTestDto.getDescription())
                .build();

        return LabTestMapper.toRetrieveLabTestDto(labTestRepository.save(labTest));
    }

    @Override
    public boolean removeLabTestById(long id) {
        LabTest labTest = labTestRepository.findLabTestById(id);
        if (null == labTest) {
            throw new NullPointerException("Lab test Not Found");
        }
        return true;
    }
}
