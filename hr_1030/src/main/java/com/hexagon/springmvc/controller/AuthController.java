package com.hexagon.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/loginform")
	public String getLoginForm() {
		return "secure/loginform";//WEB-INF/view/secure/loginform.jsp
	}
}
