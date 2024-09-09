package org.medx.elixrlabs.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.repository.TestResultRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

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
                .generatedAt(LocalDateTime.now())
                .name("Roman")
                .result(List.of("Positive"))
                .orderDate(LocalDate.parse("2024-09-01"))
                .build();

        responseTestResult = TestResult.builder()
                .id(1L)
                .generatedAt(LocalDateTime.now())
                .name("Roman")
                .result(List.of("Positive"))
                .orderDate(LocalDate.parse("2024-09-01"))
                .build();
    }

    @Test
    public void testAddTestResult() {
        when(testResultRepository.save(testResult)).thenReturn(responseTestResult);
        TestResult result = testResultService.addResult(testResult);
        assertEquals(1L, result.getId());
        assertNotNull(result);
    }
}
