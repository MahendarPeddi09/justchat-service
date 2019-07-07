package com.just.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.just.chat.model.LoginUser;
import com.just.chat.model.User;

@Repository
public interface LoginDao extends JpaRepository<User, String>{


}
