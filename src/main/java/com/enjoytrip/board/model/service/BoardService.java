package com.enjoytrip.board.model.service;

import java.util.List;

import com.enjoytrip.board.model.BoardDto;

public interface BoardService {
	List<BoardDto> listBoard();
	BoardDto viewBoard(int boardId);
	int writeBoard(BoardDto board);
	int deleteBoard(int boardId);
	int modifyBoard(BoardDto board);
}
