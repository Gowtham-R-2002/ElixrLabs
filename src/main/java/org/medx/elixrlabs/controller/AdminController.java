package org.medx.elixrlabs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.medx.elixrlabs.service.AdminService;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

//    @PutMapping("/sample-collectors/{id}")
//    public SampleCollectorDto verifySampleCollector(@PathVariable long id) {
//        return adminService.verifySampleCollector(id);
//    }

    @GetMapping
    public String greet() {
        return "Hi admin";
    }


}
