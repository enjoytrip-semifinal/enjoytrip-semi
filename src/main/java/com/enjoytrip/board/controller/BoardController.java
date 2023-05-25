package com.enjoytrip.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.service.BoardService;
import com.enjoytrip.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = {"게시판 API"})
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardController {
	
	private BoardService boardService;
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	/* 게시판 글 관련 */
	// 1. 페이징 처리된 게시판 글 조회
	@ApiOperation(value = "페이징 처리된 게시판 글 조회", notes = "페이징 처리된 게시판의 <b>목록</b>을 리턴합니다. <br>"
			+ "map : pgno=[페이지 번호]&key=[검색종류 : (title, content, userId)]&word=[검색어] <br>"
			+ "ex : pgno=1&key=&word=")
	@GetMapping("/list")
	public ResponseEntity<?> listBoard(@RequestParam Map<String, String> map) throws Exception {
		List<BoardDto> list = boardService.listBoard(map);
		
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("boardList", list);
		returnMap.put("pgno", map.get("pgno"));
		returnMap.put("key", map.get("key"));
		returnMap.put("word", map.get("word"));
		
		System.out.println("pgno : " + returnMap.get("pgno"));
		
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<Map>(returnMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 2. 게시판 글 하나 조회
	@ApiOperation(value = "게시판 글 하나 조회", notes = "원하는 게시글 <b>하나</b>를 리턴합니다. <br>")
	@GetMapping("/list/{boardId}")
	public ResponseEntity<?> viewBoard(@PathVariable int boardId) throws Exception {
		
		// 게시글과 그 게시글에 대한 파일 정보 가져오기
		Map<String, Object> resultMap = boardService.viewBoard(boardId);
		
		// 조회수 하나 증가
		boardService.updateHit(boardId);
		
		if(resultMap.get("board") != null) {
			return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 3. 게시판 글 쓰기
	@ApiOperation(value = "게시판 글 쓰기", notes = "게시판의 글 하나를 작성합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> writeBoard(@RequestBody BoardDto board) throws Exception {
		// 사용자 정보 
		
		// ===========================================
		
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		pageMap.put("key", "");
		pageMap.put("word", "");
		
		int result = boardService.writeBoard(board);
		if(result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 4. 게시판 글 삭제
	@ApiOperation(value = "게시판 글 하나 삭제", notes = "게시판의 글 하나를 삭제합니다.")
	@DeleteMapping("/delete/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable int boardId) throws Exception {
		int result = boardService.deleteBoard(boardId);
		
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 5. 게시판 수정
	@ApiOperation(value = "게시판 글 하나 수정", notes = "게시판의 글 하나를 수정합니다.")
	@PutMapping("/modify/{boardId}")
	public ResponseEntity<?> modifyBoard(@RequestBody BoardDto board, String[] path) throws Exception {
		int result = boardService.modifyBoard(board, path);
		
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 6. Board 테이블에 있는 모든 게시글 수 반환 
	@ApiOperation(value = "Board 테이블에 있는 모든 게시글 수 반환", notes = "Board 테이블에 있는 모든 게시글 수 반환합니다.")
	@GetMapping("/list/count")
	public ResponseEntity<?> getTotalAllBoardCount() throws Exception {
		try {
			int result = boardService.getTotalAllBoardCount();
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
