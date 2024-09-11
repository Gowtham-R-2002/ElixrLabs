package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.repository.LabTestRepository;
import org.medx.elixrlabs.service.LabTestService;


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

    private static final Logger logger = LoggerFactory.getLogger(LabTestServiceImpl.class);

    @Autowired
    private LabTestRepository labTestRepository;

    @Override
    public LabTestDto createOrUpdateTest(LabTestDto labTestDto) {
        try {
            LabTest labTest = LabTestMapper.toLabTest(labTestDto);
            LabTest savedLabTest = labTestRepository.save(labTest);
            logger.info("Lab test created/updated successfully with id: {}", savedLabTest.getId());
            return LabTestMapper.toRetrieveLabTestDto(savedLabTest);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Test with name : {} already exists !", labTestDto.getName());
            throw new DataIntegrityViolationException("Test with name : " +labTestDto.getName() + " already exists !");
        } catch (Exception e) {
            logger.warn("Error while creating/updating lab test: {}", labTestDto.getName());
            throw new LabException("Error while creating/updating lab test: " + labTestDto.getName(), e);
        }
    }

    @Override
    public List<LabTestDto> getAllLabTests() {
        try {
            List<LabTest> labTests = labTestRepository.findByIsDeletedFalse();
            if (labTests.isEmpty()) {
                logger.info("No lab tests found.");
            } else {
                logger.info("Fetched {} lab tests.", labTests.size());
            }
            return labTests.stream()
                    .map(LabTestMapper::toRetrieveLabTestDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.warn("Error while fetching all lab tests: {}", e.getMessage());
            throw new LabException("Error while fetching all lab tests: ", e);
        }
    }

    @Override
    public LabTestDto getLabTestById(long id) {
        LabTest labTest = labTestRepository.findByIdAndIsDeletedFalse(id);
        if (labTest == null) {
            logger.warn("Lab test not found with id: {}", id);
            throw new NoSuchElementException("Lab Test Not Found with id " + id);
        }
        LabTestDto labTestDto;
        try {
            logger.info("Lab test retrieved successfully with id: {}", id);
            labTestDto = LabTestMapper.toRetrieveLabTestDto(labTest);
        } catch (Exception e) {
            logger.warn("Error while retrieving lab test with id: {}", id);
            throw new LabException("Error while retrieving lab test with id: " + id + e.getMessage(), e);
        }
        return labTestDto;
    }

    @Override
    public boolean removeLabTestById(long id) {
            LabTest labTest = labTestRepository.findByIdAndIsDeletedFalse(id);
            if (labTest == null) {
                logger.warn("Lab test not found while removing with id: {}", id);
                throw new NoSuchElementException("Lab test Not Found with id: " + id);
            }
            labTest.setDeleted(true);
        try {
            labTestRepository.save(labTest);
            logger.info("Lab test marked as deleted with id: {}", id);
            return true;
        } catch (Exception e) {
            logger.warn("Error while removing lab test with id: {}", id);
            throw new LabException("Error while removing lab test with id: " + id, e);
        }
    }
}
