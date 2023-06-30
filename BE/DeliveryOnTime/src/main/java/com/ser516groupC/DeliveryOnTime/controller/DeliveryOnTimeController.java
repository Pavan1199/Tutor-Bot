package com.ser516groupC.DeliveryOnTime.controller;


import com.ser516groupC.DeliveryOnTime.model.DeliveryOnTimeModel;
import com.ser516groupC.DeliveryOnTime.services.DeliveryOnTimeServices;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import Taiga services here
@RestController
@RequestMapping("/")
public class DeliveryOnTimeController {

    @Autowired
    DeliveryOnTimeServices DeliveryOnTime;
    @CrossOrigin(origins = "http://localhost:3000")

    @GetMapping("/getDeliveryOnTime")
    public DeliveryOnTimeModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) {

        return DeliveryOnTime.fetchDeliveryOnTimeResponse(projectName, sprintName);
    }

}
