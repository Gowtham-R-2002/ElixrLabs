package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.repository.LabTestRepository;
import org.medx.elixrlabs.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Service implementation for managing LabTestService-related operations.
 * This class contains business logic for handling LabTestService entities, including
 * creation, retrieval, update, deletion, and some more operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class LabTestServiceImpl implements LabTestService {

    @Autowired
    LabTestRepository labTestRepository;

    @Override
    public LabTestDto createOrUpdateTest(LabTestDto labTestDto) {
        LabTest labTest = LabTestMapper.toLabTest(labTestDto);
        return LabTestMapper.toRetrieveLabTestDto(labTestRepository.save(labTest));
    }

    @Override
    public List<LabTestDto> getAllLabTests() {
        List<LabTestDto> labTestDtos = new ArrayList<>();
        List<LabTest> labTests = labTestRepository.findByIsDeletedFalse();
        if (null == labTests) {
            return labTestDtos;
        }
        for (LabTest labTest : labTestRepository.findByIsDeletedFalse()) {
            labTestDtos.add(LabTestMapper.toRetrieveLabTestDto(labTest));
        }
        return labTestDtos;
    }

    @Override
    public LabTestDto getLabTestById(long id) {
        LabTest labTest = labTestRepository.findByIdAndIsDeletedFalse(id);
        if (null == labTest) {
            throw new NullPointerException("Lab Test Not Found");
        }
        return LabTestMapper.toRetrieveLabTestDto(labTest);
    }

    @Override
    public boolean removeLabTestById(long id) {
        LabTest labTest = labTestRepository.findByIdAndIsDeletedFalse(id);
        if (null == labTest) {
            throw new NullPointerException("Lab test Not Found");
        }
        labTest.setDeleted(true);
        labTestRepository.save(labTest);
        return true;
    }
}
