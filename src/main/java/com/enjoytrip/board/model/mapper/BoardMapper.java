package com.enjoytrip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {
	// 게시판 글 전체 가져오기
	List<BoardDto> listBoard(Map<String, Object> param) throws SQLException;
	
	// 총 게시글 수 반환
	int getTotalBoardCount(Map<String, Object> param) throws SQLException;
	
	// 게시글 하나 반환
	BoardDto viewBoard(int boardId);
	
	// 글 쓰기
	int writeBoard(BoardDto board);
	
	// 파일 정보 입력하기
	int registerFileInfo(List<FileInfoDto> fileInfoList);
	
	// 글 삭제
	int deleteBoard(int boardId);
	
	// 글 수정
	int modifyBoard(BoardDto board);
	
	// 조회수 증가
	void updateHit(int boardId);
	
	// 파일 삭제
	void deleteFile(int boardId) throws Exception;
	
	// 파일 정보 가져오기
	List<FileInfoDto> fileInfoList(int boardId) throws Exception;
}	
