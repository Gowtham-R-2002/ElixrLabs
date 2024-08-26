package org.medx.elixrlabs.config;

import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupRunner {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleServiceImpl roleServiceimpl;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            roleServiceimpl.setupInitialData();
            adminService.setupInitialData();
        };
    }
}
