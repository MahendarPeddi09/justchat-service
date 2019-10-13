package com.just.chat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.just.chat.model.People;

@Repository
public interface PeopleDao extends JpaRepository<People, Long>{

	
	@Query(value="SELECT user_name,userid FROM userids WHERE user_name LIKE ?1%",nativeQuery = true)
	List<People> getPeople(String name);
	
	@Query(value="SELECT user_name,userid FROM userids WHERE userid = ?1",nativeQuery = true)
	People getNewUser(Long id);
	
	@Query(value="SELECT user_name,userid FROM userids WHERE userid IN (SELECT friend_id FROM friends where owner_id=?1)",nativeQuery = true)
	List<People> getFriends(Long id);
	
	@Query(value="SELECT *  from userids WHERE user_name = ?1",nativeQuery = true)
	People getOwnerId(String name);
	
	
	
}
