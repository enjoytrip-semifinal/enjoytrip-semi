package com.enjoytrip.jwt.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.enjoytrip.user.repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
		
	@Autowired
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		final String token;
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		token = authHeader.substring(7);
		
		refreshTokenRepository.deleteByrefreshtoken(token);
	}
	
//	@Override
//	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//		// 리프레시 토큰을 제거하는 과정 
//		log.info("Logout Handler ...");
//		log.info("{}", SecurityContextHolder.getContext().getAuthentication());
//        if (authentication != null && authentication.getPrincipal() != null) {
//            UserDetails user = (UserDetails) authentication.getPrincipal();
//            String userId = user.getUsername();  // assuming getId() is a method in your UserDetails implementation
//            log.info("로그아웃을 진행할 ID : {}", userId);
//            
//            refreshTokenRepository.deleteByid(userId);
//        }
//        log.info("");
//	}
	
	
	
}
