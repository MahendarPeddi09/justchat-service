package com.just.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.just.chat.model.JCMessage;
import com.just.chat.service.ChatService;

@CrossOrigin(origins= {"http://localhost:3000","http://localhost:7000"})
@RestController
public class ConversationController {
	
	@Autowired
	ChatService cs;
	
	@RequestMapping(value="/chats/{id1}/{id2}",method=RequestMethod.GET,produces="application/json")
	List<JCMessage> getRecentConversation(@PathVariable("id1") Long id1,@PathVariable("id2") Long id2){
		
		return cs.getMessagesRecent(id1, id2);	
	}
	
	@RequestMapping(value="/chats/{id1}",method=RequestMethod.DELETE,produces="application/json")
	public boolean deleteMessage() {
		
		
		return false;
	}
	
	@RequestMapping(value="/m",method=RequestMethod.POST,produces="application/json",consumes="application/json")
	public JCMessage saveMessage(@RequestBody JCMessage message) {
		if((message = cs.saveMessage(message))!=null) {
			return message;
		}
		
		return message;
	}
	
}
