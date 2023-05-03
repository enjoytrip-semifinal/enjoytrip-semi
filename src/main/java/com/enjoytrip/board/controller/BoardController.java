package com.enjoytrip.board.controller;

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

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.service.BoardService;
import com.enjoytrip.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	// 1. 페이징 처리된 게시판 글 조회
	@ApiOperation(value = "페이징 처리된 게시판 글 조회", notes = "페이징 처리된 게시판의 <b>목록</b>을 리턴합니다. "
			+ "											<br> URL: /board/list?pgno=[num]&key=[String]&word=[String]")
	@GetMapping("/list")
	public ResponseEntity<?> listBoard(@RequestParam Map<String, String> map) throws Exception {
		List<BoardDto> list = boardService.listBoard(map);
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("bordList", list);
		returnMap.put("navigation", pageNavigation);
		
		returnMap.put("pgno", map.get("pgno"));
		returnMap.put("key", map.get("key"));
		returnMap.put("word", map.get("word"));
		
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 2. 게시판 글 하나 조회
	@ApiOperation(value = "게시판 글 하나 조회", notes = "원하는 게시글 <b>하나</b>를 리턴합니다.")
	@GetMapping("/list/{boardId}")
	public ResponseEntity<?> viewBoard(@PathVariable int boardId) {
		BoardDto board = boardService.viewBoard(boardId);
		if(board != null) {
			return new ResponseEntity<BoardDto>(board, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 3. 게시판 글 쓰기
	@ApiOperation(value = "게시판 글 쓰기")
	@PostMapping("/write")
	public ResponseEntity<?> writeBoard(@RequestBody BoardDto board) {
		int result = boardService.writeBoard(board);
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 4. 게시판 글 삭제
	@ApiOperation(value = "게시판 글 하나 삭제")
	@DeleteMapping("/delete/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable int boardId) {
		int result = boardService.deleteBoard(boardId);
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 5. 게시판 수정
	@ApiOperation(value = "게시판 글 하나 수정")
	@PutMapping("/modify/{boardId}")
	public ResponseEntity<?> modifyBoard(@RequestBody BoardDto board) {
		int result = boardService.modifyBoard(board);
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
