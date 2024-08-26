package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.UserDto;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    /**
     * Retrieves all patients.
     *
     * @return A list of User DTOs.
     */
    List<UserDto> getAllPatients();

    /**
     * Books a slot according to the patients booking time and returns a status.
     *
     * @return A boolean value based on the booking slots.
     */
    boolean bookSlotAndReturnStatus();

    /**
     * Retrieves all orders.
     *
     * @return A list of orders.
     */
    List<Order> getOrders();

    /**
     * Retrieves an TestResult by orderID.
     *
     * @param orderId {@link Long} The ID of the SampleCollector.
     * @return The TestResult DTO.
     * @throws NoSuchElementException if the TestResult is not found.
     */
    TestResult getTestReport(Long orderId);

    /**
     * Deletes an TestResult by user emailId (soft deletion).
     *
     * @param email {@link UserDto} The ID of the TestResult to delete.
     * @throws RuntimeException if the TestResult is not found.
     */
    void deletePatient(String email);
}
