package com.just.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.just.chat.model.LoginUser;
import com.just.chat.model.User;
import com.just.chat.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	LoginService ser;

	@RequestMapping(value="/login",method=RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	String authenticateUser(@RequestBody LoginUser user) {
		String status = "";
		status = ser.verifyUser(user);
		return status;
	}
	@RequestMapping(value="/login",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	String getUser() {
		String status = "";
		status = "success";
		return status;
	}
	@RequestMapping(value="/register",method=RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	String saveUser(@RequestBody User user) {
		String status = "";
		status = ser.addUser(user);
		return status;
	}
}
