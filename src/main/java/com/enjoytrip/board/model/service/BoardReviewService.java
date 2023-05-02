package com.enjoytrip.board.model.service;

import java.util.List;

import com.enjoytrip.board.model.BoardReviewDto;

public interface BoardReviewService {
	List<BoardReviewDto> listReview();
	int writeReview(BoardReviewDto boardReview);
	int deleteReview(int boardReviewId);
	int modifyReview(BoardReviewDto boardReview);
}
