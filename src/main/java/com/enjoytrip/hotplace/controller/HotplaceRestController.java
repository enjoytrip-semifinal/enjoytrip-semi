package com.enjoytrip.hotplace.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.hotplace.model.HotplaceFileInfoDto;
import com.enjoytrip.hotplace.model.service.HotplaceReplyService;
import com.enjoytrip.hotplace.model.service.HotplaceService;
import com.enjoytrip.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/hotplace")
@Api(tags = { "핫플레이스 API" })
public class HotplaceRestController {

	@Autowired
	HotplaceService service;
	
//	//조회수 높은 순으로 3개 뽑아오기
//	@GetMapping("/top")
//	public ResponseEntity<?> top3Hotplace() throws Exception{
//		
//	}
	

	// 게시판 전체 목록 조회
	@GetMapping("/list")
	public ResponseEntity<?> listHotplace(@RequestParam Map<String, String> map) throws Exception {

		List<HotplaceDto> list = service.listHotplace(map);


		PageNavigation pageNavigation = service.makePageNavigation(map);

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("hotplaceList", list);
		returnMap.put("navigation", pageNavigation);

		//넣어줘야할 값 pgno, writer, address, type, season

		returnMap.put("pgno", map.get("pgno")==null?"1":map.get("pgno"));
		returnMap.put("key", map.get("key")==null?"":map.get("key"));	//key는 검색 조건을 의미한다.
		returnMap.put("word", map.get("word")==null?"":map.get("word"));
		returnMap.put("type", map.get("type")==null?"0":map.get("type"));
		returnMap.put("season", map.get("season")==null?"0":map.get("season"));

		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<Map>(returnMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	// 게시글 하나 조회
	@ApiOperation(value = "핫플레이스 글 하나 조회", notes = "원하는 핫플레이스 <b>하나</b>를 리턴합니다.")
	@GetMapping(value = "/list/{num}")
	public ResponseEntity<?> viewHotplace(@PathVariable int num)
			throws Exception {
		
		HotplaceDto hotplace = service.getHotplaceById(num);

		// 조회수 증가
		service.updateHit(num);

		if (hotplace != null) {
			return new ResponseEntity<HotplaceDto>(hotplace, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 불러오는 중 오류 발생", HttpStatus.NO_CONTENT);
		}

	}

	// 게시글 하나 조회
	@ApiOperation(value = "핫플레이스 글 수정", notes = "원하는 핫플레이스 <b>하나</b>를 리턴합니다.")
	@PutMapping("/update")
	public ResponseEntity<?> updateHotplace(@RequestBody HotplaceDto hotplace)
			throws Exception {

		Map<String, String> pageMap = new HashMap<String, String>();

		pageMap.put("pgno","1");
		pageMap.put("key","");	//key는 검색 조건을 의미한다.
		pageMap.put("word", "");
		pageMap.put("type", "0");
		pageMap.put("season", "0");
		
		int result = service.updateHotplace(hotplace);
		if (result > 0) {
			return new ResponseEntity<Integer>(service.updateHotplace(hotplace), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 수정 중 오류 발생", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "게시판 글 쓰기", notes = "게시판의 글 하나를 작성합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> writeHotplace(@RequestBody HotplaceDto hotplace) throws Exception {
		System.out.println("insert hoplace is called!!");

//		hotplace.setUserId();
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();

		pageMap.put("pgno", "1");
		pageMap.put("type", "0");
		pageMap.put("season", "0");
		
		int result = service.insertHotplace(hotplace);
		if (result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 등록 중 오류 발생", HttpStatus.BAD_REQUEST);
		}
	}

	// 4. 게시판 글 삭제
	@ApiOperation(value = "게시판 글 하나 삭제", notes = "게시판의 글 하나를 삭제합니다.")
	@DeleteMapping("/delete/{num}")
	public ResponseEntity<?> deleteHotplace(@PathVariable int num) throws Exception {
		int result = service.deleteHotplace(num);

		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");

		if (result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	// 5. 좋아요 수 증가
	@ApiOperation(value = "좋앙용~~~", notes = "너어무우 좋아용~~~")
	@GetMapping("/like/{num}")
	public ResponseEntity<?> likeHotplace(@PathVariable int num) throws Exception {
		int result = service.likeHotplace(num);

		if (result > 0) {
			HotplaceDto hotplace = service.getHotplaceById(num);
			return new ResponseEntity<HotplaceDto>(hotplace, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	// 5. 좋아요 수 감소
	@ApiOperation(value = "싫어용!!", notes = "싫은데요~~~")
	@GetMapping("/hate/{num}")
	public ResponseEntity<?> hateHotplace(@PathVariable int num) throws Exception {
		int result = service.hateHotplace(num);

		System.out.println(num);

		if (result > 0) {
			//HotplaceDto hotplace = service.getHotplaceById(num);
			HotplaceDto hotplace = service.getHotplaceById(num);
			return new ResponseEntity<HotplaceDto>(hotplace, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}
