package org.medx.elixrlabs.service.impl;

import java.util.List;
import java.util.Objects;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.Patient;
import org.medx.elixrlabs.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.mapper.TestResultMapper;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.util.LocationEnum;


/**
 * <p>
 * Service implementation for managing lab-related operations.
 * This class contains business logic for handling lab operations, including
 * order management, report assignments, and status updates. It acts as
 * a bridge between the controller layer and the other service layers.
 * </p>
 */
@Service
public class LabServiceImpl implements LabService {

    private static final Logger logger = LoggerFactory.getLogger(LabServiceImpl.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PatientService patientService;

    @Override
    public List<ResponseOrderDto> getOrders() {
        try {
            Admin admin = adminService.getAdminByEmail(SecurityContextHelper.extractEmailFromContext());
            System.out.println(admin);
            LocationEnum location = admin.getUser().getPlace();
            System.out.println(location);
            return orderService.getOrdersByLocation(location);
        } catch (Exception e) {
            logger.warn("Error while fetching orders: {}", e.getMessage());
            throw new LabException("Error while fetching orders: " + e.getMessage());
        }
    }

    @Override
    public void assignReport(TestResult testResult) {
        try {
            // To Do
            logger.info("Test result assigned successfully: {}", testResult);
        } catch (Exception e) {
            logger.warn("Error while assigning test report: {}", e.getMessage());
            throw new LabException("Error while assigning test report: " + e.getMessage());
        }
    }

    @Override
    public void updateStatus(Long id) {
        try {
            orderService.updateOrderStatus(id);
            logger.info("Order status updated successfully for order id: {}", id);
        } catch (Exception e) {
            logger.warn("Error while updating order status for order id: {}", id);
            throw new LabException("Error while updating order status for order id: " + id );
        }
    }

    @Override
    public TestResultDto getTestResultByUser(UserDto patientDto, Long orderId) {
        try {
            Patient patient = patientService.getPatientByEmail(patientDto.getEmail());
            OrderSuccessDto order = OrderMapper.toOrderSuccessDto(orderService.getOrder(orderId));
            List<ResponseOrderDto> patientOrders = patientService.getOrdersByPatient(patientDto);

            for (ResponseOrderDto patientOrder : patientOrders) {
                if (Objects.equals(order.getId(), patientOrder.getId())) {
                    TestResult testResult = patientService.getTestReport(orderId);
                    return TestResultMapper.toTestResultDto(testResult);
                }
            }
            logger.info("Test result not found for user: {} and order id: {}", patientDto.getEmail(), orderId);
            return null;
        } catch (Exception e) {
            logger.warn("Error while retrieving test result for user: {} and order id: {}", patientDto.getEmail(), orderId);
            throw new LabException("Error while retrieving test result for user: " + patientDto.getEmail() + " and order id: " + orderId);
        }
    }
}
