package com.enjoytrip.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.BoardReviewDto;

@Mapper
public interface BoardReviewMapper {
	List<BoardReviewDto> listReview();
	int writeReview(BoardReviewDto boardReview);
	int deleteReview(int boardReviewId);
	int modifyReview(BoardReviewDto boardReview);
}	
