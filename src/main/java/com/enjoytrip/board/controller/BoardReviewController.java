package com.enjoytrip.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.enjoytrip.board.model.service.BoardReviewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController()
@Api(tags = {"리뷰 API"})
@RequestMapping("/board/review")
public class BoardReviewController {

	private BoardReviewService boardReviewService;
	public BoardReviewController(BoardReviewService boardReviewService) {
		super();
		this.boardReviewService = boardReviewService;
	}
	
	/* 리뷰 관련 */
	// 1. 리뷰 반환
	@ApiOperation(value = "페이징 처리된 리뷰 조회", notes = "페이징 처리된 리뷰 <b>목록</b>을 리턴합니다. <br>"
			+ "넘겨줘야하는 QueryString ..../list?pgno=[값]&boardid=[값]")
	@GetMapping("/list")
	public ResponseEntity<?> listReview(@RequestParam Map<String, String> map) {
		List<BoardReviewDto> list = boardReviewService.listReview(map);
		
		// 리뷰는 리뷰 리스트와 페이지 번호만 있으면 됨
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("reviewList", list);
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
	public ResponseEntity<?> writeReview(@RequestBody BoardReviewDto boardReview) {
		
		// 사용자 정보
		
		// ===========================================
		
		// 댓글을 쓰면 1번으로 이동한다.
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		int result = boardReviewService.writeReview(boardReview);
		if(result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 3. 리뷰  삭제
	@ApiOperation(value = "리뷰 삭제", notes = "리뷰를 삭제합니다.")
	@DeleteMapping("/delete/{boardReviewId}")
	public ResponseEntity<?> deleteReview(@PathVariable int boardReviewId) {
		int result = boardReviewService.deleteReview(boardReviewId);
		
		// 댓글을 쓰면 1번으로 이동한다.
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 4. 리뷰 수정
	@ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
	@PutMapping("/modify/{boardReviewId}")
	public ResponseEntity<?> modifyReview(@RequestBody BoardReviewDto boardReview) {
		int result = boardReviewService.modifyReview(boardReview);
		
		// 댓글을 쓰면 1번으로 이동한다.
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
