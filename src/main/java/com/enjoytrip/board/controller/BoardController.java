package com.enjoytrip.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

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

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.FileInfoDto;
import com.enjoytrip.board.model.service.BoardService;
import com.enjoytrip.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = {"게시판 API"})
@RequestMapping("/board")
public class BoardController {
	
	private BoardService boardService;
	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	// application.properties에서 얻어온 파일 경로 (application.properties에서 경로 수정 가능)
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.imgPath}")
	private String imgPath;
	
	/* 게시판 글 관련 */
	// 1. 페이징 처리된 게시판 글 조회
	@ApiOperation(value = "페이징 처리된 게시판 글 조회", notes = "페이징 처리된 게시판의 <b>목록</b>을 리턴합니다.")
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
	public ResponseEntity<?> viewBoard(@PathVariable int boardId, @RequestParam Map<String, String> map) throws Exception {
		BoardDto board = boardService.viewBoard(boardId);
		
		// 조회수 하나 증가
		boardService.updateHit(boardId);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("board", board);
		resultMap.put("pgno", map.get("pgno"));
		resultMap.put("key", map.get("key"));
		resultMap.put("word", map.get("word"));
		
		if(board != null) {
			return new ResponseEntity<Map>(resultMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	// 3. 게시판 글 쓰기
	@ApiOperation(value = "게시판 글 쓰기", notes = "게시판의 글 하나를 작성합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> writeBoard(@RequestBody BoardDto board, @RequestBody MultipartFile[] files) throws Exception {
		// 사용자 정보 
			
		// ===========================================
		
		// 파일 입력 부분
		if (!files[0].isEmpty()) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = uploadPath + File.separator + today;
			
			File folder = new File(saveFolder);
			
			// 폴더가 없으면 폴더를 만들어줌
			if (!folder.exists())
				folder.mkdirs();
			
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginalFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				
				fileInfos.add(fileInfoDto);
			}
			board.setFileInfos(fileInfos);
		} // file
		
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
		int result = boardService.deleteBoard(boardId, uploadPath);
		
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
	public ResponseEntity<?> modifyBoard(@RequestBody BoardDto board) {
		int result = boardService.modifyBoard(board);
		
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");
		
		if(result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
