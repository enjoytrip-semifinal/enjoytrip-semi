package com.enjoytrip.board.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.board.model.BoardReviewDto;

public interface BoardReviewService {
	List<BoardReviewDto> listReview(Map<String, String> map);
	int writeReview(BoardReviewDto boardReview);
	int deleteReview(int boardReviewId);
	int modifyReview(BoardReviewDto boardReview);
}
