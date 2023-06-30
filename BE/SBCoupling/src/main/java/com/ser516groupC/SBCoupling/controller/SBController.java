package com.ser516groupC.SBCoupling.controller;



import com.ser516groupC.SBCoupling.model.SBCModel;
import com.ser516groupC.SBCoupling.services.SBCServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import taiga services here
@RestController
@RequestMapping("/")
public class SBController {
    @Autowired
    SBCServices SBC;

    @CrossOrigin(origins = "http://localhost:3000") 
    @GetMapping("/getSBC")
    public SBCModel firstController(@RequestParam("Project") String projectName,
                                   @RequestParam("Sprint") String sprintName) {

        return SBC.fetchSBResponse(projectName, sprintName);
    }

}
