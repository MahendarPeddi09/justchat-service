package com.just.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.just.chat.dao.FriendDao;
import com.just.chat.dao.PeopleDao;
import com.just.chat.model.Friend;
import com.just.chat.model.People;

@Service
public class FriendService {
	
	@Autowired
	FriendDao fd;
	
	@Autowired
	PeopleDao pd;

	public String addFriend(Long id) {
		Friend f = new Friend();
		f.setFriendId(id);
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
	
	public List<People> getFriends(Long id){

		return pd.getFriends(id);
	}
	
	
}
