package com.enjoytrip.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = {"게시판 관리"})
@RequestMapping("/board")
public class BoardController {
	
	private BoardService boardService;
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	/* 게시판 글 관련 */
	// 1. 전체 게시판 글 조회
	
	@ApiOperation(value = "전체 게시판 출력", notes = "게시판의 <b>전체 목록</b>을 리턴합니다.")
	@ApiResponses({@ApiResponse(code = 200, message = "전체 게시판 OK"), 
	@ApiResponse(code = 500, message = "서버 에러")})
	@GetMapping("/list")
	public List<BoardDto> listBoard() {
		List<BoardDto> list = boardService.listBoard();
		return list;
	}
	
	// 2. 게시판 글 하나 조회
	@GetMapping("/list/{boardId}")
	public BoardDto viewBoard(@PathVariable int boardId) {
		BoardDto board = boardService.viewBoard(boardId);
		return board;
	}
	
	// 3. 게시판 글 쓰기
	@PostMapping("/write")
	public int writeBoard(@RequestBody BoardDto board) {
		int result = boardService.writeBoard(board);
		return result;
	}
	
	// 4. 게시판 글 삭제
	@DeleteMapping("/delete/{boardId}")
	public int deleteBoard(@PathVariable int boardId) {
		int result = boardService.deleteBoard(boardId);
		return result;
	}
	
	// 5. 게시판 수정
	@PutMapping("/modify/{boardId}")
	public int modifyBoard(@RequestBody BoardDto board) {
		int result = boardService.modifyBoard(board);
		return result;
	}
}
