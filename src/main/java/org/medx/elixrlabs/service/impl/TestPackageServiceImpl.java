package org.medx.elixrlabs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.medx.elixrlabs.dto.RequestTestPackageDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.mapper.TestPackageMapper;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.repository.TestPackageRepository;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.TestPackageService;

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

    private static final Logger logger = LoggerFactory.getLogger(TestPackageServiceImpl.class);

    @Autowired
    private TestPackageRepository testPackageRepository;

    @Autowired
    private LabTestService labTestService;

    @Override
    public ResponseTestPackageDto createOrUpdateTestPackage(RequestTestPackageDto requestTestPackageDto) {
        logger.info("Creating or updating TestPackage with name: {}", requestTestPackageDto.getName());
        TestPackage testPackage = TestPackageMapper.toTestPackage(requestTestPackageDto);
        TestPackage existingTestPackage = getTestPackage(requestTestPackageDto.getId());
        List<LabTest> tests;
        TestPackage savedTestPackage;
        tests = requestTestPackageDto.getLabTestIds().stream()
                .map(testId -> LabTestMapper
                        .toLabTest(labTestService
                                .getLabTestById(testId)))
                .toList();
        double price = tests.stream()
                .mapToDouble(LabTest::getPrice)
                .sum();
        double offerPrice = price * 0.8;
        testPackage.setPrice(offerPrice);
        testPackage.setTests(tests);
        if (existingTestPackage != null){
            testPackage.setId(existingTestPackage.getId());
        }
        try {
            savedTestPackage = testPackageRepository.save(testPackage);
            logger.info("Successfully saved TestPackage with name: {}", requestTestPackageDto.getName());
        } catch (Exception e) {
            logger.warn("Failed to create or update TestPackage: {}", requestTestPackageDto.getName());
            throw new LabException("Error occurred while saving TestPackage" + requestTestPackageDto.getName(), e);
        }
        return TestPackageMapper.toTestPackageDto(savedTestPackage);
    }

    private TestPackage getTestPackage(Long id) {
        TestPackage testPackage;
        try {
            testPackage = testPackageRepository.findByIdAndIsDeletedFalse(id);
        } catch (Exception e) {
            throw new NoSuchElementException("Error while fetching test package with ID : " + id);
        }
        return testPackage;
    }

    @Override
    public List<ResponseTestPackageDto> getAllTestPackages() {
        logger.debug("Retrieving all non-deleted TestPackages");
        List<ResponseTestPackageDto> testPackageDtos = new ArrayList<>();
        try {
            List<TestPackage> testPackages = testPackageRepository.findByIsDeletedFalse();
            if (testPackages != null) {
                for (TestPackage testPackage : testPackages) {
                    testPackageDtos.add(TestPackageMapper.toTestPackageDto(testPackage));
                }
            }
            logger.info("Successfully retrieved {} TestPackages", testPackageDtos.size());
        } catch (Exception e) {
            logger.warn("Failed to retrieve TestPackages");
            throw new LabException("Error occurred while retrieving TestPackages", e);
        }
        return testPackageDtos;
    }

    @Override
    public ResponseTestPackageDto getTestPackageById(long id) {
        logger.debug("Retrieving TestPackage by ID: {}", id);
        TestPackage testPackage;
        try {
            testPackage = testPackageRepository.findByIdAndIsDeletedFalse(id);
            logger.info("Successfully retrieved TestPackage with ID: {}", id);
        } catch (Exception e) {
            logger.warn("Failed to retrieve TestPackage by ID: {}", id);
            throw new LabException("Error occurred while retrieving TestPackage" + id, e);
        }
        if (testPackage == null) {
            logger.warn("TestPackage not found for ID: {}", id);
            throw new NoSuchElementException("TestPackage not found with ID: " + id);
        }
        return TestPackageMapper.toTestPackageDto(testPackage);
    }

    @Override
    public boolean deleteTestPackageById(long id) {
        logger.info("Deleting TestPackage by ID: {}", id);
        TestPackage testPackage = testPackageRepository.findByIdAndIsDeletedFalse(id);
        if (testPackage == null) {
            logger.warn("TestPackage not found for deletion with ID: {}", id);
            throw new NoSuchElementException("TestPackage not found with ID: " + id);
        }
        try {
            testPackage.setDeleted(true);
            testPackageRepository.save(testPackage);
            logger.info("Successfully marked TestPackage as deleted with ID: {}", id);
            return true;
        } catch (Exception e) {
            logger.warn("Failed to delete TestPackage by ID: {}", id);
            throw new LabException("Error occurred while deleting TestPackage" + id, e);
        }
    }
}
