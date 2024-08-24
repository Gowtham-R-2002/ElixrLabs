package org.medx.elixrlabs.config;

import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.impl.LabTestServiceImpl;
import org.medx.elixrlabs.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupRunner {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LabTestServiceImpl labTestService;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            roleService.setupInitialData();
            adminService.setupInitialData();
            labTestService.setupInitialData();
        };
    }
}
