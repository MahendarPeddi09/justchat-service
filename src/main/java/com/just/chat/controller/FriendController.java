package com.just.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.just.chat.model.JCMessage;
import com.just.chat.model.People;
import com.just.chat.service.FriendService;

@RestController
public class FriendController {
	
	@Autowired
	FriendService fs;
	
	@RequestMapping(value="/friend/{id}/{id1}",method=RequestMethod.POST,produces="application/json",consumes = "application/json")
	@ResponseBody
	public String addFriend(@PathVariable("id") Long id,@PathVariable("id1") Long id1) {
		System.out.println(id);
		
		return fs.addFriend(id,id1);
	}
	@RequestMapping(value="/friend/{id}",method=RequestMethod.DELETE,produces="application/json",consumes="applicaation/json")
	public String deleteFriend() {
		
		return null;
	}
	@SuppressWarnings("null")
	@RequestMapping(value="/friend/{name}",method=RequestMethod.GET)
	public List<People> getPeople(@PathVariable("name") String name) {
		 if(name != null || !name.isEmpty()) {
			 return fs.getPeople(name);
		 }
		return null;
	}
	@CrossOrigin(origins="http://localhost:3000")
	@RequestMapping(value="/friends/{id}",method=RequestMethod.GET)
	public List<People> getContacts(@PathVariable("id") Long id){
		
		return fs.getFriends(id);
	}
	@RequestMapping(value="/friends/active/{id}",method=RequestMethod.GET)
	public List<JCMessage> getActiveContacts(@PathVariable("id") Long id){
		return fs.getActiveContacts(id);
	}
	
	@RequestMapping(value="/friends/newuser/{id}",method=RequestMethod.GET)
	public People getNewUser(@PathVariable("id") Long id) {
		return fs.getNewUser(id);
	}
	
	
//	public void requestFriend() {}
//	public void acceptFriend() {}

}
