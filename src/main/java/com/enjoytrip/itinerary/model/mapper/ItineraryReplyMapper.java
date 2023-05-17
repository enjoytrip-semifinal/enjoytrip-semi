package com.enjoytrip.itinerary.model.mapper;

import java.util.List;
import java.util.Map;

import com.enjoytrip.itinerary.model.ItineraryReplyDto;

public interface ItineraryReplyMapper {

	List<ItineraryReplyDto> listReply(Map<String, Object> param);

	int writeReply(ItineraryReplyDto itineraryReply);

	int deleteReply(int itineraryReplyId);

	int modifyReply(ItineraryReplyDto itineraryReply);

}