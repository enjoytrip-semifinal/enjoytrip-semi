package com.enjoytrip.user.controller;

import java.security.Principal;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.jwt.service.JwtTokenProvider;
import com.enjoytrip.jwt.service.SecurityUtil;
import com.enjoytrip.jwt.service.TokenInfo;
import com.enjoytrip.jwt.service.TokenRefreshException;
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
	private final JwtTokenProvider jwtTokenProvider;
	
	@GetMapping("/login")
	public ResponseEntity<?> login() {
		// 로그인 페이지 이동
		log.info("로그인 페이지 이동");
		try {
			return new ResponseEntity<String>("로그인 페이지 이동에 성공!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("로그인 페이지 이동에 실패", HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	// 230506 : 로그인 고침
	@PostMapping("/newlogin") 
	public ResponseEntity<TokenInfo> authorize(@RequestBody UserLoginRequestDto userLoginRequestDto){
		log.info("로그인 요청 중");
		String id = userLoginRequestDto.getId();
		String password = userLoginRequestDto.getPassword();
		TokenInfo tokenInfo = userService.login(id, password);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + tokenInfo.getAccessToken());
		
		log.info("tokeninfo : {}", tokenInfo);
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		log.info("authentication : {}", authentication.toString());
		
		return new ResponseEntity<TokenInfo>(new TokenInfo(tokenInfo.getGrantType(), tokenInfo.getAccessToken(), tokenInfo.getRefreshToken()), httpHeaders, HttpStatus.OK);
	}
	
	// 230506
	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDto requestDto) {
		String requestRefreshToken = requestDto.getRefreshToken();
		log.info("현재 리프레시 토큰 : {}", requestRefreshToken);
		
		try {
			userService.validateRefreshToken(requestRefreshToken);
			String id = userService.findRefrestokenByrefreshtoken(requestRefreshToken).getId();
			log.info("id : {}", id);
			// String rtoken = jwtTokenProvider.generateRefreshToken(id);
			String role = "ROLE_" + userService.findRolesById(id);
			String atoken = jwtTokenProvider.genereateAccessToken(id, role);
			
			return ResponseEntity.ok(new TokenInfo("Bearer", atoken, requestRefreshToken));
		} catch (Exception e) {
			new TokenRefreshException(requestRefreshToken, " -> 이 리프레시 토큰은 DB에 존재하지 않습니다!...");
		}
		
		log.info("리프레시 토큰 에러 !");
		return new ResponseEntity<String>("모종의 이유로 리프레시 토큰 과정에서 실패했습니다 ㅜㅜ", HttpStatus.INTERNAL_SERVER_ERROR);

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
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + tokenInfo.getAccessToken());
		
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
	public ResponseEntity<?> join() {
		try {
			return new ResponseEntity<String>("회원 가입 페이지 이동 성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("회원 가입 페이지 이동 실패. ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> userJoin(@RequestBody UserDto userDto) throws Exception {
		
		try {
			userService.join(userDto);
			return new ResponseEntity<String>("회원 가입 성공", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("기존 가입유저가 있거나 통신 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// 2.2 join - check id - GET
//	int idCheck(String userId) throws Exception;
	
	// 2.3 join - email verification - GET
//	void emailVerification(UserDto userDto) throws Exception;
	
	@DeleteMapping(value="/join/{id}")
	public ResponseEntity<?> userDeleteJoin(@PathVariable String id) throws Exception {
		String loginedId = SecurityUtil.getCurrentMemberId();
		// 로그인된 ID 와 내가 delete 주소로 온 아이디가 같을 경우에만 삭제 가능
		
		if(loginedId.equals(id) && userService.deleteUser(id) == 1) {
			log.info("회원 탈퇴 정상 완료 : {}", id);
			return ResponseEntity.ok("회원 탈퇴 정상 완료");
		} else {
			log.info("회원 탈퇴 실패!! :  {}", id);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GetMapping("/modify")
	public ResponseEntity<?> existingInfo(Principal principal) throws Exception {
		String id = SecurityUtil.getCurrentMemberId();
		// 수정할 유저 값 불러오기
		try {
			return new ResponseEntity<UserDto>(userService.userInfo(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("유저 정보 가져오기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/modify")
	public int userInfoModify(@RequestBody UserDto userDto) throws Exception {
		if(userService.updateUser(userDto.getId(), userDto) == 1) {
			log.info("회원 수정 정상 완료 : {}", userDto.getId());
			return 1;
		} else {
			log.info("회원 수정 실패!! :  {}", userDto.getId());
			return 0;
		}
	}
	
	@GetMapping(value="/join/{email}")
	public String emailVerification(@PathVariable String email) throws Exception {
		log.info("이메일 인증");
		return "";
	}
	
	
	@GetMapping("/review/board")
	public String userReviewBoard() {
		String loginedId = SecurityUtil.getCurrentMemberId();
		return "";
	}
	
	@GetMapping("/review/hotplace")
	public String userReviewHotplace() {
		String loginedId = SecurityUtil.getCurrentMemberId();
		return "";
	}
	
	
	
	// 아래부터는 테스트 용도입니다. 실제로 사용하지 않습니다.
	
	@GetMapping("/test")
	public String test() {
		log.info("SecurityUtil.getCurrentMemberId(); {}", SecurityUtil.getCurrentMemberId());
		SecurityUtil.getCurrentMemberId();
		return SecurityUtil.getCurrentMemberId();
	}
	
	
	@GetMapping("/auth/admin/test")
	public String admin_test() {
		return "i am an admin.";
	}
	
	@GetMapping("/auth/user/test")
	public String user_test() {
		return "i am an user.";
	}
	
	
}
