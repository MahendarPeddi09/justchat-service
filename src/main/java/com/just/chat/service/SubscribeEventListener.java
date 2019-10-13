package com.just.chat.service;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent>{

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		System.out.println(headerAccessor.getSessionAttributes().get("sessionId").toString());
		
	}

}
