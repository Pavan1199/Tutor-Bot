package com.ser516groupC.BDConsistency.controller;

import com.ser516groupC.BDConsistency.model.BDCModel;
import com.ser516groupC.BDConsistency.services.BDCServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import taiga services here
@RestController
@RequestMapping("/")
public class BDCController {

    @Autowired
    BDCServices BDC;

    @GetMapping("/getBDConsistency")
    public BDCModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) {
//        System.out.println(projectName);
//        System.out.println(sprintName);
//        return taigaServiceAPI.request(projectName, sprintName);
        return BDC.fetchBDCResponse(projectName, sprintName);
    }

    @GetMapping("/getBDConsistency1")
    public String firstController() {
//        System.out.println(projectName);
//        System.out.println(sprintName);
//        return taigaServiceAPI.request(projectName, sprintName);
        return "Hello World";
    }
}
