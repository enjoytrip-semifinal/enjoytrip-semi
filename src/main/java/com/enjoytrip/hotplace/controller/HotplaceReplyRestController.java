package com.enjoytrip.hotplace.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.enjoytrip.hotplace.model.HotplaceReplyDto;
import com.enjoytrip.hotplace.model.service.HotplaceReplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController()
@Api(tags = { "핫플레이스 댓글 API" })
@RequestMapping("/hotplace/reply")
public class HotplaceReplyRestController {

	@Autowired
	private HotplaceReplyService service;

	/* 리뷰 관련 */
	// 1. 리뷰 반환
	@ApiOperation(value = "페이징 처리된 리뷰 조회", notes = "페이징 처리된 리뷰 <b>목록</b>을 리턴합니다. <br>"
			+ "넘겨줘야하는 QueryString ..../list?pgno=[값]&boardid=[값]")
	@GetMapping("/list")
	public ResponseEntity<?> listReply(@RequestParam Map<String, String> map) {
		List<HotplaceReplyDto> list = service.listReply(map);

		// 리뷰는 리뷰 리스트와 페이지 번호만 있으면 됨
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("replyList", list);
		returnMap.put("pgno", map.get("pgno"));

		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<Map>(returnMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("댓글 리스트 불러오기중 오류가 발생하였습니다.", HttpStatus.BAD_REQUEST);
		}
	}

	// 2. 리뷰 쓰기
	@ApiOperation(value = "리뷰 쓰기", notes = "리뷰를 작성하여 저장합니다.")
	@PostMapping("/write")
	public ResponseEntity<?> insertReply(@RequestBody HotplaceReplyDto replyDto) {

		int result = service.insertReply(replyDto);
		if (result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("댓글을 적는 과정에서 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
		}

	}

	// 3. 리뷰 삭제
	@ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다.")
	@DeleteMapping("/delete/{hotplaceReplyId}")
	public ResponseEntity<?> deleteReply(@PathVariable(name = "hotplaceReplyId") int replyId) throws SQLException {
		int result = service.deleteReply(replyId);

		if (result > 0) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("댓글을 삭제하는 과정에서 오류가 발생", HttpStatus.BAD_REQUEST);
		}
	}

	// 4. 리뷰 수정
	@ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
	@PutMapping("/update/{hotplaceReplyId}")
	public ResponseEntity<?> updateReply(@RequestBody HotplaceReplyDto replyDto) {
		int result = service.updateReply(replyDto);
		if (result > 0) {
			return new ResponseEntity<String>("핫플레이스 댓글 수정 성공!!", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("핫플레이스 댓글 수정 실패", HttpStatus.BAD_REQUEST);
		}
	}

	// 5. 해당 게시글에 대한 총 리뷰 수 반환하기
	@ApiOperation(value = "해당 게시글에 대한 총 리뷰 수 반환하기", notes = "해당 게시글에 대한 총 리뷰 수 반환합니다.")
	@GetMapping("/list/{hotplaceId}")
	public ResponseEntity<?> getTotalAllBoardReviewCount(@PathVariable int hotplaceId) {
		try {
			int result = service.getTotalCount(hotplaceId);
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

}
