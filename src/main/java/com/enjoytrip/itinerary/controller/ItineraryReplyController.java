package com.enjoytrip.itinerary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.enjoytrip.board.model.BoardReviewDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.itinerary.model.service.ItineraryReplyService;

import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/itinerary/reply")
public class ItineraryReplyController {
	
	private ItineraryReplyService itineraryReplyService;
	public ItineraryReplyController(ItineraryReplyService itineraryReplyService) {
		super();
		this.itineraryReplyService = itineraryReplyService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> listReply(@RequestParam Map<String, String> map){
		
		List<ItineraryReplyDto> list = itineraryReplyService.listReply(map);
		
		// 리뷰는 리뷰 리스트와 페이지 번호만 있으면 됨
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("replyList", list);
		returnMap.put("pgno", map.get("pgno"));
		
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<Map>(returnMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 2. 리뷰 쓰기
	@ApiOperation(value = "리뷰 쓰기", notes = "리뷰를 작성하여 저장합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> writeReply(@RequestBody ItineraryReplyDto itineraryReply) {
		
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		int result = itineraryReplyService.writeReply(itineraryReply);
		if(result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 3. 리뷰  삭제
	@ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제합니다.")
	@DeleteMapping("/delete/{itineraryReplyId}")
	public ResponseEntity<?> deleteReply(@PathVariable int itineraryReplyId) {
		int result = itineraryReplyService.deleteReply(itineraryReplyId);
		
		// 댓글을 쓰면 1번으로 이동한다.
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 4. 리뷰 수정
	@ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
	@PutMapping("/modify/{itineraryReplyId}")
	public ResponseEntity<?> modifyReply(@RequestBody ItineraryReplyDto itineraryReply) {
		int result = itineraryReplyService.modifyReply(itineraryReply);
		
		// 댓글을 쓰면 1번으로 이동한다.
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
