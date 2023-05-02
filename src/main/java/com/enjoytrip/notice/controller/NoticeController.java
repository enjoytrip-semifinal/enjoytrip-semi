package com.enjoytrip.notice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enjoytrip.notice.model.NoticeDto;
import com.enjoytrip.notice.model.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	private final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	private NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		super();
		this.noticeService = noticeService;
	}

	@GetMapping("/list")
	public ResponseEntity<?> list() throws Exception {
		try {
			return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/list/{noticeid}")
	public ResponseEntity<?> getNotice(@PathVariable("noticeid") Integer noticeid) throws Exception {
		try {
//			noticeService.updateNoticeHit(noticeid);
			return new ResponseEntity<NoticeDto>(noticeService.getNotice(noticeid), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody NoticeDto notice) throws Exception {
		try {
			return new ResponseEntity<Integer>(noticeService.write(notice), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{noticeid}")
	public ResponseEntity<?> delete(@PathVariable("noticeid") Integer noticeid) throws Exception {
		try {
			return new ResponseEntity<Integer>(noticeService.delete(noticeid), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value="modify")
	public ResponseEntity<?> modify(@RequestBody NoticeDto notice) throws Exception {
		try {
			return new ResponseEntity<Integer>(noticeService.modify(notice), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
