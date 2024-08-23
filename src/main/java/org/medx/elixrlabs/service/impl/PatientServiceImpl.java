package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.dto.RegisterAndLoginUserDto;
import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.mapper.UserMapper;
import org.medx.elixrlabs.model.Order;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.UserRepository;
import org.medx.elixrlabs.service.PatientService;
import org.medx.elixrlabs.service.RoleService;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public RegisterAndLoginUserDto createPatient(RegisterAndLoginUserDto userDto) {
        User user = UserMapper.toUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(roleService.getRoleByName(RoleEnum.ROLE_PATIENT)));
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new LabException("Error while saving Patient of email : " + userDto.getEmail());
        }
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public List<RegisterAndLoginUserDto> getAllPatients() {
        return userRepository.fetchAllPatients().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public boolean boolSlotAndReturnStatus() {
        return false;
    }

    @Override
    public List<Order> getOrders(Long orderId) {
        return List.of();
    }

    @Override
    public TestResult getTestReport(Long testReportId) {
        return null;
    }

    @Override
    public User updatePatient(User user) {
        return null;
    }

    @Override
    public void deletePatient(Long userId) {

    }
}
