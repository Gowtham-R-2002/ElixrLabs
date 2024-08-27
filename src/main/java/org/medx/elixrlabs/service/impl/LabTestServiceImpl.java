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
 *
 * @author Deolin Jaffens
 * @version 1.0
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

    public void setupInitialData() {
        LabTest bloodTest = LabTest.builder()
                .name("Blood test")
                .description("Simple Blood test")
                .price(150)
                .defaultValue("BPC : 1000")
                .build();

        LabTest HIVTest = LabTest.builder()
                .name("HIV Test")
                .description("Test to identify HIV status")
                .price(500)
                .defaultValue("Status : negative")
                .build();

        LabTest cancerTest = LabTest.builder()
                .name("Cancer Test")
                .description("Test to detect Cancer presence")
                .price(1000)
                .defaultValue("Cell count : 500")
                .build();
        try {
            labTestRepository.save(bloodTest);
            labTestRepository.save(HIVTest);
            labTestRepository.save(cancerTest);
        } catch (Exception e) {
            System.out.println("Already exists..." + e.getMessage());
        }
    }
}
