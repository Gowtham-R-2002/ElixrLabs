package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("sample-collector")
    public User createSampleCollector(@RequestBody User user) {
        return adminService.createSampleCollector(user);
    }

    @GetMapping("sample-collector")
    public List<User> getAllSampleCollectors() {
        return adminService.getAllSampleCollectors();
    }
}
