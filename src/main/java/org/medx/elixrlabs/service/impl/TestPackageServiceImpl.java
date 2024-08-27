package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.mapper.TestPackageMapper;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.repository.TestPackageRepository;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.TestPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Service implementation for managing TestPackage-related operations.
 * This class contains business logic for handling TestPackage entities, including
 * creation, retrieval, update, deletion, and some more operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class TestPackageServiceImpl implements TestPackageService {


    @Autowired
    TestPackageRepository TestPackageRepository;

    @Autowired
    LabTestService labTestService;

    @Override
    public ResponseTestPackageDto createOrUpdateTestPackage(TestPackageDto testPackageDto) {
        TestPackage testPackage = TestPackageMapper.toTestPackage(testPackageDto);
        List<LabTest> tests = new ArrayList<>();
        for(Long testId : testPackageDto.getLabTestIds()) {
            tests.add(LabTestMapper.toLabTest(labTestService.getLabTestById(testId)));
        }
        testPackage.setTests(tests);
        return TestPackageMapper.toTestPackageDto(TestPackageRepository.save(testPackage));
    }

    @Override
    public List<ResponseTestPackageDto> getAllTestPackages() {
        List<ResponseTestPackageDto> testPackageDtos = new ArrayList<>();
        List<TestPackage> testPackages = TestPackageRepository.findByIsDeletedFalse();
        if (null == testPackages) {
            return testPackageDtos;
        }
        for (TestPackage TestPackage : TestPackageRepository.findByIsDeletedFalse()) {
            testPackageDtos.add(TestPackageMapper.toTestPackageDto(TestPackage));
        }
        return testPackageDtos;
    }

    @Override
    public ResponseTestPackageDto getTestPackageById(long id) {
        TestPackage testPackage = TestPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == testPackage) {
            throw new NullPointerException("Lab Test Not Found");
        }
        return TestPackageMapper.toTestPackageDto(testPackage);
    }

    @Override
    public boolean deleteTestPackageById(long id) {
        TestPackage testPackage = TestPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == testPackage) {
            throw new NullPointerException("Lab test Not Found");
        }
        testPackage.setDeleted(true);
        TestPackageRepository.save(testPackage);
        return true;
    }
}
