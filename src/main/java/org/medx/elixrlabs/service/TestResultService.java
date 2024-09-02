package org.medx.elixrlabs.service;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.model.TestResult;

/**
 * <p>
 * Interface for TestResultService, defining the business operations related to TestResult.
 * This interface is implemented by TestResultServiceImpl and defines the contract for
 * managing TestResult entities.
 * </p>
 */
@Service
public interface TestResultService {

    /**
     * Adds a new test result for a specific order
     *
     * @param testResult {@link TestResult} test result to be added
     * @return test result that is added
     */
    TestResult addResult(TestResult testResult);

}
