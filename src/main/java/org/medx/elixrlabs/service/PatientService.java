package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    UserDto createOrUpdatePatient(UserDto userDto);

    List<UserDto> getAllPatients();

    boolean bookSlotAndReturnStatus();

    List<Order> getOrders();

    TestResult getTestReport(Long orderId);

    void deletePatient(String email);
}
