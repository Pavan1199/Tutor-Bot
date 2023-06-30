package com.taiga.taiga.models;

import java.util.List;

public class TaigaProjectsSprintModel {
    String projectName;
    List<String> sprintName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getSprintName() {
        return sprintName;
    }

    public void setSprintName(List<String> sprintName) {
        this.sprintName = sprintName;
    }
}
