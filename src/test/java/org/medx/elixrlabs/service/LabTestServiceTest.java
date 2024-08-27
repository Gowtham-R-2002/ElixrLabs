package org.medx.elixrlabs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.repository.LabTestRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabTestServiceTest {

    @Mock
    LabTestRepository labTestRepository;

    @InjectMocks
    LabTestService labTestService;

    private LabTest labTest;
    private LabTest responseLabTest;
    private LabTestDto labTestDto;
    private LabTestDto responseLabTestDto;

    @BeforeEach
    void setUp() {
        labTest.setName("Cancer Test");
        labTest.setDescription("Simple Cancer Test");
        labTest.setPrice(300.00);
        labTest.setDefaultValue("BPC : 1000");
        labTest.setDeleted(false);
        responseLabTest.setId(12L);
        responseLabTest.setName("Cancer Test");
        responseLabTest.setDescription("Simple Cancer Test");
        responseLabTest.setPrice(300.00);
        responseLabTest.setDefaultValue("BPC : 1000");
        responseLabTest.setDeleted(false);
        labTestDto.setName("Cancer Test");
        labTestDto.setDescription("Simple Cancer Test");
        labTestDto.setPrice(300.00);
        labTestDto.setDefaultValue("BPC : 1000");
        responseLabTestDto.setId(12L);
        responseLabTestDto.setName("Cancer Test");
        responseLabTestDto.setDescription("Simple Cancer Test");
        responseLabTestDto.setPrice(300.00);
        responseLabTestDto.setDefaultValue("BPC : 1000");
    }

    @Test
    void testCreateOrUpdate_positive() {
        when(labTestRepository.save(labTest)).thenReturn(responseLabTest);
        LabTestDto result = labTestService.createOrUpdateTest(labTestDto);
        assertEquals(responseLabTestDto,result);
    }
}
