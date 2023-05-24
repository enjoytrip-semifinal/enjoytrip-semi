package com.enjoytrip.itinerary.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.util.PageNavigation;

public interface ItineraryService {
	
	// 여행계획 전체 리스트 가져오기
	public List<ItineraryDetailDto> listItinerary(Map<String, String> map) ;
	
	// 여행계획 작성
	public int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	
	// 여행계획 수정
	public int modifyItinerary(ItineraryDetailDto Itinerarydetaildto);
	
	// 여행계획 삭제
	public int deleteItinerary(int num);
	
	// 여행계획 글 하나 조회
	public ItineraryDetailDto selectOne(Integer num);
	
	//총 여행계획 갯수 가져오기
	public int getTotalAllItineraryCount();
}
