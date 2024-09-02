package org.medx.elixrlabs.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.ResponsePatientDto;
import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.dto.UserDto;

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
     * Fetches all the patients.
     *
     * @return list of patients.
     */
    List<ResponsePatientDto> getAllPatients();

    /**
     * Fetches a patient with the help of their email
     *
     * @param email email of the patient to be fetched
     * @return the dto of the specific patient
     */
    ResponsePatientDto getPatientByEmail(String email);

    /**
     * Fetches all the orders of a specific patient.
     *
     * @return list of orders.
     */
    List<ResponseOrderDto> getOrders();

    /**
     * Fetches the TestResult of the patient by orderID.
     *
     * @param orderId ID of the SampleCollector.
     * @return The TestResult DTO.
     * @throws NoSuchElementException if the TestResult is not found.
     */
    TestResultDto getTestReport(Long orderId);

    /**
     * Deletes an patient by user emailId (soft deletion).
     *
     * @param email The ID of patient to be deleted.
     * @throws RuntimeException if the patient is not found.
     */
    void deletePatient(String email);

    /**
     * Fetches orders of a specific patient
     *
     * @param patient {@link RequestUserNameDto} patient whose orders has to be fetched
     * @return all the orders related to the patient
     */

    List<ResponseOrderDto> getOrdersByPatient(RequestUserNameDto patient);

    void setupInitialData();
}
