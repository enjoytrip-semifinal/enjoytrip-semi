package com.enjoytrip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.enjoytrip.board.model.BoardReviewDto;
import com.enjoytrip.board.model.mapper.BoardReviewMapper;
import com.enjoytrip.util.SizeConstant;

@Service
public class BoardReviewServiceImpl implements BoardReviewService{

	private BoardReviewMapper boardReviewMapper;
	public BoardReviewServiceImpl(BoardReviewMapper boardReviewMapper) {
		super();
		this.boardReviewMapper = boardReviewMapper;
	}
	
	// 리뷰 반환
	@Override
	public List<BoardReviewDto> listReview(Map<String, String> map) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("boardid", map.get("boardid"));
		
		return boardReviewMapper.listReview(param);
	}

	@Override
	public int writeReview(BoardReviewDto boardReview) {
		return boardReviewMapper.writeReview(boardReview);
	}

	@Override
	public int deleteReview(int boardReviewId) {
		return boardReviewMapper.deleteReview(boardReviewId);
	}

	@Override
	public int modifyReview(BoardReviewDto boardReview) {
		return boardReviewMapper.modifyReview(boardReview);
	}
}
