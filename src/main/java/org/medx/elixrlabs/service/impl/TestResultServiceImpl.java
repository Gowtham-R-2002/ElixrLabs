package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.repository.TestResultRepository;
import org.medx.elixrlabs.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultServiceImpl implements TestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Override
    public TestResult addResult(TestResult testResult) {
        return testResultRepository.save(testResult);
    }
}
