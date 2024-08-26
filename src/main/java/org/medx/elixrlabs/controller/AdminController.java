package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
