package com.just.chat.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.just.chat.model.JCMessage;
import com.just.chat.service.ChatService;

@Controller
public class ChatController {
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Autowired
	ChatService cs;
	
	@EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }
	
	
	@MessageMapping("/senduser/{id}")
    @SendToUser("/queue/reply")
    public void processMessageFromClient(Principal principal,@DestinationVariable("id") String id1,@Payload JCMessage message,SimpMessageHeaderAccessor headerAccessor,MessageHeaders mHeaders) throws Exception {
		//String name= message.getTo_id().toString();
		String userId = "userId";
		System.out.println(principal.getName());//+"------"+mHeaders.toString());
		
		System.out.println(message.getTo_id());
		JCMessage t = cs.saveMessage(message);
		if( t !=null) {
			
			messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", t);
			messagingTemplate.convertAndSendToUser(id1, "/queue/reply", t);
		}
		else {
			
			messagingTemplate.convertAndSendToUser(id1, "/queue/reply", message);
		}
		
		
		//System.out.println(messagingTemplate.);
		//messagingTemplate.convertAndSend("/queue/reply", message);
    //return message;
    }
	@EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null) {
//            WebSocketChatMessage chatMessage = new WebSocketChatMessage();
//            chatMessage.setType("Leave");
//            chatMessage.setSender(username);
//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
        System.out.println("Web socket connections ended");
    }
	
}
