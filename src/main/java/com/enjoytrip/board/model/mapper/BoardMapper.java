package com.enjoytrip.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.BoardDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> listBoard();
	BoardDto viewBoard(int boardId);
	int writeBoard(BoardDto board);
	int deleteBoard(int boardId);
	int modifyBoard(BoardDto board);
}	
