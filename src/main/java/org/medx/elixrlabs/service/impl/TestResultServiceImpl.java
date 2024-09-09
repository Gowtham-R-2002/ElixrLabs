package org.medx.elixrlabs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.repository.TestResultRepository;
import org.medx.elixrlabs.service.TestResultService;

/**
 * <p>
 * The TestResultServiceImpl class provides the implementation of the {@link TestResultService}
 * interface. It handles the business logic for managing test results, specifically adding new
 * test results to the system.
 * This service interacts with the {@link TestResultRepository} to persist test result data
 * in the database.
 * </p>
 */
@Service
public class TestResultServiceImpl implements TestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Override
    public TestResult addResult(TestResult testResult) {
        return testResultRepository.save(testResult);
    }
}
