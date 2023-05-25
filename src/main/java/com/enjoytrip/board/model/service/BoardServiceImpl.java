package com.enjoytrip.board.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.board.model.BoardDto;
import com.enjoytrip.board.model.FileInfoDto;
import com.enjoytrip.board.model.mapper.BoardMapper;
import com.enjoytrip.board.model.mapper.BoardReviewMapper;
import com.enjoytrip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService{
	
	private BoardMapper boardMapper;
	private BoardReviewMapper boardReviewMapper;
	
	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper, BoardReviewMapper boardReviewMapper) {
		this.boardMapper = boardMapper;
		this.boardReviewMapper = this.boardReviewMapper;
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
	
	// 글 하나 가져오기
	@Override
	public Map<String, Object> viewBoard(int boardId) throws Exception {
		Map<String, Object> serviceMap = new HashMap<>();
		
		// 게시글 하나 가져오기
		serviceMap.put("board", boardMapper.viewBoard(boardId));
		
		// 게시글의 파일 정보 가져오기
		serviceMap.put("files", boardMapper.fileInfoList(boardId));
		
		return serviceMap;
	}
	
	// 글 쓰기
	@Override
	@Transactional
	public int writeBoard(BoardDto board) throws Exception {
		
		// 게시판 글 쓰기
		int reuslt = boardMapper.writeBoard(board);
		
		//업로드 할 파일 정보 얻어오기
		List<String> uploadFiles = board.getFileInfos();
		
		// 파일 정보가 있다면
		if (uploadFiles != null && uploadFiles.size() > 0) {
			List<FileInfoDto> files = new ArrayList<>();
			for (String fileName : uploadFiles) {
				FileInfoDto file = new FileInfoDto();
				file.setFile_url(fileName);
				files.add(file);
			}
			
			boardMapper.registerFileInfo(files);
		}
		
		return reuslt;
	}

	// 글 삭제
	@Override
	public int deleteBoard(int boardId) throws Exception {
		// 글과 연관된 파일 및 댓글을 먼저 삭제 후 글을 삭제
		List<FileInfoDto> fileList = boardMapper.fileInfoList(boardId);
		if (!fileList.isEmpty()) {
			try {
				// (1) 파일 삭제
				boardMapper.deleteFile(boardId);
				
				// (2) 댓글 삭제
				boardReviewMapper.deleteAllReview(boardId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return boardMapper.deleteBoard(boardId);
	}

	// 글 수정
	@Override
	public int modifyBoard(BoardDto board, String[] path) throws Exception {
		// 글과 연관된 파일을 먼저 삭제
		List<FileInfoDto> fileList = boardMapper.fileInfoList(board.getBoard_id());
		if (!fileList.isEmpty()) {
			try {
				boardMapper.deleteFile(board.getBoard_id());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 파일 정보가 있다면 insert
		if (path != null && path.length > 0) {
			List<FileInfoDto> fileInfoList = new ArrayList<>();
			for (int i = 0; i < path.length; i++) {
				FileInfoDto fileInfo = new FileInfoDto();
				fileInfo.setBoard_id(board.getBoard_id());
				fileInfo.setFile_url(path[i]);
				
				fileInfoList.add(fileInfo);
			}
			boardMapper.registerFileInfo(fileInfoList);
		}
		
		return boardMapper.modifyBoard(board);
	}

	// 조회수 증가
	@Override
	public void updateHit(int boardId) throws Exception{
		boardMapper.updateHit(boardId);
	}

	// 전체 글 수 반환하기
	@Override
	public int getTotalAllBoardCount() throws Exception {
		return boardMapper.getTotalAllBoardCount();
	}
}
