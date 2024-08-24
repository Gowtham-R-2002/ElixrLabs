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

@Service
public class TestPackageServiceImpl implements TestPackageService {

    @Autowired
    private LabTestService labTestService;

    @Autowired
    private TestPackageRepository testPackageRepository;

    @Override
    public ResponseTestPackageDto createOrUpdateTest(TestPackageDto testPackageDto) {
        TestPackage testPackage = TestPackageMapper.toTestPackage(testPackageDto);
        List<LabTest> tests = new ArrayList<>();
        for (Long i : testPackageDto.getLabTestIds()) {
            tests.add(LabTestMapper.toLabTest(labTestService.getLabTestById(i)));
        }
        testPackage.setTests(tests);
        return TestPackageMapper.toTestPackageDto(testPackageRepository.save(testPackage));
    }

    @Override
    public List<ResponseTestPackageDto> getAllTestPackages() {
        List<ResponseTestPackageDto> testPackageDtos = new ArrayList<>();
        List<TestPackage> TestPackages = testPackageRepository.findByIsDeletedFalse();
        if (null == TestPackages) {
            return testPackageDtos;
        }
        for (TestPackage testPackage : testPackageRepository.findByIsDeletedFalse()) {
            testPackageDtos.add(TestPackageMapper.toTestPackageDto(testPackage));
        }
        return testPackageDtos;
    }

    @Override
    public ResponseTestPackageDto getTestPackageById(long id) {
        TestPackage testPackage = testPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == testPackage) {
            throw new NullPointerException("Lab Test Not Found");
        }
        return TestPackageMapper.toTestPackageDto(testPackage);
    }

    @Override
    public boolean removeTestPackageById(long id) {
        TestPackage testPackage = testPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == testPackage) {
            throw new NullPointerException("Lab test Not Found");
        }
        testPackage.setDeleted(true);
        testPackageRepository.save(testPackage);
        return true;
    }
}
