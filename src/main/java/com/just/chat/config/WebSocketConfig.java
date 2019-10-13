package com.just.chat.config;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.just.chat.service.CustomHandShakeHandler;
import com.just.chat.service.CustomHandShakeInterceptor;
import com.just.chat.service.HttpHandshakeInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSocketMessageBroker 
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		registry.addEndpoint("/chat").withSockJS();//.setInterceptors(new CustomHandShakeInterceptor());
		registry.addEndpoint("/chat").setAllowedOrigins("*").setHandshakeHandler(new CustomHandShakeHandler());//addInterceptors(new HttpHandshakeInterceptor());
	}
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// TODO Auto-generated method stub
		registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue").setTaskScheduler(new DefaultManagedTaskScheduler()).setHeartbeatValue(new long[]{0,250000});
        registry.setUserDestinationPrefix("/user");
        //registry.setUserDestinationPrefix("/secured/room");
        
        
	}
	
	
	
}