package com.enjoytrip.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.BoardReviewDto;
import com.enjoytrip.board.model.service.BoardReviewService;
import com.enjoytrip.board.model.service.BoardService;

@RestController()
@RequestMapping("/board/review")
public class BoardReviewController {

	private BoardReviewService boardReviewService;
	public BoardReviewController(BoardReviewService boardReviewService) {
		super();
		this.boardReviewService = boardReviewService;
	}
	
	/* 게시판 리뷰 관련 */
	// 1. 리뷰 반환
	@GetMapping("/list")
	public List<BoardReviewDto> listReview() {
		List<BoardReviewDto> list = boardReviewService.listReview();
		return list;
	}
	
	// 2. 리뷰 쓰기
	@PostMapping("/write")
	public int writeReview(@RequestBody BoardReviewDto boardReview) {
		int result = boardReviewService.writeReview(boardReview);
		return result;
	}
	
	// 3. 리뷰  삭제
	@DeleteMapping("/delete/{boardId}")
	public int deleteReview(@PathVariable int boardReviewId) {
		int result = boardReviewService.deleteReview(boardReviewId);
		return result;
	}
	
	// 4. 리뷰 수정
	@PutMapping("/modify/{boardId}")
	public int modifyReview(@RequestBody BoardReviewDto boardReview) {
		int result = boardReviewService.modifyReview(boardReview);
		return result;
	}
}
