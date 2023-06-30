package com.ser516groupC.ValueBD;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ValueAUC.ValueAUC.Controller.ValueAUCController;
import com.ValueAUC.ValueAUC.Service.ValueAUCService;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ValueAUCController.class, secure = false)

@SpringBootTest
class ValueBdApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ValueBDServices valueBDServices;

	BDService mockBDService = new BDService ("Project 2", "Sprint3");

	String exampleServiceJson = "{\" Project\":\"Sprint\ "}";

	@Test
	void contextLoads() throws Exception {

		Mockito.when(
				valueBDServices.fetchBDResponse(Mockito.anyString(), Mockito
						.anyString())).thenReturn(mockBDService);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/ValueBDService/Project1/ValueBD/Project1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{project:2, sprint:3}";

		

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);

	}

}
