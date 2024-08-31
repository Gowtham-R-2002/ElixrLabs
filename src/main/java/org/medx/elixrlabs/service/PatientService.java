package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.Patient;
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


    /**
     * Adds a new patient or updates the details associated with a specific patient
     *
     * @param userDto - patient details which has to be added or updated for a
     *                patient
     * @return patient details that has been added or updated
     */
    ResponsePatientDto createOrUpdatePatient(UserDto userDto);

    /**
     * Retrieves all patients.
     *
     * @return A list of User DTOs.
     */
    List<ResponsePatientDto> getAllPatients();

    /**
     * Fetches a patient with the help of their email
     *
     * @param email email of the specific patient
     * @return the dto of the specific patient
     */
    Patient getPatientByEmail(String email);

    /**
     * Retrieves all orders.
     *
     * @return A list of orders.
     */
    List<ResponseOrderDto> getOrders();

    /**
     * Retrieves an TestResult by orderID.
     *
     * @param orderId {@link Long} The ID of the SampleCollector.
     * @return The TestResult DTO.
     * @throws NoSuchElementException if the TestResult is not found.
     */
    TestResultDto getTestReport(Long orderId);

    /**
     * Deletes an patient by user emailId (soft deletion).
     *
     * @param email {@link UserDto} The ID of the TestResult to delete.
     * @throws RuntimeException if the TestResult is not found.
     */
    void deletePatient(String email);

    /**
     * Fetches orders of a specific patient
     *
     * @param patientDto {@link UserDto} patient whose test report has to be fetched
     * @return all the orders related to the patient
     */

    List<ResponseOrderDto> getOrdersByPatient(UserDto patientDto);

    void setupInitialData();
}
