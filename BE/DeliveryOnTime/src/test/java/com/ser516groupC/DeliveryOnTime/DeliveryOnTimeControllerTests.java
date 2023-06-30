package com.ser516groupC.DeliveryOnTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ser516groupC.DeliveryOnTime.controller.DeliveryOnTimeController;
import com.ser516groupC.DeliveryOnTime.services.DeliveryOnTimeServices;

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

@WebMvcTest(DeliveryOnTimeController.class)
@ComponentScan(basePackages = {"com.ser516groupC.DeliveryOnTime"})
public class DeliveryOnTimeControllerTests {

    @Autowired
    MockMvc mock;

    @Test
    public void getDeliveryOnTime_success() throws Exception
    {
        mock.perform( MockMvcRequestBuilders
        .get("/getDeliveryOnTime") //?Project=dbiegan-sbs-bieganski&Sprint=Sprint 3
        .param("Project","dbiegan-sbs-bieganski" )
        .param("Sprint", "Sprint 3"))
        .andExpect(status().isOk());
    }
   

    
}
