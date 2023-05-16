package com.enjoytrip.itinerary.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.util.PageNavigation;

public interface ItineraryService {
	
	public List<ItineraryDetailDto> listItinerary(Map<String, String> map) ;
	public int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	public int modifyItinerary(ItineraryDetailDto Itinerarydetaildto);
	public int deleteItinerary(int num);
	public ItineraryDetailDto selectOne(Integer num);
	public PageNavigation makePageNavigation(Map<String, String> map);
	public List<ItineraryPlaceDto> selectPlace(Integer num);
}
