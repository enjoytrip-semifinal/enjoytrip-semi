package com.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> listBoard(Map<String, Object> param) throws SQLException;
	int getTotalBoardCount(Map<String, Object> param) throws SQLException;
	BoardDto viewBoard(int boardId);
	int writeBoard(BoardDto board);
	int deleteBoard(int boardId);
	int modifyBoard(BoardDto board);
	void registerFile(BoardDto boardDto) throws Exception;
	void updateHit(int boardId);
	void deleteFile(int boardId) throws Exception;
	List<FileInfoDto> fileInfoList(int boardId) throws Exception;
}	
