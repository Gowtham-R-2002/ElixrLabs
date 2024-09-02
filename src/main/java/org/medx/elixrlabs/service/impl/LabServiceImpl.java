package org.medx.elixrlabs.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.mapper.TestResultMapper;
import org.medx.elixrlabs.model.*;
import org.medx.elixrlabs.service.*;
import org.medx.elixrlabs.util.DateUtil;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
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

    @Autowired
    private LabTestService labTestService;

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private EmailService emailService;

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
            throw new LabException("Error while fetching orders: " , e);
        }
    }

    @Override
    public void assignReport(long orderId, RequestTestResultDto resultDto) {
        try {
            Order order = orderService.getOrder(orderId);
            if (order.getTestStatus().equals(TestStatusEnum.PENDING) && order.getSampleCollectionPlace().equals(TestCollectionPlaceEnum.HOME)) {
                throw new LabException("Cannot update report for patient whose sample is not collected!");
            }
            Map<LabTestDto, String> parsedResultDto = resultDto.getTestIdWithResult()
                    .stream()
                    .collect(Collectors.toMap(
                            id -> labTestService.getLabTestById(id.getTestId()),
                            RequestTestIdWithResultDto::getResult));
            List<IndividualTestReportDto> individualTests = parsedResultDto.keySet().stream().map(parsedResult -> IndividualTestReportDto.builder()
                    .testName(parsedResult.getName())
                    .actualValue(parsedResult.getDefaultValue())
                    .presentValue(parsedResultDto.get(parsedResult))
                    .build()).toList();
            List<String> result = individualTests.stream().map(individualTestReportDto -> individualTestReportDto.getTestName() + ", " +
                    individualTestReportDto.getActualValue() + ", " +
                    individualTestReportDto.getPresentValue() + ".").toList();
            TestResult testResult = TestResult.builder()
                    .name(order.getPatient().getUser().getUsername())
                    .ageAndGender(DateUtil.getAge(order.getPatient().getUser().getDateOfBirth())
                            + " " + order.getPatient().getUser().getGender().toString())
                    .generatedAt(LocalDateTime.now())
                    .result(result)
                    .orderDate(order.getSlot().getDateSlot())
                    .build();
            TestResult savedTestResult = testResultService.addResult(testResult);
            emailService.sendTestResult(testResult);
            order.setTestResult(savedTestResult);
            orderService.createOrUpdateOrder(order);
            logger.info("Test result assigned successfully: {}", resultDto);
        } catch (Exception e) {
            logger.warn("Error while assigning test report: {}", e.getMessage());
            throw new LabException("Error while assigning test report: ", e);
        }
    }

    @Override
    public void updateStatus(Long id) {
        try {
            orderService.updateOrderStatus(id);
            logger.info("Order status updated successfully for order id: {}", id);
        } catch (Exception e) {
            logger.warn("Error while updating order status for order id: {}", id);
            throw new LabException("Error while updating order status for order id: " + id, e );
        }
    }

    @Override
    public TestResultDto getTestResultByOrder(long orderId) {
        Order order = orderService.getOrder(orderId);
        return TestResultMapper.toTestResultDto(order.getTestResult());
    }

}
