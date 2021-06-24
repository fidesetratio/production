package com.app.rest.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AlertManager {
	@PostMapping("/alert-hook")
	public void receiveAlertHook(@RequestBody Map request) throws Exception {
		System.out.println("-------------"+new Date()+"---------------");
		System.out.println(request);
		System.out.println("-------------------------------------------");
		System.out.println(" ");
		

	}
}
