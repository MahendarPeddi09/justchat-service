package com.just.chat.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.just.chat.model.JCMessage;
import com.just.chat.model.People;

@Repository
public interface ChatDao extends JpaRepository<JCMessage, Long>{
	
	@Query(value="select * from chats where (from_id = ?1 and to_id = ?2) or (from_id = ?2 and to_id = ?1) order by msg_id desc limit 20",nativeQuery = true)
	List<JCMessage> getMessagesRecent(Long id1, Long id2);
	
	//select msg_id,from_id,msg_status,msg_text,max(timestamp) as timestamp,to_id from (select * from chats  where from_id=3) as t group by to_id;
		
		//@Query(value="select * from chats where timestamp in (select max(timestamp) from (select * from chats where from_id=?1 or to_id=?1 order by timestamp asc) as t group by  to_id)",nativeQuery=true)
	@Query(value="select * from chats where timestamp in (select max(timestamp) from (select * from chats where timestamp in (select max(timestamp) from (select * from chats where from_id=?1  order by timestamp asc) as t  group by to_id) union select msg_id,to_id as from_id,msg_status,msg_text,timestamp,from_id as to_id from chats where timestamp in (select max(timestamp) from (select * from chats where to_id=?1  order by timestamp asc) as t  group by from_id)) as d group by to_id)",nativeQuery=true)
	List<JCMessage> getActiveChats(Long id);
	
	@Query(value="delete from chats where msg_id=?1",nativeQuery=true)
	boolean deleteMessage(Long id);

}
