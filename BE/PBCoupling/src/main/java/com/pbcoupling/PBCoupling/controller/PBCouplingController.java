package com.pbcoupling.PBCoupling.controller;

import com.pbcoupling.PBCoupling.models.PBCouplingDetails;
import com.pbcoupling.PBCoupling.models.TaigaMilestoneRequestModel;
import com.pbcoupling.PBCoupling.services.PBCouplingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PBCouplingController {

    @Autowired
    PBCouplingService pbCouplingService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/pb-coupling")
    public List<PBCouplingDetails> getPBCouplingDetails(@RequestParam("Project") String projectName,
                                                        @RequestParam("Sprint") String sprintName) {
        TaigaMilestoneRequestModel taigaMilestoneRequestModel = new TaigaMilestoneRequestModel();
        taigaMilestoneRequestModel.setProjectName(projectName);
        taigaMilestoneRequestModel.setSprintName(sprintName);

        return pbCouplingService.getPBCouplingDetails(taigaMilestoneRequestModel);
    }
}
