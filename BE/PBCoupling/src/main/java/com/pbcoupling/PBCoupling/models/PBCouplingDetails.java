package com.pbcoupling.PBCoupling.models;

import java.util.HashMap;
import java.util.List;

public class PBCouplingDetails {

    String type;

    HashMap<String, List<String >> dependencyMap;

    public PBCouplingDetails() {
        dependencyMap = new HashMap<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, List<String>> getDependencyMap() {
        return dependencyMap;
    }

    public void setDependencyMap(HashMap<String, List<String>> dependencyMap) {
        this.dependencyMap = dependencyMap;
    }
}
