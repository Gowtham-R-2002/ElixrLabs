package org.medx.elixrlabs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.medx.elixrlabs.repository.CartRepository;
import org.medx.elixrlabs.service.impl.CartServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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

    @BeforeEach
    void setUp() {

    }
}
