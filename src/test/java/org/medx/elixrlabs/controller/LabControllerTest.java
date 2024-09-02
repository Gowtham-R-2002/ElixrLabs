//package org.medx.elixrlabs.controller;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.medx.elixrlabs.dto.*;
//import org.medx.elixrlabs.model.SampleCollector;
//import org.medx.elixrlabs.service.LabService;
//import org.medx.elixrlabs.service.PatientService;
//import org.medx.elixrlabs.service.SampleCollectorService;
//import org.medx.elixrlabs.util.GenderEnum;
//import org.medx.elixrlabs.util.LocationEnum;
//import org.medx.elixrlabs.util.TestStatusEnum;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class LabControllerTest {
//
//    @MockBean
//    private LabService labService;
//
//    @MockBean
//    private SampleCollectorService sampleCollectorService;
//
//    @MockBean
//    private PatientService patientService;
//
//    private List<ResponseOrderDto> responseOrderDtos;
//    private List<SampleCollectorDto> sampleCollectorDtos;
//    private TestResultDto testResultDto;
//    private ResponseTestPackageDto responseTestPackageDto;
//    private List<LabTestDto> labTestDtos;
//    @BeforeEach
//    public void setup() {
//        SampleCollectorDto firstSampleCollectorDto = SampleCollectorDto.builder()
//                .place(LocationEnum.MARINA)
//                .gender(GenderEnum.M)
//                .phoneNumber("1234567890")
//                .dateOfBirth(LocalDate.of(2002,8,8))
//                .isVerified(true)
//                .email("samplecollector1@gmail.com")
//                .build();
//        SampleCollectorDto secondSampleCollectorDto = SampleCollectorDto.builder()
//                .place(LocationEnum.MARINA)
//                .gender(GenderEnum.F)
//                .phoneNumber("1234567899")
//                .dateOfBirth(LocalDate.of(2002,8,8))
//                .isVerified(true)
//                .email("samplecollector2@gmail.com")
//                .build();
//        LabTestDto firstLabTestDto = LabTestDto.builder()
//                .name("Blood Test")
//                .id(1L)
//                .defaultValue("WBC count : 500")
//                .price(150.00)
//                .description("A simple blood test")
//                .build();
//        LabTestDto secondLabTestDto = LabTestDto.builder()
//                .name("HIV Test")
//                .id(1L)
//                .defaultValue("Result : negative")
//                .price(150.00)
//                .description("A simple HIV test")
//                .build();
//
//        labTestDtos = List.of(firstLabTestDto, secondLabTestDto);
//        responseTestPackageDto = ResponseTestPackageDto.builder()
//                .id(1L)
//                .price(1000.00)
//                .labTests(labTestDtos)
//                .name("Body test")
//                .description("Simple body test")
//                .build();
//        ResponseOrderDto firstResponseOrderDto = ResponseOrderDto.builder()
//                .id(1L)
//                .testStatus(TestStatusEnum.COMPLETED)
//                .testPackageDto(responseTestPackageDto)
//                .tests(labTestDtos)
//                .build();
//
//        ResponseOrderDto secondResponseOrderDto = ResponseOrderDto.builder()
//                .id(1L)
//                .testStatus(TestStatusEnum.COMPLETED)
//                .testPackageDto(responseTestPackageDto)
//                .tests(labTestDtos)
//                .build();
//
//        testResultDto = TestResultDto.builder()
//                .orderDate(LocalDate.now())
//                .ageAndGender("22 M")
//                .result(List.of("Blood Test : Actual WBC Count : 500, Current WBC count : 503"))
//                .generatedAt(LocalDateTime.now())
//                .id(1L)
//                .email("gowtham080802@gmail.com")
//                .build();
//        sampleCollectorDtos = List.of(firstSampleCollectorDto, secondSampleCollectorDto);
//        responseOrderDtos = List.of(firstResponseOrderDto, secondResponseOrderDto);
//    }
//
//}
