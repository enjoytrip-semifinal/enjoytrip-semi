package com.enjoytrip.itinerary.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.itinerary.model.ItineraryReplyDto;

public interface ItineraryReplyService {

	List<ItineraryReplyDto> listReply(Map<String, String> map);

	int writeReply(ItineraryReplyDto itineraryReply);

	int deleteReply(int itineraryReplyId);

	int modifyReply(ItineraryReplyDto itineraryReply);

	int getTotalAllItineraryReplyCount(int itineraryId);

}
