package com.ValueAUC.ValueAUC.Controller;

import com.ValueAUC.ValueAUC.Model.ValueAUCModel;
import com.ValueAUC.ValueAUC.Service.ValueAUCService;

import java.text.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import taiga services here
@RestController
@RequestMapping("/")
public class ValueAUCController {

    @Autowired
    ValueAUCService AUC;
    @CrossOrigin(origins = "http://localhost:3000")

    @GetMapping("/getValueAUC")
    public ValueAUCModel firstController(@RequestParam("Project") String projectName,
                                    @RequestParam("Sprint") String sprintName) throws ParseException {
        
        return AUC.fetchAUCResponse(projectName, sprintName);
    }
}

