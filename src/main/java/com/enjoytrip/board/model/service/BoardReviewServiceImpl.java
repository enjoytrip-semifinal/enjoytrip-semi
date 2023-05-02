package com.enjoytrip.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enjoytrip.board.model.BoardReviewDto;
import com.enjoytrip.board.model.mapper.BoardReviewMapper;

@Service
public class BoardReviewServiceImpl implements BoardReviewService{

	private BoardReviewMapper boardReviewMapper;
	public BoardReviewServiceImpl(BoardReviewMapper boardReviewMapper) {
		super();
		this.boardReviewMapper = boardReviewMapper;
	}
	
	@Override
	public List<BoardReviewDto> listReview() {
		return boardReviewMapper.listReview();
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
