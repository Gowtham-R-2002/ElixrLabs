package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

@Service
public interface TestResultService {
    TestResult addResult(TestResult testResult);
}
