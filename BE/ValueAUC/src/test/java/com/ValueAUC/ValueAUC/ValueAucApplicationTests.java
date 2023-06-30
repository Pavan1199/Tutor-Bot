package com.ValueAUC.ValueAUC;

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


@RunWith(SpringRunner.class)
@WebMvcTest(value = ValueAUCController.class, secure = false)

@SpringBootTest
public class ValueAucApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ValueAUCService valueAUCService;
	
	AUCService mockAUCService = new AUCService ("medium","project 2", "Sprint 2", "2022-04-15",
			Arrays.asList("businessValue", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'","finishDate"),"estimated_start","estimated_finish");
	
	String exampleServiceJson = "{\"Value\":\"Project\":\"description\":\"Sprint\":\"digit\":[\"businessValue\":\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\":"
			+ "finishDate\"]:\"estimated_start\":\"estimated_finish\"}";
	

	@Test
	public void contextLoads() throws Exception{
		Mockito.when(
				valueAUCService.fetchAUCResponse(Mockito.anyString(), Mockito
						.anyString())).thenReturn(mockAUCService);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/ValueAUCService/Project1/ValueAUC/Project1").accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{value:3.0,project:2,sprint:2,date:2022-04-15}";

		

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

}
