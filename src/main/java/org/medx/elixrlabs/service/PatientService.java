package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Interface for PatientService, defining the business operations related to Patient.
 * This interface is implemented by PatientServiceImpl and defines the contract for
 * managing Patient entities.
 * </p>
 */
@Service
public interface PatientService {


    UserDto createOrUpdatePatient(UserDto userDto);

    List<UserDto> getAllPatients();

    boolean bookSlotAndReturnStatus();

    List<Order> getOrders();

    TestResult getTestReport(Long orderId);

    void deletePatient(String email);
}
