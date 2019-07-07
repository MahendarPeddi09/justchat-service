package com.just.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.just.chat.model.People;
import com.just.chat.service.FriendService;

@RestController
public class FriendController {
	
	@Autowired
	FriendService fs;
	
	@RequestMapping(value="/friend/{id}",method=RequestMethod.POST,produces="application/json",consumes = "application/json")
	@ResponseBody
	public String addFriend(@PathVariable("id") Long id) {
		System.out.println(id);
		
		return fs.addFriend(id);
	}
	@RequestMapping(value="/friend/{id}",method=RequestMethod.DELETE,produces="application/json",consumes="applicaation/json")
	public String deleteFriend() {
		
		return null;
	}
	@RequestMapping(value="/friend/{name}",method=RequestMethod.GET)
	public List<People> getPeople(@PathVariable("name") String name) {
		 if(name != null || !name.isEmpty()) {
			 return fs.getPeople(name);
		 }
		return null;
	}
	@RequestMapping(value="/friends/",method=RequestMethod.GET)
	public List<People> getContacts(){
		
		return fs.getFriends((long) 3);
	}
	
	
//	public void requestFriend() {}
//	public void acceptFriend() {}

}
