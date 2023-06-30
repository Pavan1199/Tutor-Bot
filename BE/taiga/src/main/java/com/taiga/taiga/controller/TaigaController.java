package com.taiga.taiga.controller;

import com.taiga.taiga.models.TaigaAuthModel;
import com.taiga.taiga.models.TaigaMilestoneRequestModel;
import com.taiga.taiga.models.TaigaProjectsSprintModel;
import com.taiga.taiga.models.TaigaUserStory;
import com.taiga.taiga.services.TaigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taiga")
public class TaigaController {

    @Autowired
    TaigaService taigaService;

    public String taigaAuthToken() {
        return taigaService.getTaigaAuthToken();
    }
    
    @CrossOrigin
    @PostMapping("/auth-input")
    public HttpStatus taigaAuthInput(@RequestBody TaigaAuthModel taigaAuthModel) {
        if(taigaService.authenticateTaiga(taigaAuthModel))
            return HttpStatus.OK;
        return HttpStatus.UNAUTHORIZED;
    }
    @CrossOrigin
    @GetMapping("/projects")
    public List<TaigaProjectsSprintModel> taigaProjectSprintList() {
        return taigaService.taigaProjectList();
    }
    @CrossOrigin
    @PostMapping("/milestone-stats")
    public String taigaMilestoneStats(@RequestBody TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        return taigaService.taigaMilestoneStatsDetails(taigaMilestoneRequestModel);
    }
    @CrossOrigin
    @PostMapping("/project-stories")
    public List<TaigaUserStory> taigaUserStory(@RequestBody TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        return taigaService.taigaProjectUserStoriesList(taigaMilestoneRequestModel);
    }

    @CrossOrigin
    @PostMapping("/project-all-stories")
    public List<TaigaUserStory > taigaProjectAllUserStories(@RequestBody TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        return taigaService.taigaAllProjectUserStoriesList(taigaMilestoneRequestModel);
    }

    @CrossOrigin
    @PostMapping("/sprint-all-stories")
    public List<TaigaUserStory > taigaSprintAllUserStories(@RequestBody TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        return taigaService.taigaGetSprintUserStories(taigaMilestoneRequestModel);
    }
}
