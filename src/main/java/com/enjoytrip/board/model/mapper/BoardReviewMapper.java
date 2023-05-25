package com.enjoytrip.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.BoardReviewDto;

@Mapper
public interface BoardReviewMapper {
	// 전체 댓글 수 가져오기
	List<BoardReviewDto> listReview(Map<String, Object> param);
	
	// 댓글 쓰기
	int writeReview(BoardReviewDto boardReview);
	
	// 댓글 삭제
	int deleteReview(int boardReviewId);
	
	// 댓글 수정
	int modifyReview(BoardReviewDto boardReview);
	
	// 댓글 전체 삭제
	int deleteAllReview(int boardId);
	
	// 해당 게시글에 대한 모든 댓글 수 반환하기
	int getTotalAllBoardReviewCount(int boardId);
}	
