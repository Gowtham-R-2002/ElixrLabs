package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.AdminDto;
import org.medx.elixrlabs.dto.RequestUserNameDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SuperAdminService {
    void createOrUpdateAdmin(AdminDto adminDto);

    void deleteAdmin(RequestUserNameDto requestUserNameDto);

    Map<String, String> getAdmins();

    void blockUser(RequestUserNameDto requestUserNameDto);
}
