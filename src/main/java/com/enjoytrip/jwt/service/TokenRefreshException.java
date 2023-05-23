package com.enjoytrip.jwt.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TokenRefreshException(String token, String msg) {
		super(String.format("실패 [%s] : %s", token, msg));
	}
}
