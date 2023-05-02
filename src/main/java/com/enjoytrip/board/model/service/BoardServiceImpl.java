package com.enjoytrip.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
	
	private BoardMapper boardMapper;
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	// 글 가져오기
	@Override
	public List<BoardDto> listBoard() {
		return boardMapper.listBoard();
	}
	
	// 글 쓰기
	@Override
	public int writeBoard(BoardDto board) {
		return boardMapper.writeBoard(board);
	}

	// 글 삭제
	@Override
	public int deleteBoard(int boardId) {
		return boardMapper.deleteBoard(boardId);
	}

	// 글 수정
	@Override
	public int modifyBoard(BoardDto board) {
		return boardMapper.modifyBoard(board);
	}

	// 글 하나 가져오기
	@Override
	public BoardDto viewBoard(int boardId) {
		return boardMapper.viewBoard(boardId);
	}
}
