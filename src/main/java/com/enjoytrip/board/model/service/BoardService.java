package com.enjoytrip.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.util.PageNavigation;

public interface BoardService {
	// 게시글 리스트 가져오기
	List<BoardDto> listBoard(Map<String, String> map) throws SQLException ;
	
	// 게시글 하나 가져오기
	Map<String, Object> viewBoard(int boardId) throws Exception;
	
	// 글 쓰기
	int writeBoard(BoardDto board, String[] path) throws Exception;
	
	// 글 수정하기
	int modifyBoard(BoardDto board, String[] path) throws Exception;
	
	// 조회수 증가하기
	void updateHit(int boardId) throws Exception;
	
	// 삭제하기
	int deleteBoard(int boardId) throws Exception;
	
	// 전체 글 수 반환하기
	int getTotalAllBoardCount() throws Exception;
}
