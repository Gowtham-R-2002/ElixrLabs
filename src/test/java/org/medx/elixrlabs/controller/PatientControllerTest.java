package org.medx.elixrlabs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.service.AppointmentSlotService;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @Mock
    private CartService cartService;

    @Mock
    private AppointmentSlotService appointmentSlotService;

    @InjectMocks
    private PatientController patientController;

    private UserDto userDto;
    private ResponsePatientDto responsePatientDto;
    private RequestSlotBookDto slotBookDto;
    private TestResultDto testResultDto;
    private ResponseTestInCartDto test;
    private List<ResponseTestInCartDto> tests;
    private CartDto cartDto;
    private ResponseCartDto responseCartDto;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("user@gmail.com")
                .place(LocationEnum.VELACHERY)
                .password("user@123")
                .phoneNumber("1234567890")
                .dateOfBirth(LocalDate.parse("1999-02-25"))
                .gender(GenderEnum.M)
                .build();
        responsePatientDto = ResponsePatientDto.builder()
                .id(1L)
                .dateOfBirth(LocalDate.parse("1999-02-25"))
                .email("user@gmail.com")
                .gender(GenderEnum.M)
                .phoneNumber("1234567890")
                .place(LocationEnum.VELACHERY)
                .build();
        slotBookDto = RequestSlotBookDto.builder()
                .date(LocalDate.parse("2024-09-01"))
                .location(LocationEnum.VELACHERY)
                .testCollectionPlace(TestCollectionPlaceEnum.HOME)
                .build();
        testResultDto = TestResultDto.builder()
                .generatedAt(LocalDateTime.parse("2024-09-01"))
                .id(1L)
                .email("user@gmail.com")
                .orderDate(LocalDate.parse("2024-08-29"))
                .result(List.of("Normal"))
                .ageAndGender("24 M")
                .build();
        test = ResponseTestInCartDto.builder()
                .id(1L)
                .name("Blood Test")
                .description("Simple Blood Test")
                .price(200.00)
                .build();
        tests = List.of(test);
        cartDto = CartDto.builder()
                .testIds(List.of(test.getId()))
                .testPackageId(null)
                .build();
        responseCartDto = ResponseCartDto.builder()
                .id(1L)
                .tests(tests)
                .price(200.00)
                .testPackage(null)
                .build();
    }
}
