package com.enjoytrip.user.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.jwt.service.SecurityUtil;
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
	public TokenInfo login(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpSession session) {
		log.info("/user/login !!");
		String id = userLoginRequestDto.getId();
		String password = userLoginRequestDto.getPassword();
		log.info("id : {}, password : {}", id, password);
		TokenInfo tokenInfo = userService.login(id, password);
//		TokenInfo tokenInfo = userService.login("test", "1234");
		
		
		// session.setAttribute("userid", id);
		
		log.info("tokeninfo : {}", tokenInfo);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("authentication : {}", authentication.toString());
		log.info("음:{}", SecurityContextHolder.getContext());
//		User user = (User) authentication.getPrincipal();
//		log.info("authentication.getPrincipal().getUsername() : {}", user.toString());
		return tokenInfo;
	}
	
//	@GetMapping("/logout")
	public String logout() {
		// LOGOUT HANDLE로 처리
		return "";
	}
	
	
	// 아이디 찾기 (이메일로 아이디 찾기)
	@GetMapping("/find-id")
	public String findidByEmail(String email) throws Exception {
		log.info("아이디 찾기");
		String this_id = userService.findId(email);
		return this_id;
	}
	
	// 비번 찾기
	@GetMapping("/find-pw")
	public String findpwByEmail(String emai) {
		// 구현필요
		return "";
	}
	
	// ## 회원 가입 관련 ## //
	@GetMapping("/join")
	public String join() {
		
		return "user/join";
	}
	
	@PostMapping("/join")
	public String userJoin(@RequestBody UserDto userDto) throws Exception {
		
		userService.join(userDto);
		
		return "redirect:/user/join";
	}
	// 2.2 join - check id - GET
//	int idCheck(String userId) throws Exception;
	
	// 2.3 join - email verification - GET
//	void emailVerification(UserDto userDto) throws Exception;
	
	@DeleteMapping(value="/join/{id}")
	public String userDeleteJoin(@PathVariable String id) throws Exception {
		if(userService.deleteUser(id) == 1) {
			log.info("회원 삭제 정상 완료 : {}", id);
		} else {
			log.info("회원 삭제 실패!! :  {}", id);
		}
		return "index.html";
	}
	
	@GetMapping("/modify")
	public ResponseEntity<?> existingInfo(Principal principal) throws Exception {
		log.info("principal.getName():{}", principal.getName());
		return null;
	}
	
	@PutMapping(value="/modify")
	public String userInfoModify(@RequestBody UserDto userDto) throws Exception {
		if(userService.updateUser(userDto.getId(), userDto) == 1) {
			log.info("회원 수정 정상 완료 : {}", userDto.getId());
		} else {
			log.info("회원 수정 실패!! :  {}", userDto.getId());
		}
		return "index.html";
	}
	
	@GetMapping(value="/join/{email}")
	public String emailVerification(@PathVariable String email) throws Exception {
		log.info("이메일 인증");
		return "";
	}
	
	
	@GetMapping("/review/board")
	public String userReviewBoard() {
		return "";
	}
	
	@GetMapping("/review/hotplace")
	public String userReviewHotplace() {
		return "";
	}
	
	@GetMapping("/test")
	public String test() {
		log.info("SecurityUtil.getCurrentMemberId(); {}", SecurityUtil.getCurrentMemberId());
		SecurityUtil.getCurrentMemberId();
		return SecurityUtil.getCurrentMemberId();
	}
	
	
}
