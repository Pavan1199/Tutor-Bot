package com.ser516groupC.ValueBD.controller;

import com.ser516groupC.ValueBD.model.ValueBDModel;
import com.ser516groupC.ValueBD.services.ValueBDServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import Taiga services here
@RestController
@RequestMapping("/")
public class ValueBDController {

    @Autowired
    ValueBDServices ValueBD;
    @CrossOrigin(origins = "http://localhost:3000")

    @GetMapping("/getValueBD")
    public ValueBDModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) {

        return ValueBD.fetchValueBDResponse(projectName, sprintName);
    }

}