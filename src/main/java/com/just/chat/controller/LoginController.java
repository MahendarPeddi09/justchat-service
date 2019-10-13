package com.just.chat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.just.chat.model.AuthStatus;
import com.just.chat.model.LoginUser;
import com.just.chat.model.People;
import com.just.chat.model.User;
import com.just.chat.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	LoginService ser;

	@RequestMapping(value="/login",method=RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	People authenticateUser(@RequestBody LoginUser user,HttpServletRequest request) {
		
		AuthStatus stat = new AuthStatus();
		stat.setStatus(ser.verifyUser(user));
		People owner = null;
		if(stat.getStatus() == "success") {
			HttpSession session  = request.getSession();
			owner = ser.getOwner(user.getUserName());
			session.setAttribute("ownerName", owner.getUser_name());
			session.setAttribute("ownerId",owner.getPersonId()); 
			
		}
		return owner;
		
	}
//	@RequestMapping(value="/login",method=RequestMethod.GET,produces = "application/json")
//	@ResponseBody
//	String getUser() {
//		String status = "";
//		status = "success";
//		return status;
//	}
	@RequestMapping(value="/register",method=RequestMethod.POST,consumes = "application/json")
	@ResponseBody
	People saveUser(@RequestBody User user,HttpServletRequest request) {
		AuthStatus stat = new AuthStatus();
		stat.setStatus(ser.addUser(user));
		People owner = null;
		if(stat.getStatus() == "success") {
			//user.setUser_name(user_name);
			HttpSession session  = request.getSession();
			owner = ser.getOwner(user.getUserName());
			session.setAttribute("ownerName", owner.getUser_name());
			session.setAttribute("ownerId",owner.getPersonId()); 
			
		}
		return owner;
	}
	@RequestMapping(value = "/user/session", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	People getSession(HttpServletRequest request) {
		//System.out.println("session id - "+request.getSession().getId()+"-"+request.getHeader("SESSION"));
		People ownerInfo = new People();
		ownerInfo.setUser_name((String)request.getSession().getAttribute("ownerName"));
		ownerInfo.setPersonId((Long)request.getSession().getAttribute("ownerId"));
		System.out.println("session id end - "+request.getSession().getId());
		return ownerInfo;
	}
	@RequestMapping(value = "/user/session/kill", method = RequestMethod.GET, produces = "application/json")
	void killSession(HttpSession session) {
		//System.out.println("killing session : "+session.getId());
		session.invalidate();
	}
	
}
