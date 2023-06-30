package com.taiga.taiga.models;

import java.time.LocalDateTime;
import java.util.Date;

public class TaigaUserStory {
    private Integer id;
    private String subject;
    private String finishDate;
    private String businessValue;
    private String userStoryPoints;

    private Integer refNumber;

    private CouplingCV couplingCV;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(String businessValue) {
        this.businessValue = businessValue;
    }

    public String getUserStoryPoints() {
        return userStoryPoints;
    }

    public void setUserStoryPoints(String userStoryPoints) {
        this.userStoryPoints = userStoryPoints;
    }

    public CouplingCV getCouplingCV() {
        return couplingCV;
    }

    public void setCouplingCV(CouplingCV couplingCV) {
        this.couplingCV = couplingCV;
    }

    public Integer getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(Integer refNumber) {
        this.refNumber = refNumber;
    }
}
