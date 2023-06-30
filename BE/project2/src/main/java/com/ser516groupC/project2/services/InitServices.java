package com.ser516groupC.project2.services;

import com.ser516groupC.project2.model.InitModel;
import org.springframework.stereotype.Service;

@Service
public class InitServices {
    public InitModel testInitClass() {
        InitModel initModel = new InitModel();
        initModel.setSampleText("Initializing SER 516 Project2 GroupC");
        return initModel;
    }
}
