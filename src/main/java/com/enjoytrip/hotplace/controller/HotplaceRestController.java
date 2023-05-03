package com.enjoytrip.hotplace.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.hotplace.model.FileInfoDto;
import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.hotplace.model.service.HotplaceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/hotplace")
@Api(tags = "핫플레이스")
public class HotplaceRestController {
	
	@Autowired
	HotplaceService service;
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.imgPath}")
	private String uploadImgPath;
	
	@ApiOperation(value = "전체 게시판 출력", notes = "게시판의 <b>전체 목록</b>을 리턴합니다.")
	@ApiResponses({@ApiResponse(code = 200, message = "전체 게시판 OK"), 
	@ApiResponse(code = 500, message = "서버 에러")})
	@PostMapping(value = "/write")
	public ResponseEntity<?> write(@RequestBody HotplaceDto hotplace) throws Exception{
		
		try {
			
			hotplace.setRegDate(LocalDate.now().toString());
			hotplace.setHit(0);
			 return new ResponseEntity<Integer>(service.writeHotplace(hotplace), HttpStatus.OK);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	         return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
	      }
		
		
	}
	
	@GetMapping(value="list")
	public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
	      try {
	    	  System.out.println(map.get("sido"));
	         //return new ResponseEntity<List<HotplaceDto>>(service.listHotplace(map), HttpStatus.OK);
	         return new ResponseEntity<List<HotplaceDto>>(service.listHotplace(map), HttpStatus.OK);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	         return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	@GetMapping(value="list/{num}")
	public ResponseEntity<?> listOne(@PathVariable("num")Integer num) throws Exception {
		try {
			service.updateHit(num);
			return new ResponseEntity<HotplaceDto>(service.getHotplace(num), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value="list/{num}")
	public ResponseEntity<?> listOneView(@PathVariable("num")Integer num, @RequestBody HotplaceDto hotplaceDto) throws Exception {
		try {
			HotplaceDto hotplace = service.getHotplace(num);
			hotplace.setTitle(hotplaceDto.getTitle());
			hotplace.setContent(hotplaceDto.getContent());
			
			
			
			return new ResponseEntity<Integer>(service.modifyHotplace(hotplace), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value="list/{num}")
	public ResponseEntity<?> delete(@PathVariable("num")Integer num) throws Exception {
		try {
			return new ResponseEntity<Integer>(service.deleteHotplace(num,uploadPath), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
