package com.mycom.mytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageMoveController {
	
	@GetMapping("/")
	public String showIndexPage() {
		System.out.println(123);
		return "index.html";
	}
	
	@GetMapping("/error")
	public String redirectRoot() {
		return "index.html";
	}
}
