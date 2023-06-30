package com.ser516groupC.SBCoupling.model;

import java.util.List;

public class SBCModel {

    private List<SBCNodes> nodes;
    private List<SBCEdges> edges;
    
    public List<SBCNodes> getNodes() {
        return nodes;
    }
    public void setNodes(List<SBCNodes> nodes) {
        this.nodes = nodes;
    }
    public List<SBCEdges> getEdges() {
        return edges;
    }
    public void setEdges(List<SBCEdges> edges) {
        this.edges = edges;
    }

    

}
