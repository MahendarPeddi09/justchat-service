package com.just.chat.service;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.just.chat.util.PrincipalImp;

public class CustomHandShakeHandler extends DefaultHandshakeHandler {

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		HttpSession session = servletRequest.getServletRequest().getSession();
		Principal principal = request.getPrincipal(); 
		System.out.println(session.getId());

        if (principal == null) {
            principal = new PrincipalImp();

            String uniqueName = String.valueOf(session.getAttribute("ownerId"));//UUID.randomUUID().toString();

            ((PrincipalImp) principal).setName(uniqueName);
        }

        return principal;

	}
	

}
