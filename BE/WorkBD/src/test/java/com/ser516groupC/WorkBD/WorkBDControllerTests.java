package com.ser516groupC.WorkBD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ser516groupC.WorkBD.controller.WorkBDController;
import com.ser516groupC.WorkBD.services.WorkBDServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@WebMvcTest(WorkBDController.class)
@ComponentScan(basePackages = {"com.ser516groupC.WorkBD"})
public class WorkBDControllerTests {

    @Autowired
    MockMvc mock;

    @Test
    public void getWorkBD_success() throws Exception
    {
        mock.perform( MockMvcRequestBuilders
        .get("/workBD") //?Project=dbiegan-sbs-bieganski&Sprint=Sprint 3
        .param("Project","dbiegan-sbs-bieganski" )
        .param("Sprint", "Sprint 3"))
        .andExpect(status().isOk());
    }
   

    
}
