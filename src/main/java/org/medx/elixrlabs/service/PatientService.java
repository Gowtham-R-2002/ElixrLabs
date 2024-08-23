package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.RegisterAndLoginUserDto;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientService {
    RegisterAndLoginUserDto createPatient(RegisterAndLoginUserDto userDto);

    List<RegisterAndLoginUserDto> getAllPatients();

    boolean boolSlotAndReturnStatus();

    List<Order> getOrders(Long orderId);

    TestResult getTestReport(Long testReportId);

    User updatePatient(User user);

    void deletePatient(Long userId);
}
