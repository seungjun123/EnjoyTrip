package com.mycom.mytrip.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mycom.mytrip.users.dto.UserDto;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ModelAttribute("userInfo")
	public Object getUserInfoFromSession(HttpSession session) {
		return session.getAttribute("userInfo");
	}
}
