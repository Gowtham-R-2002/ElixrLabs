package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.LabService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabServiceImpl implements LabService {
    @Override
    public List<Order> getOrders() {
        return List.of();
    }

    @Override
    public void assignReport(TestResult testResult) {

    }

    @Override
    public void updateStatus() {

    }
}
