package org.medx.elixrlabs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.PatientService;
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
public class OrderControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private LabService labService;

    @InjectMocks
    private OrderController orderController;

    private TestResultDto testResultDto;
    private Order order;
    private RequestUserNameDto userNameDto;
    private List<ResponseOrderDto> responseOrderDtos;
    private RequestTestResultDto requestTestResultDto;

    @BeforeEach
    void setUp() {
        SampleCollectorDto firstSampleCollectorDto = SampleCollectorDto.builder()
                .place(LocationEnum.MARINA)
                .gender(GenderEnum.M)
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.of(2002,8,8))
                .isVerified(true)
                .email("samplecollector1@gmail.com")
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

        List<LabTest> tests = List.of(firstLabTest, secondLabTest);
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
        List<ResponseTestInCartDto> responseTestInCartDtos = List.of(firstResponseTestInCartDto, secondResponseTestInCartDto);
        List<LabTestDto> labTestDtos = List.of(firstLabTestDto, secondLabTestDto);
        ResponseTestPackageDto responseTestPackageDto = ResponseTestPackageDto.builder()
                .id(1L)
                .price(1000.00)
                .labTests(labTestDtos)
                .name("Body test")
                .description("Simple body test")
                .build();
        TestPackage testPackage = TestPackage.builder()
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
        responseOrderDtos = List.of(firstResponseOrderDto, secondResponseOrderDto);
        testResultDto = TestResultDto.builder()
                .generatedAt(LocalDateTime.now())
                .id(1L)
                .email("user@gmail.com")
                .orderDate(LocalDate.of(2024, 8, 29))
                .result(List.of("Normal"))
                .ageAndGender("24 M")
                .build();
    }

    @Test
    void testGetOrdersByPatient() {
        when(patientService.getOrdersByPatient(userNameDto)).thenReturn(responseOrderDtos);
        ResponseEntity<List<ResponseOrderDto>> result = orderController.getOrdersByPatient(userNameDto);
        assertEquals(responseOrderDtos, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetTestResult() {
        when(labService.getTestResultByOrder(anyLong())).thenReturn(testResultDto);
        ResponseEntity<TestResultDto> result = orderController.getTestResult(order.getId());
        assertEquals(testResultDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    void testGetOrders_all() {
        when(labService.getOrders()).thenReturn(responseOrderDtos);
        ResponseEntity<List<ResponseOrderDto>> result = orderController.getOrders();
        assertEquals(responseOrderDtos, result.getBody());
    }

    @Test
    void testUpdateReport() {
        doNothing().when(labService).assignReport(anyLong(),any(RequestTestResultDto.class));
        ResponseEntity<HttpStatus.Series> result = orderController.updateReport(requestTestResultDto, order.getId());
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }


    @Test
    void testGetOrders() {
        when(patientService.getOrders()).thenReturn(List.of(ResponseOrderDto.builder().build()));
        ResponseEntity<List<ResponseOrderDto>> result = orderController.getOrdersOfPatient();
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetTestReport() {
        when(patientService.getTestReport(anyLong())).thenReturn(testResultDto);
        ResponseEntity<TestResultDto> result = orderController.getTestReport(order.getId());
        assertEquals(testResultDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
