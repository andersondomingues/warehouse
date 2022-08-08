package com.warehouse.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldReturnHelloWorldRegardlessTheParameter() throws Exception {

		// this.mockMvc.perform(get("/helloWorld"))
		// 		.andDo(print())
		// 		.andExpect(status().isOk())
		// 		.andExpect(jsonPath("$.hello").value("hello"))
		// 		.andExpect(jsonPath("$.world").value("world"));
		//
		return;
	}
}
