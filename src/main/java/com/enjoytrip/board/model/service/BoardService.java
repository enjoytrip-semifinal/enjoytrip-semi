package com.enjoytrip.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.util.PageNavigation;

public interface BoardService {
	List<BoardDto> listBoard(Map<String, String> map) throws SQLException ;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto viewBoard(int boardId);
	int writeBoard(BoardDto board) throws Exception;
	int modifyBoard(BoardDto board);
	void updateHit(int boardId) throws Exception;
	int deleteBoard(int boardId, String uploadPath) throws Exception;
}
