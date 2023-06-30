package com.ser516groupC.ValueBD;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ser516groupC.ValueBD.controller.ValueBDController;

@WebMvcTest(ValueBDController.class)
@ComponentScan(basePackages = {"com.ser516groupC.ValueBD"})
public class ValueBDControllerTests {

    @Autowired
    MockMvc mock;

    @Test
    public void getValueBD_success() throws Exception
    {
        mock.perform( MockMvcRequestBuilders
        .get("/getValueBD") //?Project=dbiegan-sbs-bieganski&Sprint=Sprint 3
        .param("Project","dbiegan-sbs-bieganski" )
        .param("Sprint", "Sprint 3"))
        .andExpect(status().isOk());
    }
   

    
}
