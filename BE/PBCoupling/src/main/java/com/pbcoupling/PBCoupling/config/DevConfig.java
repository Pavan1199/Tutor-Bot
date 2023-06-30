package com.pbcoupling.PBCoupling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("dev")
@Configuration
public class DevConfig {

    @PostConstruct
    public void environmentMessage(){
        System.out.println("Opening the DEV environment");
    }
}
