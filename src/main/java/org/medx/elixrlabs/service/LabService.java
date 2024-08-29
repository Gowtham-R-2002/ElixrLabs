package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabService {
    List<ResponseOrderDto> getOrders();

    void assignReport(long orderId, RequestTestResultDto resultDto);

    void updateStatus(Long id);

    TestResultDto getTestResultByOrder(long orderId);

    TestResultDto getTestResultByUser(Long orderId);
}
