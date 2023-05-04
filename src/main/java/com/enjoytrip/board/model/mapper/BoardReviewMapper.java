package com.enjoytrip.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.BoardReviewDto;

@Mapper
public interface BoardReviewMapper {
	List<BoardReviewDto> listReview(Map<String, Object> param);
	int writeReview(BoardReviewDto boardReview);
	int deleteReview(int boardReviewId);
	int modifyReview(BoardReviewDto boardReview);
}	
