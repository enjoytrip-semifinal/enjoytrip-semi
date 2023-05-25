package com.enjoytrip.notice.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.enjoytrip.notice.model.NoticeDto;
import com.enjoytrip.notice.model.NoticeFileInfoDto;
import com.enjoytrip.notice.model.service.NoticeService;
import com.enjoytrip.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = { "공지사항 관리" })
@CrossOrigin("*")
@RequestMapping("/notice")
public class NoticeController {

	private NoticeService noticeService;

	@Autowired
	public NoticeController(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}

	@ApiOperation(value = "공지 사항")
	@ApiResponses({ @ApiResponse(code = 200, message = "공지 사항 OK"), @ApiResponse(code = 500, message = "서버 에러") })
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
		try {
			List<NoticeDto> list = noticeService.listNotice(map);
			PageNavigation pageNavigation = noticeService.makePageNav(map);

			Map<String, Object> returnMap = new HashMap<>();
			returnMap.put("noticeList", list);
			returnMap.put("navigation", pageNavigation);

			returnMap.put("pageNum", map.get("pgno"));
			returnMap.put("key", map.get("key"));
			returnMap.put("word", map.get("word"));

			return new ResponseEntity<Map>(returnMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "공지 사항 글 하나 조회")
	@ApiResponses({ @ApiResponse(code = 200, message = "공지 사항 글 하나 조회 OK"), @ApiResponse(code = 500, message = "서버 에러") })
	@GetMapping("/list/{noticeid}")
	public ResponseEntity<?> getNotice(@PathVariable("noticeid") Integer noticeid) throws Exception {
		
		Map<String, Object> resultMap = noticeService.getNotice(noticeid);
		noticeService.updateNoticeHit(noticeid);
		
		if (resultMap.get("files") != null) {
			return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 가져오기 중 오류 발생", HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "공지 사항 글 쓰기")
	@ApiResponses({ @ApiResponse(code = 200, message = "공지 사항 글 쓰기 OK"), @ApiResponse(code = 500, message = "서버 에러") })
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody NoticeDto notice) throws Exception {

		int result = noticeService.write(notice);
		
		if(result > 0) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("게시글 등록 중 오류 발생", HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "공지 사항 글 삭제")
	@ApiResponses({ @ApiResponse(code = 200, message = "공지 사항 글 삭제 OK"), @ApiResponse(code = 500, message = "서버 에러") })
	@DeleteMapping("/delete/{noticeid}")
	public ResponseEntity<?> delete(@PathVariable("noticeid") Integer noticeid) throws Exception {
		try {
			return new ResponseEntity<Integer>(noticeService.delete(noticeid), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "공지 사항 글 수정")
	@ApiResponses({ @ApiResponse(code = 200, message = "공지 사항 글 수정 OK"), @ApiResponse(code = 500, message = "서버 에러") })
	@PutMapping(value = "modify")
	public ResponseEntity<?> modify(@RequestBody NoticeDto notice) throws Exception {
		try {
			return new ResponseEntity<Integer>(noticeService.modify(notice), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "총 공지사항 글 개수 반환")
	@GetMapping("/list/count")
	public ResponseEntity<?> getTotalAllBoardCount() throws Exception {
		try {
			int result = noticeService.getTotalAllBoardCount();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}

