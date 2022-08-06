package com.warehouse.controllers;

import com.warehouse.models.HelloWorldModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

	@GetMapping("/helloWorld")
	public HelloWorldModel helloWorld(
		@RequestParam(value = "name", defaultValue = "World") String name) {
			return new HelloWorldModel("hello", "world");
	}
}
