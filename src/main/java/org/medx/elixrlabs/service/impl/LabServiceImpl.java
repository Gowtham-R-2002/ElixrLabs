package org.medx.elixrlabs.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.dto.IndividualTestReportDto;
import org.medx.elixrlabs.dto.LabTestDto;
import org.medx.elixrlabs.dto.RequestTestIdWithResultDto;
import org.medx.elixrlabs.dto.RequestTestResultDto;
import org.medx.elixrlabs.dto.ResponseOrderDto;
import org.medx.elixrlabs.dto.TestResultDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.TestResultMapper;
import org.medx.elixrlabs.model.Admin;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.EmailService;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.TestResultService;
import org.medx.elixrlabs.util.DateUtil;
import org.medx.elixrlabs.util.LocationEnum;
import org.medx.elixrlabs.util.TestCollectionPlaceEnum;
import org.medx.elixrlabs.util.TestStatusEnum;


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
    private OrderService orderService;

    @Autowired
    private AdminService adminService;

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
            LocationEnum location = admin.getUser().getPlace();
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
                logger.warn("Cannot update report for patient whose sample is not collected!");
                throw new LabException("Cannot update report for patient whose sample is not collected!");
            }
            Set<Long> userOrderTestIds = (order.getTests().isEmpty() || order.getTests() == null) ? new HashSet<>() : order.getTests().stream().map(test -> test.getId()).collect(Collectors.toSet());
            Set<Long> userOrderPackageTestIds = order.getTestPackage() == null ? new HashSet<>() :  order.getTestPackage().getTests().stream().map(test -> test.getId()).collect(Collectors.toSet());
            userOrderTestIds.addAll(userOrderPackageTestIds);
            Set<Long> resultTestIds = resultDto.getTestIdsWithResults().stream().map(result -> result.getTestId()).collect(Collectors.toSet());
            if(!userOrderTestIds.equals(resultTestIds)) {
                logger.warn("Result Test ID's doesn't match with Patient Order test/package ID's");
                throw new BadRequestException("Result Test ID's doesn't match with Patient Order test/package ID's");
            }
            Map<LabTestDto, String> parsedResultDto = resultDto.getTestIdsWithResults()
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
            orderService.updateOrderStatus(orderId);
            logger.info("Test result assigned successfully: {}", resultDto);
        } catch (Exception e) {
            logger.warn("Error while assigning test report: {}", e.getMessage());
            throw new LabException("Error while assigning test report: ", e);
        }
    }

    @Override
    public TestResultDto getTestResultByOrder(long orderId) {
        Order order = orderService.getOrder(orderId);
        if(order.getTestStatus().equals(TestStatusEnum.PENDING) || order.getTestStatus().equals(TestStatusEnum.IN_PROGRESS)) {
            logger.warn("Test Result not available yet ! for order ID : {}", orderId );
            throw new NoSuchElementException("Test Result not available yet ! for order ID : " + orderId);
        }
        return TestResultMapper.toTestResultDto(order.getTestResult());
    }

}
