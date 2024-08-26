package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabService {
    List<Order> getOrders();

    void assignReport(TestResult testResult);

    void updateStatus();
}
