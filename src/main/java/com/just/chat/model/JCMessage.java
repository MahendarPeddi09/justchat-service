package com.just.chat.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chats")
public class JCMessage {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long msg_id;
	private Long from_id;
	private Long to_id;
	private String msg_text;
	private int msg_status = 0;
	
	private Long timestamp = new Date().getTime();
	
	
	public Long getMsg_id() {
		return msg_id;
	}
//	public void setMsg_id(Long msg_id) {
//		this.msg_id = msg_id;
//	}
	public Long getFrom_id() {
		return from_id;
	}
	public void setFrom_id(Long from_id) {
		this.from_id = from_id;
	}
	public Long getTo_id() {
		return to_id;
	}
	public void setTo_id(Long to_id) {
		this.to_id = to_id;
	}
	public String getMsg_text() {
		return msg_text;
	}
	public void setMsg_text(String msg_text) {
		this.msg_text = msg_text;
	}
	public int getMsg_status() {
		return msg_status;
	}
//	public void setMsg_status(int msg_status) {
//		this.msg_status = msg_status;
//	}
	public Long getTimestamp() {
		return timestamp;
	}
//	public void setTimestamp(Long timestamp) {
//		this.timestamp = timestamp;
//	}
	
	

}
