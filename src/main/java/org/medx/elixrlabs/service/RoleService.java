package org.medx.elixrlabs.service;

import org.medx.elixrlabs.model.Role;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.util.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void setupInitialData() {
        roleRepository.save(Role.builder().name(RoleEnum.ADMIN).build());
        roleRepository.save(Role.builder().name(RoleEnum.PATIENT).build());
        roleRepository.save(Role.builder().name(RoleEnum.SAMPLE_COLLECTOR).build());
    }
}
