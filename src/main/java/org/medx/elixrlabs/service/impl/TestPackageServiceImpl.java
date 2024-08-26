package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.mapper.TestPackageMapper;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.repository.TestPackageRepository;
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
    public boolean deleteTestPackageById(long id) {
        TestPackage TestPackage = TestPackageRepository.findByIdAndIsDeletedFalse(id);
        if (null == TestPackage) {
            throw new NullPointerException("Lab test Not Found");
        }
        TestPackage.setDeleted(true);
        TestPackageRepository.save(TestPackage);
        return true;
    }
}
