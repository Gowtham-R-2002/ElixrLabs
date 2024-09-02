package org.medx.elixrlabs.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.repository.CartRepository;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.TestPackageService;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.medx.elixrlabs.util.RoleEnum.ROLE_PATIENT;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private TestPackageService testPackageService;

    @Mock
    private LabTestService labTestService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Role role;
    private Patient patient;
    private LabTest test;
    private Cart cart;
    private Cart responseCart;
    private CartDto requestCartDto;
    private ResponseCartDto responseCartDto;
    private LabTestDto labTestDto;
    private ResponseTestPackageDto testPackage;
    private CartDto cartDto;

    @BeforeEach
    void setUp() {
        cartDto = CartDto.builder()
                .testPackageId(1L)
                .testIds(List.of(1L))
                .build();
        test = LabTest.builder()
                .name("Blood Test")
                .price(150.00)
                .description("Simple Blood Test")
                .defaultValue("BPC : 1000")
                .id(1L)
                .build();
        labTestDto = LabTestDto.builder()
                .name("Blood Test")
                .price(150.00)
                .description("Simple Blood Test")
                .defaultValue("BPC : 1000")
                .id(1L)
                .build();
        role = Role.builder()
                .id(1)
                .name(ROLE_PATIENT)
                .build();
        user = User.builder()
                .UUID("23456")
                .email("test@gmail.com")
                .place(LocationEnum.VELACHERY)
                .dateOfBirth(LocalDate.parse("1998-04-20"))
                .gender(GenderEnum.M)
                .phoneNumber("9876543210")
                .roles(Collections.singletonList(role))
                .build();
        patient = Patient.builder()
                .id(1L)
                .user(user)
                .isDeleted(false)
                .build();
        cart = Cart.builder()
                .id(1)
                .tests(Collections.singletonList(test))
                .build();
        responseCart = Cart.builder()
                .id(1)
                .tests(Collections.singletonList(test))
                .build();
        requestCartDto = CartDto.builder()
                .testIds(Arrays.asList(1L, 2L))
                .build();
        responseCartDto = ResponseCartDto.builder()
                .id(1)
                .tests(Collections.singletonList(ResponseTestInCartDto.builder().id(1L)
                        .name("Blood Test")
                        .build()))
                .build();
        testPackage = ResponseTestPackageDto.builder()
                .id(1L)
                .labTests(List.of(labTestDto))
                .description("Test package")
                .price(1500.0)
                .name("Test Package")
                .build();
    }

    @Test
    public void testAddTestsOrPackagesToCart() {
        try(MockedStatic<SecurityContextHelper> mockedStatic = mockStatic(SecurityContextHelper.class)) {
            mockedStatic.when(SecurityContextHelper::extractEmailFromContext).thenReturn(user.getEmail());
            when(patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);
            when(labTestService.getLabTestById(anyLong())).thenReturn(labTestDto);
            when(testPackageService.getTestPackageById(anyLong())).thenReturn(testPackage);
            when(cartRepository.save(cart)).thenReturn(cart);
            ResponseCartDto savedResponseCartDto = cartService.addTestsOrPackagesToCart(cartDto);
            assertEquals(cart.getId(), 1);
        }
    }

}