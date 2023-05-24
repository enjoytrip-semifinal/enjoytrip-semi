package com.enjoytrip.itinerary.model.mapper;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.itinerary.model.ItineraryReplyDto;
@Mapper
public interface ItineraryReplyMapper {

	List<ItineraryReplyDto> listReply(Map<String, Object> param);

	int writeReply(ItineraryReplyDto itineraryReply);

	int deleteReply(int itineraryReplyId);

	int modifyReply(ItineraryReplyDto itineraryReply);

	int getTotalAllItineraryReplyCount(int itineraryReplyId);

}
