package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.CartMapper;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.mapper.TestPackageMapper;
import org.medx.elixrlabs.model.Cart;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.CartRepository;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.TestPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    TestPackageService testPackageService;

    @Autowired
    LabTestService labTestService;

    @Autowired
    PatientService patientService;

    @Override
    public ResponseCartDto addTestsOrPackagesToCart(CartDto cartDto) {
        User patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        Cart existingCart = cartRepository.findCartByUser(patient);
        Cart cart = CartMapper.toCart(cartDto);
        if(null != existingCart) {
            cart = existingCart;
        }
        cart.setPatient(patient);
        cart.setTests(new ArrayList<>());
        cartDto.setTestIds(new ArrayList<>());
        for(long labTestId : cartDto.getTestIds()) {
            cart.getTests()
                    .add(LabTestMapper
                            .toLabTest(
                                    labTestService
                                            .getLabTestById(
                                                    labTestId)));
        }
        cart.setTestPackage(
                TestPackageMapper
                        .toTestPackageFromResponseDto(
                                testPackageService
                                        .getTestPackageById(
                                                cartDto
                                                        .getTestPackageId())));
        return CartMapper.toCartDto(cartRepository.save(cart));
    }

    @Override
    public ResponseCartDto getCartByPatient() {
        User patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        return CartMapper.toCartDto(cartRepository.findCartByUser(patient));
    }

    @Override
    public ResponseCartDto removeTestsOrPackageFromCart(CartDto cartDto) {
        User patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        Cart cart = cartRepository.findCartByUser(patient);
        cartDto.setTestIds(new ArrayList<>());
        if (!cartDto.getTestIds().isEmpty()) {
            for (long testId : cartDto.getTestIds()) {
                cart.getTests().remove(LabTestMapper.toLabTest(labTestService.getLabTestById(testId)));
            }
        }
        if(null != cartDto.getTestPackageId()){
            cart.setTestPackage(null);
        }
        return CartMapper.toCartDto(cartRepository.save(cart));
    }
}
