package com.just.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.just.chat.dao.ChatDao;
import com.just.chat.dao.FriendDao;
import com.just.chat.dao.PeopleDao;
import com.just.chat.model.Friend;
import com.just.chat.model.JCMessage;
import com.just.chat.model.People;

@Service
public class FriendService {
	
	@Autowired
	FriendDao fd;
	
	@Autowired
	PeopleDao pd;
	
	@Autowired
	ChatDao cd;

	public String addFriend(Long id,Long id1) {
		Friend f = new Friend();
		f.setOwnerId(id);
		f.setFriendId(id1);
		if(fd.checkFriend(f.getOwnerId(),f.getFriendId()) > 0) {
			return "Already Friends";
		}
		else {
			fd.save(f);
			return "Added to Friends";
		}
		
	}
	
	public String deleteFriend(Long id) {
		
		return null;
	}
	public List<People> getPeople(String name){
		
		return pd.getPeople(name);
	}
	
	
	public People getNewUser(Long id) {
		return pd.getNewUser(id);
	}
	
	public List<People> getFriends(Long id){

		return pd.getFriends(id);
	}
	
	public List<JCMessage> getActiveContacts(Long id){

		return cd.getActiveChats(id);
	}
	
	
}
