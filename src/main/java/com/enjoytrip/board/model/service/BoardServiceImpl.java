package com.enjoytrip.board.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.mapper.BoardMapper;
import com.enjoytrip.util.PageNavigation;
import com.enjoytrip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService{
	
	private BoardMapper boardMapper;
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	// 글 가져오기
	@Override
	public List<BoardDto> listBoard(Map<String, String> map) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		
		return boardMapper.listBoard(param);
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
	
	// 페이지네비게이션 만들기
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		
		if ("userid".equals(key))
			key = "user_id";
		
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		
		int totalCount = boardMapper.getTotalBoardCount(param);
		pageNavigation.setTotalCount(totalCount);
		
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}
}
