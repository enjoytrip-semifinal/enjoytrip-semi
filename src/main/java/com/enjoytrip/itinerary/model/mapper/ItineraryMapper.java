package com.enjoytrip.itinerary.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;

@Mapper
public interface ItineraryMapper {
	
	List<ItineraryDetailDto> listItinerary(Map<String, Object> param);
	int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	int modifyItinerary(ItineraryDetailDto itinerarydetaildto);
	int deleteItinerary(int num);
	ItineraryDetailDto selectOne(Integer num);
	int getTotalItineraryCount(Map<String, Object> param);
	List<ItineraryPlaceDto> selectPlace(Integer num);

}
