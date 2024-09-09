package org.medx.elixrlabs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LabControllerTest {

    @Mock
    private LabService labService;

    @Mock
    private SampleCollectorService sampleCollectorService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private LabController labController;

    private RequestUserNameDto userNameDto;
    private List<ResponseOrderDto> responseOrderDtos;
    private List<SampleCollectorDto> sampleCollectorDtos;
    private Order order;
    private TestResultDto testResultDto;
    private ResponseTestPackageDto responseTestPackageDto;
    private List<LabTestDto> labTestDtos;
    private List<ResponseTestInCartDto> responseTestInCartDtos;
    private TestPackage testPackage;
    private List<LabTest> tests;
    private RequestTestResultDto requestTestResultDto;
    @BeforeEach
    public void setup() {
        SampleCollectorDto firstSampleCollectorDto = SampleCollectorDto.builder()
                .place(LocationEnum.MARINA)
                .gender(GenderEnum.M)
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(2002,8,8))
                .isVerified(true)
                .email("samplecollector1@gmail.com")
                .build();
        SampleCollectorDto secondSampleCollectorDto = SampleCollectorDto.builder()
                .place(LocationEnum.MARINA)
                .gender(GenderEnum.F)
                .phoneNumber("1234567899")
                .dateOfBirth(LocalDate.of(2002,8,8))
                .isVerified(true)
                .email("samplecollector2@gmail.com")
                .build();
        userNameDto = RequestUserNameDto.builder()
                .email(firstSampleCollectorDto.getEmail())
                .build();
        LabTestDto firstLabTestDto = LabTestDto.builder()
                .name("Blood Test")
                .id(1L)
                .defaultValue("WBC count : 500")
                .price(150.00)
                .description("A simple blood test")
                .build();
        LabTestDto secondLabTestDto = LabTestDto.builder()
                .name("HIV Test")
                .id(1L)
                .defaultValue("Result : negative")
                .price(150.00)
                .description("A simple HIV test")
                .build();

        LabTest firstLabTest = LabTest.builder()
                .name("Blood Test")
                .id(1L)
                .defaultValue("WBC count : 500")
                .price(150.00)
                .description("A simple blood test")
                .build();
        LabTest secondLabTest = LabTest.builder()
                .name("HIV Test")
                .id(1L)
                .defaultValue("Result : negative")
                .price(150.00)
                .description("A simple HIV test")
                .build();

        tests = List.of(firstLabTest, secondLabTest);
        ResponseTestInCartDto firstResponseTestInCartDto = ResponseTestInCartDto.builder()
                .name("Blood Test")
                .id(1L)
                .price(150.00)
                .description("A simple blood test")
                .build();
        ResponseTestInCartDto secondResponseTestInCartDto = ResponseTestInCartDto.builder()
                .name("HIV Test")
                .id(1L)
                .price(150.00)
                .description("A simple HIV test")
                .build();
        responseTestInCartDtos = List.of(firstResponseTestInCartDto, secondResponseTestInCartDto);
        labTestDtos = List.of(firstLabTestDto, secondLabTestDto);
        responseTestPackageDto = ResponseTestPackageDto.builder()
                .id(1L)
                .price(1000.00)
                .labTests(labTestDtos)
                .name("Body test")
                .description("Simple body test")
                .build();
        testPackage = TestPackage.builder()
                .id(1L)
                .price(1000.00)
                .tests(tests)
                .name("Body test")
                .description("Simple body test")
                .build();
        ResponseOrderDto firstResponseOrderDto = ResponseOrderDto.builder()
                .id(1L)
                .testStatus(TestStatusEnum.COMPLETED)
                .testPackageDto(responseTestPackageDto)
                .tests(responseTestInCartDtos)
                .build();

        ResponseOrderDto secondResponseOrderDto = ResponseOrderDto.builder()
                .id(1L)
                .testStatus(TestStatusEnum.COMPLETED)
                .testPackageDto(responseTestPackageDto)
                .tests(responseTestInCartDtos)
                .build();

        order = Order.builder()
                .id(1L)
                .testStatus(TestStatusEnum.COMPLETED)
                .testPackage(testPackage)
                .tests(tests)
                .build();

        responseOrderDtos = List.of(firstResponseOrderDto, secondResponseOrderDto);

        requestTestResultDto = RequestTestResultDto.builder()
                .testIdWithResult(List.of(RequestTestIdWithResultDto.builder()
                        .testId(1L)
                        .result("Good Health Condition")
                        .build()))
                .build();
        testResultDto = TestResultDto.builder()
                .orderDate(LocalDate.now())
                .ageAndGender("22 M")
                .result(List.of("Blood Test : Actual WBC Count : 500, Current WBC count : 503"))
                .generatedAt(LocalDateTime.now())
                .id(1L)
                .email("gowtham080802@gmail.com")
                .build();
        sampleCollectorDtos = List.of(firstSampleCollectorDto, secondSampleCollectorDto);
        responseOrderDtos = List.of(firstResponseOrderDto, secondResponseOrderDto);
    }

    @Test
    void testVerifySampleCollector() {
        doNothing().when(sampleCollectorService).verifySampleCollector(sampleCollectorDtos.getFirst().getEmail());
        ResponseEntity<HttpStatus.Series> result = labController.verifySampleCollector(userNameDto);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void testGetAllSampleCollectors() {
        when(sampleCollectorService.getAllSampleCollectors()).thenReturn(sampleCollectorDtos);
        ResponseEntity<List<SampleCollectorDto>> result = labController.getAllSampleCollectors();
        assertEquals(sampleCollectorDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
