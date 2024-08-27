package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.CartDto;
import org.medx.elixrlabs.dto.ResponseCartDto;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.CartMapper;
import org.medx.elixrlabs.mapper.LabTestMapper;
import org.medx.elixrlabs.mapper.TestPackageMapper;
import org.medx.elixrlabs.model.Cart;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.CartRepository;
import org.medx.elixrlabs.service.CartService;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.TestPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        Cart userCart = cartRepository.findCartByUser(patient);
        if (null == userCart) {
            userCart = Cart.builder()
                    .patient(patient)
                    .build();
        }
        if (null != cartDto.getTestIds()) {
            userCart.setTests(cartDto.getTestIds()
                    .stream().map(testId ->
                            LabTestMapper.toLabTest(labTestService.getLabTestById(testId)))
                    .collect(Collectors.toList()));
        }
        if (null != cartDto.getTestPackageId()) {
            userCart.setTestPackage(TestPackageMapper
                    .toTestPackageFromResponseDto(testPackageService
                            .getTestPackageById(cartDto.getTestPackageId())));
        }
        return CartMapper.toCartDto(cartRepository.save(userCart));
    }

    @Override
    public ResponseCartDto getCartByPatient() {
        User patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        Cart userCart = cartRepository.findCartByUser(patient);
        if(null == userCart) {
            userCart = Cart.builder()
                    .tests(new ArrayList<>())
                    .testPackage(null)
                    .build();
        }
        return CartMapper.toCartDto(userCart);
    }

    @Override
    public void deleteCart() {
        User patient = patientService.getPatientByEmail(SecurityContextHelper.extractEmailFromContext());
        Cart userCart = cartRepository.findCartByUser(patient);
        cartRepository.delete(userCart);
    }
}
