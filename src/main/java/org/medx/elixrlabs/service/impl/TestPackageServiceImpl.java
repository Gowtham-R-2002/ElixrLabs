package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.Mapper.TestPackageMapper;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.repository.TestPackageRepository;
import org.medx.elixrlabs.service.TestPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestPackageServiceImpl implements TestPackageService {


    @Autowired
    TestPackageRepository TestPackageRepository;

    @Override
    public ResponseTestPackageDto createOrUpdateTest(TestPackageDto TestPackageDto) {
        TestPackage TestPackage = TestPackageMapper.toTestPackage(TestPackageDto);
        return TestPackageMapper.toTestPackageDto(TestPackageRepository.save(TestPackage));
    }

    @Override
    public List<ResponseTestPackageDto> getAllTestPackages() {
        List<ResponseTestPackageDto> TestPackageDtos = new ArrayList<>();
        List<TestPackage> TestPackages = TestPackageRepository.findByIsDeletedFalse();
        if (null == TestPackages) {
            return TestPackageDtos;
        }
        for (TestPackage TestPackage : TestPackageRepository.findByIsDeletedFalse()) {
            TestPackageDtos.add(TestPackageMapper.toTestPackageDto(TestPackage));
        }
        return TestPackageDtos;
    }

    @Override
    public ResponseTestPackageDto getTestPackageById(long id) {
        TestPackage TestPackage = TestPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == TestPackage) {
            throw new NullPointerException("Lab Test Not Found");
        }
        return TestPackageMapper.toTestPackageDto(TestPackage);
    }

    @Override
    public boolean removeTestPackageById(long id) {
        TestPackage TestPackage = TestPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == TestPackage) {
            throw new NullPointerException("Lab test Not Found");
        }
        TestPackage.setDeleted(true);
        TestPackageRepository.save(TestPackage);
        return true;
    }
}
