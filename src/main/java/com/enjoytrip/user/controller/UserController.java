package com.enjoytrip.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.jwt.service.TokenInfo;
import com.enjoytrip.user.entity.UserDto;
import com.enjoytrip.user.model.service.UserService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/login")
	public String login() {
		log.info("GET : /user/login");
		return "/user/login";
	}
	
	// ## 로그인 관련 ## //
	@PostMapping("/login")
	public TokenInfo login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
		log.info("/user/login !!");
		String id = userLoginRequestDto.getId();
		String password = userLoginRequestDto.getPassword();
		log.info("id : {}, password : {}", id, password);
		TokenInfo tokenInfo = userService.login(id, password);
//		TokenInfo tokenInfo = userService.login("test", "1234");
		log.info("tokeninfo : {}", tokenInfo);
		return tokenInfo;
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "";
	}
	
	@GetMapping("/findid")
	public String findid() {
		return "";
	}
	
	@GetMapping("/findpw")
	public String findpw() {
		return "";
	}
	
	// ## 회원 가입 관련 ## //
	@GetMapping("/join")
	public String join() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		log.info(user.getUsername());
		return "user/join";
	}
	
	@PostMapping("/join")
	public String join(UserDto userDto) {
		return "";
	}
	// 2.2 join - check id - GET
//	int idCheck(String userId) throws Exception;
	
	// 2.3 join - email verification - GET
//	void emailVerification(UserDto userDto) throws Exception;
	
	@PostMapping("/test")
    public String test() {
		log.info("에엥");
    	return "success";
    }
	 
}
