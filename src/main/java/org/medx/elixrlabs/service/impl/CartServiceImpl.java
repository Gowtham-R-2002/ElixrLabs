package org.medx.elixrlabs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.medx.elixrlabs.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.CartMapper;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.mapper.TestPackageMapper;
import org.medx.elixrlabs.model.Cart;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.CartRepository;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.TestPackageService;


/**
 * <p>
 * Service implementation for managing Cart-related operations.
 * This class contains business logic for handling Cart entities, including
 * adding tests or packages to the cart, retrieving the cart, and deleting the cart.
 * It acts as a bridge between the controller layer and the repository layer,
 * ensuring that business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TestPackageService testPackageService;

    @Autowired
    private LabTestService labTestService;

    @Autowired
    private PatientService patientService;

    @Override
    public ResponseCartDto addTestsOrPackagesToCart(CartDto cartDto) {
        Patient patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        Cart userCart = cartRepository.findCartByUser(patient);
        if (userCart == null) {
            userCart = Cart.builder()
                    .patient(patient)
                    .build();
        }
        if (cartDto.getTestIds() != null) {
            List<LabTest> tests = cartDto.getTestIds()
                    .stream()
                    .map(testId -> LabTestMapper
                            .toLabTest(labTestService
                                    .getLabTestById(testId)))
                    .collect(Collectors.toList());
            userCart.setTests(tests);
        }
        if (cartDto.getTestPackageId() != null) {
            userCart.setTestPackage(TestPackageMapper.toTestPackageFromResponseDto(
                    testPackageService.getTestPackageById(cartDto.getTestPackageId())));
        }
        ResponseCartDto responseCartDto;
        try {
            responseCartDto = CartMapper.toCartDto(cartRepository.save(userCart));
        } catch (Exception e) {
            logger.warn("Error while adding tests or packages to cart for patient: {}", patient.getUser().getUsername());
            throw new LabException("Error while adding tests or packages to cart", e);
        }
        return responseCartDto;
    }

    @Override
    public ResponseCartDto getCartByPatient() {
        String email = SecurityContextHelper.extractEmailFromContext();
        Patient patient = patientService.getPatientByEmail(email);
        if (null == patient) {
            logger.warn("Patient not found with email : {}", email);
            throw new NoSuchElementException("Patient not found with email : " + email);
        }
        try {
            Cart userCart = cartRepository.findCartByUser(patient);
            if (userCart == null) {
                userCart = Cart.builder()
                        .tests(new ArrayList<>())
                        .testPackage(null)
                        .build();
            }
            return CartMapper.toCartDto(userCart);
        } catch (Exception e) {
            logger.warn("Error while retrieving cart for patient: {}", patient.getUser().getEmail());
            throw new LabException("Error while retrieving cart" , e);
        }
    }

    @Override
    public void deleteCart() {
        Patient patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        Cart userCart = cartRepository.findCartByUser(patient);
        if (userCart != null) {
            try {
                cartRepository.delete(userCart);
            } catch (Exception e) {
                logger.warn("Error while deleting cart for patient: {}", patient.getUser().getUsername());
                throw new LabException("Error while deleting cart", e);
            }
        } else {
            logger.warn("Attempted to delete cart for patient with no existing cart: {}", patient.getUser().getUsername());
        }
    }
}
