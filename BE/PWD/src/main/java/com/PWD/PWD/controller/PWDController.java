package com.PWD.PWD.controller;

import com.PWD.PWD.model.PWDModel;
import com.PWD.PWD.services.PWDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class PWDController {
    @Autowired
    PWDService init;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getPWD")
    public PWDModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) {

        return init.fetchPWDResponse(projectName, sprintName);
    }

}
