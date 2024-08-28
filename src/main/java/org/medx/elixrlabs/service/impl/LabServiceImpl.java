package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.*;
import org.medx.elixrlabs.helper.SecurityContextHelper;
import org.medx.elixrlabs.mapper.OrderMapper;
import org.medx.elixrlabs.mapper.TestResultMapper;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.LabService;
import org.medx.elixrlabs.service.OrderService;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.SampleCollectorService;
import org.medx.elixrlabs.util.LocationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Override
    public List<OrderLocationDto> getOrders() {
        User admin =  userService.loadUserByUsername(SecurityContextHelper.extractEmailFromContext());
        LocationEnum location = LocationEnum.valueOf(jwtService.getAddress());
        return orderService.getOrdersByLocation(location);
    }

    @Override
    public void assignReport(TestResult testResult) {
    }

    @Override
    public void updateStatus(Long id) {
        orderService.updateOrderStatus(id);
    }

    @Override
    public TestResultDto getTestResultByUser(UserDto patientDto, Long orderId) {
        User patient = patientService.getPatientByEmail(patientDto.getEmail());
        OrderSuccessDto order = OrderMapper.toOrderSuccessDto(orderService.getOrder(orderId));
        for(OrderSuccessDto patientOrder : patientService.getOrdersByPatient(patientDto)) {
            if(order == patientOrder) {
                return TestResultMapper.toTestResultDto(patientService.getTestReport(orderId));
            }
        }
        return null;
    }
}
