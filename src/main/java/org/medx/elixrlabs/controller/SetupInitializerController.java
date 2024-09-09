package org.medx.elixrlabs.controller;

import org.medx.elixrlabs.service.impl.SetupInitializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("initialize")
public class SetupInitializerController {

    @Autowired
    private SetupInitializerService setupInitializerService;

    @PostMapping
    public ResponseEntity<HttpStatus.Series> initialize() {
        setupInitializerService.initializeData();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
