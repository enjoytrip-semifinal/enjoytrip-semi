package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.enjoytrip.board.model.BoardReviewDto;
import com.enjoytrip.hotplace.model.HotplaceReplyDto;

import io.jsonwebtoken.io.IOException;

public interface HotplaceReplyService {
	// 삽삭갱 해야함
	// 1. 댓글 등록
	int writeReply(HotplaceReplyDto replyDto) throws SQLException;

	// 2. 댓글 삭제
	int deleteReply(int replyId) throws SQLException;

	// 3. 댓글 수정
	int modifyReply(HotplaceReplyDto replyDto) throws SQLException;

	// 4. 특정 게시글에 혹하는 모든 댓글 삭제
	int deleteReplyAll(int hotplaceId) throws SQLException;

	// 5. 특정 게시글에 속하는 모든 댓글 불러오기
	List<HotplaceReplyDto> allReply(Map<String, String> map);
}
