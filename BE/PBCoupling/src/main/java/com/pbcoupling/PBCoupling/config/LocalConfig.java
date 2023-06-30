package com.pbcoupling.PBCoupling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("local")
@Configuration
public class LocalConfig {
    @PostConstruct
    public void environmentMessage(){
        System.out.println("Opening the local environment");
    }
}
