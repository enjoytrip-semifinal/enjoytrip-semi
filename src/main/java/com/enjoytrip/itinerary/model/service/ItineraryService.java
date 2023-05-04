package com.enjoytrip.itinerary.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryDto;
import com.enjoytrip.itinerary.model.ItineraryReviewDto;

public interface ItineraryService {
	
	public List<ItineraryDto> listItinerary(Map<String, Object> map) ;
	public int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	public int modifyItinerary(ItineraryDetailDto Itinerarydetaildto);
	public int deleteItinerary(int num);
	public ItineraryDto selectOne(Integer num);
	public Integer reviewItinerary(ItineraryReviewDto itineraryreviewdto);
}
