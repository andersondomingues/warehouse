package com.warehouse.controllers;

import com.warehouse.models.HelloWorld;
import com.warehouse.services.HelloWorldService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWordController {

	@GetMapping("/helloWorld")
	public HelloWorld helloWorld(
		@RequestParam(value = "hello", defaultValue = "Hello") String hello,
		@RequestParam(value = "world", defaultValue = "World") String world) {

			HelloWorldService service = new HelloWorldService();
			return service.addHelloWorld(hello, world);
	}
}
