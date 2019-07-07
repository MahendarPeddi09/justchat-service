package com.just.chat.dao;

import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.just.chat.model.Friend;
import com.just.chat.model.People;

@Repository
public interface FriendDao extends JpaRepository<Friend, Long>{
	
	//@Query(value="SELECT user_name,userid FROM userids WHERE user_name LIKE 'n%'",nativeQuery = true)
	//List<People> getPeople(String name);
	//@Query(value="SELECT count(*) from friends where ((owner_id=?1 and friend_id=?2) or (owner_id=?2 and friend_id=?1))",nativeQuery=true)
	@Query(value="SELECT count(*) from friends where (owner_id=?1 and friend_id=?2)",nativeQuery=true)
	int checkFriend(Long oId,Long fId);
	
	@Query(value="DELETE FROM friends where (owner_id=?1and friend_id=?2)",nativeQuery=true)
	int deleteFriend(Long oId,Long fId);
}
