package com.taiga.taiga.models;

public class ProjectSprintModel {

    int projectId;
    int sprintId;
    String projectSlug;
    String sprintName;
    
    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public int getSprintId() {
        return sprintId;
    }
    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }
    public String getProjectSlug() {
        return projectSlug;
    }
    public void setProjectSlug(String projectSlug) {
        this.projectSlug = projectSlug;
    }
    public String getSprintName() {
        return sprintName;
    }
    public void setSprintName(String sprintName) {
        this.sprintName = sprintName;
    }
    
}
