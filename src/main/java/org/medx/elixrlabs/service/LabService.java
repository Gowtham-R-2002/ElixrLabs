package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabService {

    /**
     * Fetches all the orders related to the specific lab
     *
     * @return list of orders related to the lab
     */

    List<ResponseOrderDto> getOrders();

    /**
     *
     * @param testResult
     */

    void assignReport(TestResult testResult);

    void updateStatus(Long id);

    TestResultDto getTestResultByUser(UserDto patientDto, Long orderId);
}
