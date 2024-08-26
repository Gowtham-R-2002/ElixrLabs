package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LabController {
    @Autowired
    private LabService labService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(labService.getOrders(), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<HttpStatus.Series> updateOrderStatus() {
        labService.updateStatus();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus.Series> updateReport(TestResult testResult) {
        labService.assignReport(testResult);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
