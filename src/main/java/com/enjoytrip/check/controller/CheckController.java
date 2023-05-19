package com.enjoytrip.check.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.jwt.service.JwtTokenProvider;
import com.enjoytrip.user.controller.UserController;
import com.enjoytrip.user.model.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CheckController {
	
	@Autowired
	private final UserService userService;
	
	@ApiOperation(value = "아이디 중복 여부 체크")
	@ApiResponses({
			@ApiResponse(code = 200, message = "이 아이디는 사용가능합니다.^~^"), 
			@ApiResponse(code = 409, message = "기존 가입 유저가 있습니다.")})
	@RequestMapping(value = "/exist", method = RequestMethod.GET)
	public ResponseEntity<?> duplicateIdCheck(@RequestParam String id) {
		log.info("id : {} !!!!!" , id);
		if(userService.checkForDuplicateId(id)) {
			return new ResponseEntity<String>("fail", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
	}
	
}
