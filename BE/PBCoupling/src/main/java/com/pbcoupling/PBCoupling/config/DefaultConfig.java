package com.pbcoupling.PBCoupling.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DefaultConfig {
    @PostConstruct
    public void environmentMessage(){
        System.out.println("Opening the default profile");
    }
}
