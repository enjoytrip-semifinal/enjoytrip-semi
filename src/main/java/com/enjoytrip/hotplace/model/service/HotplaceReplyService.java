package com.enjoytrip.hotplace.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.hotplace.model.HotplaceReplyDto;

public interface HotplaceReplyService {
	//전체 댓글 불러오기
	List<HotplaceReplyDto> listReply(Map<String, String> map);
	//댓글 쓰기
	int insertReply(HotplaceReplyDto replyDto);
	//댓글 삭제
	int deleteReply(int replyId);
	//댓글 수정
	int updateReply(HotplaceReplyDto replyDto);
	//해당 게시물에 속하는 댓글 전체 삭제
	int deleteReplyAll(int hotplaceId);
}
