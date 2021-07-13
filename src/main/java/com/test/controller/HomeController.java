package com.test.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "*",allowedHeaders ="authorization")
public class HomeController {
	
	
	@GetMapping("/users")
	public String home()
	{
		System.out.println("I reached to users control");
		return "{\"name\":\"Nilesh\"}";
	}
}
