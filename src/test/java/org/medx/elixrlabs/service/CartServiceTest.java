package org.medx.elixrlabs.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.dto.ResponseTestInCartDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.repository.CartRepository;
import org.medx.elixrlabs.service.impl.CartServiceImpl;
import org.medx.elixrlabs.util.GenderEnum;
import org.medx.elixrlabs.util.LocationEnum;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.medx.elixrlabs.util.LocationEnum.VELACHERY;
import static org.medx.elixrlabs.util.RoleEnum.ROLE_PATIENT;
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
    CartServiceImpl cartService;

    private Patient patient;
    private Cart cart;
    private Cart responseCart;
    private CartDto requestCartDto;
    private ResponseCartDto responseCartDto;

    @BeforeAll
    static void beforeAll(){
        mockStatic(SecurityContextHelper.class);
    }

    @BeforeEach
    void setUp() {
        LabTest test = LabTest.builder()
                .name("Blood Test")
                .price(150.00)
                .description("Simple Blood Test")
                .defaultValue("BPC : 1000")
                .id(1L)
                .build();
        Role role = Role.builder()
                .id(1)
                .name(ROLE_PATIENT)
                .build();
        User user = User.builder()
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
                .testIds(Arrays.asList(1L,2L))
                .build();
        responseCartDto = ResponseCartDto.builder()
                .id(1)
                .tests(Collections.singletonList(ResponseTestInCartDto.builder().id(1L)
                        .name("Blood Test")
                        .build()))
                .build();
    }

    @Test
    void testAddTestOrTestPackagesToCart() {
        when(SecurityContextHelper.extractEmailFromContext()).thenReturn(patient.getUser().getEmail());
        when(patientService.getPatientByEmail(patient.getUser().getEmail())).thenReturn(patient);
        when(cartRepository.findCartByUser(patient)).thenReturn(cart);
        when(cartRepository.save(cart)).thenReturn(responseCart);
        ResponseCartDto result = cartService.addTestsOrPackagesToCart(requestCartDto);
        assertEquals(responseCartDto, result);
    }

    @Test
    void testGetCartByPatient() {

    }

    @Test
    void testDeleteCart() {

    }
}
