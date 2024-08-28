package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.OrderLocationDto;
import org.medx.elixrlabs.dto.SampleCollectorDto;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabService {
    List<OrderLocationDto> getOrders();

    void assignReport(TestResult testResult);

    void updateStatus(Long id);
}
