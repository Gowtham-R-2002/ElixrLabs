package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.repository.TestResultRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestResultServiceTest {

    @Mock
    private TestResultRepository testResultRepository;

    @InjectMocks
    TestResultServiceImpl testResultService;

    private TestResult testResult;
    private TestResult responseTestResult;

    @BeforeEach
    void setUp() {
        testResult = TestResult.builder()
                .id(1L)
                .generatedAt(LocalDateTime.parse("2024-09-02"))
                .name("Roman")
                .result(List.of("Positive"))
                .orderDate(LocalDate.parse("2024-09-01"))
                .build();

        responseTestResult = TestResult.builder()
                .id(1L)
                .generatedAt(LocalDateTime.parse("2024-09-02"))
                .name("Roman")
                .result(List.of("Positive"))
                .orderDate(LocalDate.parse("2024-09-01"))
                .build();
    }
}
