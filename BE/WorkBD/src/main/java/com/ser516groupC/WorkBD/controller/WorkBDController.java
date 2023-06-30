package com.ser516groupC.WorkBD.controller;

import com.ser516groupC.WorkBD.model.WorkBDModel;
import com.ser516groupC.WorkBD.services.WorkBDServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import taiga services here
@RestController
@RequestMapping("/")
public class WorkBDController {

    @Autowired
    WorkBDServices WorkBD;

    @GetMapping("/getWorkBD")
    public WorkBDModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) {

        return WorkBD.fetchWorkBDResponse(projectName, sprintName);
    }

}
