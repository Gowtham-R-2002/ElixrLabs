package org.medx.elixrlabs.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.medx.elixrlabs.util.RoleEnum.ROLE_PATIENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.exception.LabException;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private LabTestService labTestService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private TestPackageService testPackageService;

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
        testPackage = ResponseTestPackageDto.builder()
                .id(1L)
                .labTests(List.of(labTestDto))
                .description("Test package")
                .price(1500.0)
                .name("Test Package")
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
                .roles(List.of(role))
                .build();

        patient = Patient.builder()
                .id(1L)
                .user(user)
                .isDeleted(false)
                .build();

        TestPackage testPackageEntity = TestPackage.builder()
                .id(1L)
                .isDeleted(false)
                .tests(List.of(test))
                .build();

        cart = Cart.builder()
                .id(1)
                .tests(List.of(test))
                .testPackage(testPackageEntity)
                .build();

        responseCart = Cart.builder()
                .id(1)
                .tests(List.of(test))
                .build();

        requestCartDto = CartDto.builder()
                .testIds(Arrays.asList(1L, 2L))
                .build();

        responseCartDto = ResponseCartDto.builder()
                .id(1)
                .tests(List.of(ResponseTestInCartDto.builder().id(1L)
                        .name("Blood Test")
                        .build()))
                .build();

    }

    @Test
    void addTestsOrPackagesToCart_success() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);
            when(labTestService.getLabTestById(anyLong())).thenReturn(labTestDto);
            when(testPackageService.getTestPackageById(anyLong())).thenReturn(testPackage);
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            ResponseCartDto response = cartService.addTestsOrPackagesToCart(cartDto);
            assertNotNull(response);
            verify(cartRepository, times(1)).save(any(Cart.class));
        }
    }

    @Test
    void addTestsOrPackagesToCart_testPackage_null_success() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);
            when(labTestService.getLabTestById(anyLong())).thenReturn(labTestDto);
            cartDto.setTestPackageId(null);
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            ResponseCartDto response = cartService.addTestsOrPackagesToCart(cartDto);
            assertNotNull(response);
            verify(cartRepository, times(1)).save(any(Cart.class));
        }
    }

    @Test
    void addTestsOrPackagesToCart_success_test_null() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);
            cartDto.setTestIds(null);
            when(testPackageService.getTestPackageById(anyLong())).thenReturn(testPackage);
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            ResponseCartDto response = cartService.addTestsOrPackagesToCart(cartDto);
            assertNotNull(response);
            verify(cartRepository, times(1)).save(any(Cart.class));
        }
    }


    @Test
    void addTestsOrPackagesToCart_cart_null_success() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(null);
            when(labTestService.getLabTestById(anyLong())).thenReturn(labTestDto);
            when(testPackageService.getTestPackageById(anyLong())).thenReturn(testPackage);
            when(cartRepository.save(any(Cart.class))).thenReturn(cart);

            ResponseCartDto response = cartService.addTestsOrPackagesToCart(cartDto);
            assertNotNull(response);
            verify(cartRepository, times(1)).save(any(Cart.class));
        }
    }


    @Test
    void addTestsOrPackagesToCart_failure() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenThrow(new LabException("Unable to decode security context"));

            assertThrows(LabException.class, () -> {
                cartService.addTestsOrPackagesToCart(cartDto);
            });

            verify(cartRepository, never()).save(any(Cart.class));
        }
    }

    @Test
    void addTestsOrPackagesToCart_exception() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);
            when(labTestService.getLabTestById(anyLong())).thenReturn(labTestDto);
            when(testPackageService.getTestPackageById(anyLong())).thenReturn(testPackage);
            when(cartRepository.save(cart)).thenThrow(LabException.class);
            assertThrows(LabException.class, () -> cartService.addTestsOrPackagesToCart(cartDto));
        }
    }


    @Test
    void getCartByPatient_success() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);

            ResponseCartDto response = cartService.getCartByPatient();

            assertNotNull(response);
            verify(cartRepository, times(1)).findCartByUser(patient);
        }
    }

    @Test
    void getCartByPatient_success_cart_null() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(null);

            ResponseCartDto response = cartService.getCartByPatient();

            assertNotNull(response);
            verify(cartRepository, times(1)).findCartByUser(patient);
        }
    }

    @Test
    void getCartByPatient_failure() {
        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenThrow(LabException.class);
            assertThrows(LabException.class, () -> {
                cartService.getCartByPatient();
            });

            verify(cartRepository, never()).findCartByUser(patient);
        }
    }

    @Test
    void getCartByPatient_exception() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenThrow(new NullPointerException("Database error"));

            LabException exception = assertThrows(LabException.class, () -> {
                cartService.getCartByPatient();
            });

            assertTrue(exception.getMessage().contains("Error while retrieving cart"));
            verify(cartRepository, times(1)).findCartByUser(patient);
        }
    }

    @Test
    void deleteCart_success() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);

            cartService.deleteCart();

            verify(cartRepository, times(1)).delete(cart);
        }
    }

    @Test
    void deleteCart_failure() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(null);

            cartService.deleteCart();

            verify(cartRepository, never()).delete(any(Cart.class));
        }
    }

    @Test
    void deleteCart_exception() {

        try (MockedStatic<SecurityContextHelper> mockSecurityContext = Mockito.mockStatic(SecurityContextHelper.class)) {
            mockSecurityContext.when(SecurityContextHelper::extractEmailFromContext).thenReturn("test@example.com");

            when(patientService.getPatientByEmail(anyString())).thenReturn(patient);
            when(cartRepository.findCartByUser(patient)).thenReturn(cart);
            doThrow(new RuntimeException("Database error")).when(cartRepository).delete(cart);

            LabException exception = assertThrows(LabException.class, () -> {
                cartService.deleteCart();
            });

            assertTrue(exception.getMessage().contains("Error while deleting cart"));
            verify(cartRepository, times(1)).delete(cart);
        }
    }
}