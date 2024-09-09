package org.medx.elixrlabs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.RequestTestResultDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.TestResultDto;

/**
 * <p> Interface for LabService, defining the business operations related to lab module.
 * This interface is implemented by LabServiceImpl and defines the contract for
 * managing operations which are linked to the lab module. </p>
 */

@Service
public interface LabService {

    /**
     * Fetches all the orders related to a specific lab
     *
     * @return list of orders associated to a specific lab
     */

    List<ResponseOrderDto> getOrders();

    /**
     * Assigns a test report for each test in the order
     *
     * @param orderId id of the order in which the tests are present
     * @param resultDto report of each test in the order
     */

    void assignReport(long orderId, RequestTestResultDto resultDto);

    /**
     * Fetches the test result of a specific order
     *
     * @param orderId id of the order for which test result has to be fetched
     * @return test result of the specific order
     */

    TestResultDto getTestResultByOrder(long orderId);

}
