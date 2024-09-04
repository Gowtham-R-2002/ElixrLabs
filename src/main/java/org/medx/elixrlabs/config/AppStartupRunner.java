package org.medx.elixrlabs.config;

import org.medx.elixrlabs.service.AdminService;
import org.medx.elixrlabs.service.LabTestService;
import org.medx.elixrlabs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>This class handles tasks that should be executed
 * when the application starts. It initializes essential
 * services and sets up initial data required for the application.</p>
 *
 * @author Gowtham R
 */
@Configuration
public class AppStartupRunner {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LabTestService labTestService;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            roleService.setupInitialData();
           // adminService.setupInitialData();
            labTestService.setupInitialData();
        };
    }
}
