package com.enjoytrip.itinerary.model.mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.itinerary.model.ItineraryReplyDto;
@Mapper
public interface ItineraryReplyMapper {
	
	// 댓글 리스트형식으로 가져오기
	List<ItineraryReplyDto> listReply(Map<String, Object> param);
	
	//  댓글 쓰기
	int writeReply(ItineraryReplyDto itineraryReply);
	
	//  댓글  삭제
	int deleteReply(int itineraryReplyId);
	
	//  댓글 수정
	int modifyReply(ItineraryReplyDto itineraryReply);
	
	// 해당 게시글의 댓글 갯수 가져오기
	int getTotalAllItineraryReplyCount(int itineraryId);

}
