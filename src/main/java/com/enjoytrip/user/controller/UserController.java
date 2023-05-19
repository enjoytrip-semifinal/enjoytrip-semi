package com.enjoytrip.user.controller;

import java.awt.color.ICC_ColorSpace;
import java.security.Principal;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.jwt.service.JwtTokenProvider;
import com.enjoytrip.jwt.service.SecurityUtil;
import com.enjoytrip.jwt.service.TokenInfo;
import com.enjoytrip.jwt.service.TokenRefreshException;
import com.enjoytrip.user.entity.UserDto;
import com.enjoytrip.user.entity.UserModifyDto;
import com.enjoytrip.user.model.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private final JavaMailSender mailSender;
	
	
	@ApiOperation(value = "로그인")
	@ApiResponses({@ApiResponse(code = 200, message = "로그인 성공 OK"), @ApiResponse(code = 500, message = "서버 에러")})
	@PostMapping("/login") 
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
	
	
	@ApiOperation(value = "리프레시 토큰을 통해 AccesToken을 갱신합니다.")
	@ApiResponses({@ApiResponse(code = 200, message = "리프레시 토큰 성공"), @ApiResponse(code = 403, message = "리프레시 토큰 에러"), @ApiResponse(code = 500, message = "서버 에러")})
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
			log.info("test: {}", role);
			String atoken = jwtTokenProvider.genereateAccessToken(id, role);
			
			return ResponseEntity.ok(new TokenInfo("Bearer", atoken, requestRefreshToken));
		} catch (Exception e) {
			new TokenRefreshException(requestRefreshToken, " -> 이 리프레시 토큰은 DB에 존재하지 않습니다!...");
		}
		
		log.info("리프레시 토큰 에러 !");
		return new ResponseEntity<String>("리프레시 토큰 과정에서 실패했습니다 ㅜㅜ", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) throws Exception{		
		String authHeader = request.getHeader("Authorization");		
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			String accessToken = authHeader.substring("Bearer ".length());
			userService.logoutByToken(accessToken);
			return new ResponseEntity<String>("로그아웃 성공", HttpStatus.OK);
		} 
		return new ResponseEntity<String>("로그아웃 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// 아이디 찾기 (이메일로 아이디 찾기)
	@ApiOperation(value = "이메일을 보내면 이메일에 ID를 전송")
	@ApiResponses({
			@ApiResponse(code = 200, message = "야호~~ 아이디 찾았다"), 
			@ApiResponse(code = 500, message = "에러")})
	@GetMapping("/findid")
	public ResponseEntity<?> findidByEmail(@RequestParam String email) throws Exception {
		log.info("아이디 찾기");
		if(userService.findId(email)) {
			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
			// 이메일 없을 경우 등
		}
	}
	
	// 비번 찾기
	@ApiOperation(value = "비번 찾기 : 구현 예정")
	@ApiResponses({
			@ApiResponse(code = 200, message = "야호~~ 비번 찾았다"), 
			@ApiResponse(code = 500, message = "에러")})
	@GetMapping("/findpw")
	public ResponseEntity<?> findpwByEmail(String emai) {
		// 구현필요
		try {			
			return new ResponseEntity<String>("", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage()+"냠 냐미 ㅋㅋ", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@ApiOperation(value = "회원 가입 ")
	@ApiResponses({
			@ApiResponse(code = 200, message = "회원 가입 성공"), 
			@ApiResponse(code = 500, message = "기존 가입 유저가 있거나  통신 오류")})
	@PostMapping("/join")
	public ResponseEntity<?> userJoin(@RequestBody UserDto userDto) {
		userService.join(userDto);
		return new ResponseEntity<String>("회원 가입 성공", HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "아이디 중복 여부를 체크합니다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "이 아이디는 사용가능합니다.^~^"), 
			@ApiResponse(code = 409, message = "기존 가입 유저가 있습니다.")})
	@GetMapping("/join")
	public ResponseEntity<?> idCheck(@RequestParam String id) throws Exception {
		// 아이디가 없으면 200, 있으면 409
		if(userService.checkForDuplicateId(id)) {
			return new ResponseEntity<String>("아이디가 존재합니다.", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<String>("중복 없어요~~", HttpStatus.OK);
		}		
	}
	
	@ApiOperation(value = "아이디 중복 여부 체크")
	@ApiResponses({
			@ApiResponse(code = 200, message = "이 아이디는 사용가능합니다.^~^"), 
			@ApiResponse(code = 409, message = "기존 가입 유저가 있습니다.")})
	@GetMapping("exist")
	public ResponseEntity<?> duplicateIdCheck(@RequestParam String id) {
		if(userService.checkForDuplicateId(id)) {
			return new ResponseEntity<String>("fail", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
	}
	
	// 2.3 join - email verification - GET
//	void emailVerification(UserDto userDto) throws Exception;
	
	@ApiOperation(value = "회원 탈퇴(로그인되어 있어야 함)")
	@ApiResponses({
			@ApiResponse(code = 200, message = "회원 탈퇴 성공"), 
			@ApiResponse(code = 500, message = "통신 오류")})
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
	
	@ApiOperation(value = "회원 수정 : 회원 정보 값 불러오기")
	@ApiResponses({
			@ApiResponse(code = 200, message = "회원 정보 불러오기 성공"), 
			@ApiResponse(code = 500, message = "통신 오류")})
	@GetMapping("/modify")
	public ResponseEntity<?> existingInfo() throws Exception {
		String id = "";
		try {
			id = SecurityUtil.getCurrentMemberId();
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>("유저 정보가 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// 수정할 유저 값 불러오기
		try {
			return new ResponseEntity<UserDto>(userService.userInfo(id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("유저 정보 가져오기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiOperation(value = "회원 수정 : 수정한 회원 정보 PUT")
	@ApiResponses({
			@ApiResponse(code = 200, message = "회원 정보 수정 성공"), 
			@ApiResponse(code = 500, message = "통신 오류")})
	@PutMapping(value="/modify")
	public ResponseEntity<?> userInfoModify(@RequestBody UserModifyDto userModifyDto) throws Exception {
		String loginedId = SecurityUtil.getCurrentMemberId();
		log.info("회원 정보를 할 ID :{}", loginedId);
		if(userService.updateUser(loginedId, userModifyDto) == 1) {
			log.info("회원 수정 정상 완료 : {}", loginedId);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			log.info("회원 수정 실패!! :  {}", loginedId);
			return new ResponseEntity<String>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

//	@ApiOperation(value = "회원 수정 : 수정한 회원 정보 PUT")
//	@ApiResponses({
//			@ApiResponse(code = 200, message = "회원 정보 수정 성공"), 
//			@ApiResponse(code = 500, message = "통신 오류")})
//	@PutMapping(value="/modify")
//	public ResponseEntity<?> userInfoModify(@RequestBody UserModifyDto userModifyDto) throws Exception {
//		if(userService.updateUser(userDto.getId(), userDto) == 1) {
//			log.info("회원 수정 정상 완료 : {}", userDto.getId());
//			return new ResponseEntity<String>("유저 정보 수정 성공", HttpStatus.OK);
//		} else {
//			log.info("회원 수정 실패!! :  {}", userDto.getId());
//			return new ResponseEntity<String>("유저 정보 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	
	
	@GetMapping(value="/join/{email}")
	public String emailVerification(@PathVariable String email) throws Exception {
		log.info("이메일 인증");
		return "";
	}
	
	
	@GetMapping("/reply/board")
	public String userReviewBoard() {
		String loginedId = SecurityUtil.getCurrentMemberId();
		
		return "";
	}
	
	@GetMapping("/reply/hotplace")
	public String userReviewHotplace() {
		String loginedId = SecurityUtil.getCurrentMemberId();
		return "";
	}
	
	// ## 로그인 관련 ## //
//	@ApiOperation(value = "구) 로그인 방식입니다. 사용하지 않는데 혹시 몰라 넣어놨습니다.")
//	@ApiResponses({
//			@ApiResponse(code = 200, message = "리프레시 토큰 성공"), 
//			@ApiResponse(code = 403, message = "리프레시 토큰 에러")
//		, 	@ApiResponse(code = 500, message = "서버 에러")})
//	@PostMapping("/oldlogin")
//	public TokenInfo login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
//		log.info("/user/login !!");
//		String id = userLoginRequestDto.getId();
//		String password = userLoginRequestDto.getPassword();
//		log.info("id : {}, password : {}", id, password);
//		TokenInfo tokenInfo = userService.login(id, password);
////		TokenInfo tokenInfo = userService.login("test", "1234");
//		
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.add("Authorization", "Bearer " + tokenInfo.getAccessToken());
//		
//		// session.setAttribute("userid", id);
//		
//		log.info("tokeninfo : {}", tokenInfo);
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		log.info("authentication : {}", authentication.toString());
//		log.info("음:{}", SecurityContextHolder.getContext());
////		User user = (User) authentication.getPrincipal();
////		log.info("authentication.getPrincipal().getUsername() : {}", user.toString());
//		return tokenInfo;
//	}
//	
	
	// @@@@ 아래부터는 테스트 용도입니다. 실제로 사용하지 않습니다. @@@@

	
	@GetMapping("/test")
	public String test() {
		log.info("SecurityUtil.getCurrentMemberId(); {}", SecurityUtil.getCurrentMemberId());
		SecurityUtil.getCurrentMemberId();
		return SecurityUtil.getCurrentMemberId();
	}
	
	
	@GetMapping("/admin/test")
	public String admin_test() {
		log.info("ADMIN_TEST : SecurityUtil.getCurrentMemberId(); {}", SecurityUtil.getCurrentMemberId());
		SecurityUtil.getCurrentMemberId();
		return SecurityUtil.getCurrentMemberId();
	}
	
	@GetMapping("/user/test")
	public String user_test() {
		log.info("USER_TEST : SecurityUtil.getCurrentMemberId(); {}", SecurityUtil.getCurrentMemberId());
		SecurityUtil.getCurrentMemberId();
		return SecurityUtil.getCurrentMemberId();
	}
	
	
}
