package com.ser516groupC.project2.controller;

import com.ser516groupC.project2.model.InitModel;
import com.ser516groupC.project2.services.InitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class InitController {

    @Autowired
    InitServices init;

    @GetMapping("/test")
    public InitModel firstController() {
        return init.testInitClass();
    }
}
