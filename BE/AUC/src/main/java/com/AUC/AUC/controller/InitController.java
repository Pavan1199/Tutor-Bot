package com.AUC.AUC.controller;

import com.AUC.AUC.model.AUCModel;
import com.AUC.AUC.services.InitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class InitController {
    @Autowired
    InitServices init;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAUC")
    public AUCModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) {

        return init.fetchAUCResponse(projectName, sprintName);
    }

}
