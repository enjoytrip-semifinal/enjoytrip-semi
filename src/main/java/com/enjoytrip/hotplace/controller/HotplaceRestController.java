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

@RestController
@RequestMapping("/hotplace")
@Api(tags = { "핫플레이스 API" })
public class HotplaceRestController {

	@Autowired
	HotplaceService service;

	@Value("${file.path}")
	private String uploadPath;

	@Value("${file.imgPath}")
	private String uploadImgPath;

	// 게시판 전체 목록 조회
	@ApiOperation(value = "페이징 처리된 핫플레이스 글 조회", notes = "페이징 처리된 핫플레이스의 <b>목록</b>을 리턴합니다.")
	@GetMapping("/list")
	public ResponseEntity<?> listHotplace(@RequestParam Map<String, String> map) throws Exception {
		List<HotplaceDto> list = service.listHotplace(map);
		PageNavigation pageNavigation = service.makePageNavigation(map);

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("hotplaceList", list);
		returnMap.put("navigation", pageNavigation);

		returnMap.put("pgno", map.get("pgno"));
		returnMap.put("sido", map.get("key"));
		returnMap.put("gugun", map.get("word"));
		returnMap.put("type", map.get("type"));

		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<List<HotplaceDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	// 게시글 하나 조회
	@ApiOperation(value = "핫플레이스 글 하나 조회", notes = "원하는 핫플레이스 <b>하나</b>를 리턴합니다.")
	@GetMapping(value = "list/{num}")
	public ResponseEntity<?> viewHotplace(@PathVariable int num, @RequestParam Map<String, String> map)
			throws Exception {

		HotplaceDto hotplace = service.getHotplaceById(num);

		// 조회수 증가
		service.updateHit(num);

		Map<String, Object> returnMap = new HashMap<>();

		returnMap.put("hotplace", hotplace);
		returnMap.put("pgno", map.get("pgno"));
		returnMap.put("sido", map.get("key"));
		returnMap.put("gugun", map.get("word"));
		returnMap.put("type", map.get("type"));

		if (hotplace != null) {
			return new ResponseEntity<Map>(returnMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 불러오는 중 오류 발생", HttpStatus.NO_CONTENT);
		}

	}

	@ApiOperation(value = "게시판 글 쓰기", notes = "게시판의 글 하나를 작성합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> writeHotplace(@RequestBody HotplaceDto hotplace, String[] url)
			throws Exception {
		System.out.println("insert hoplace is called!!");
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();

		pageMap.put("pgno", "1");
		pageMap.put("sido", "0");
		pageMap.put("gugun", "0");
		pageMap.put("type", "0");

		int result = service.insertHotplace(hotplace,url);
		if (result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 등록 중 오류 발생", HttpStatus.NO_CONTENT);
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
	
	//5. 좋아요 수 증가
	@ApiOperation(value = "좋앙용~~~", notes = "너어무우 좋아용~~~")
	@DeleteMapping("/like/{num}")
	public ResponseEntity<?> likeHotplace(@PathVariable int num) throws Exception {
		int result = service.likeHotplace(num);

		if (result > 0) {
			HotplaceDto hotplace = service.getHotplaceById(num);
			return new ResponseEntity<HotplaceDto>(hotplace, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	
	//5. 좋아요 수 감소
		@ApiOperation(value = "싫어용!!", notes = "싫은데요~~~")
		@DeleteMapping("/hate/{num}")
		public ResponseEntity<?> hateHotplace(@PathVariable int num) throws Exception {
			int result = service.hateHotplace(num);

			if (result > 0) {
				HotplaceDto hotplace = service.getHotplaceById(num);
				return new ResponseEntity<HotplaceDto>(hotplace, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		}

}
