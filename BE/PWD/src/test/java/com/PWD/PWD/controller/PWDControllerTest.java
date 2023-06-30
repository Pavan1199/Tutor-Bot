package com.PWD.PWD.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PWDController.class)
@ComponentScan(basePackages = {"com.PWD.PWD"})
class PWDControllerTest {


    @Autowired
    MockMvc mock;

    @Test
    public void getPWD_Succes() throws Exception
    {
        mock.perform( MockMvcRequestBuilders
                        .get("/getPWD")
                        .param("Project","dbiegan-sbs-bieganski" )
                        .param("Sprint", "Sprint 3"))
                .andExpect(status().isOk());
    }

    // @AfterEach
    //void tearDown() {


   // }
}