package com.enjoytrip.hotplace.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.hotplace.model.HotplaceReplyDto;

@Mapper
public interface HotplaceReplyMapper {

	// 전체 댓글 불러오기
	List<HotplaceReplyDto> listReply(Map<String, String> map);

	// 댓글 쓰기
	int insertReply(HotplaceReplyDto hotplaceReplyDto);

	// 댓글 삭제
	int deleteReply(int replyId);

	// 댓글 수정
	int updateReply(HotplaceReplyDto hotplaceReplyDto);

	// 해당 게시물에 속하는 댓글 전체 삭제
	int deleteReplyAll(int hotplaceId);
	
	//해당 게시글에 속하는 전체 댓글ㅈ의 개수를 가져온다
	int getTotalCount(int hotplaceId);
}
