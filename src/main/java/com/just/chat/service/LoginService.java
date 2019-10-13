package com.just.chat.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.just.chat.dao.LoginDao;
import com.just.chat.dao.PeopleDao;
import com.just.chat.model.LoginUser;
import com.just.chat.model.People;
import com.just.chat.model.User;

@Service
public class LoginService {
	
	@Autowired
	LoginDao dao;
	
	@Autowired
	PeopleDao pd;
	
	public String addUser(User user) {
		
		try {
			if(!dao.existsById(user.getUserName())) {
				dao.save(user);
				
				return "success";
			}
		}catch (Exception e) {
			return "Fail";
		}
			
		return "Fail";	
		
	}
	
	public String verifyUser(LoginUser user) {
		
		try {
			User temp = dao.getOne(user.getUserName());
			if(temp.getUserName().equals(user.getUserName()) && temp.getUserPassword().equals(user.getUserPassword())) {
				return "success";
			}
		}
		catch (Exception e) {
			return "Fail";
		}
		
		
		return "Fail";
	}
	
	public People getOwner(String name) {
		
		return pd.getOwnerId(name);
	}
	

}
