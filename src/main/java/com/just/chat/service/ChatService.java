package com.just.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.just.chat.dao.ChatDao;
import com.just.chat.model.JCMessage;

@Service
public class ChatService {

	@Autowired
	ChatDao cd;
	
	public JCMessage saveMessage(JCMessage msg) {
		JCMessage t = cd.save(msg);
		if(t !=null) {
			System.out.println(t.getMsg_text());
			return t;
		}
		else {
			System.out.println(msg.getMsg_text());
		}
		return null;
	}
	
	public List<JCMessage> getMessagesRecent(Long id1,Long id2){
		
		
		
		return cd.getMessagesRecent(id1, id2);
		
	}
	public JCMessage getSavedMessage(Long id1,Long id2) {
		
		return null;
	}
	
	public boolean deleteMessage(Long messageId) {
		return false;
	}
}
