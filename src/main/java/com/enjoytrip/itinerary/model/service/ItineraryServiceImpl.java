package com.enjoytrip.itinerary.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryDto;
import com.enjoytrip.itinerary.model.ItineraryReviewDto;
import com.enjoytrip.itinerary.model.mapper.ItineraryMapper;

@Service
public class ItineraryServiceImpl implements ItineraryService{

	private ItineraryMapper itinerarymapper;
	
	@Autowired
	public ItineraryServiceImpl(ItineraryMapper itinerarymapper) {
		super();
		this.itinerarymapper = itinerarymapper;
	}
	
	//1. 여행계획 전체 리스트 가져오기
	@Override
	public List<ItineraryDto> listItinerary(Map<String, Object> map) {
		return itinerarymapper.listItinerary(map);
	}
	//2.여행계획 작성하기
	@Override
	public int writeItinerary(ItineraryDetailDto itinerarydetaildto) {
		return itinerarymapper.writeItinerary(itinerarydetaildto);
	}
	
	//3.여행계획 수정하기
	@Override
	public int modifyItinerary(ItineraryDetailDto itinerarydetaildto) {
		return itinerarymapper.modifyItinerary(itinerarydetaildto);
	}
	
	//4. 여행계획 삭제하기
	@Override
	public int deleteItinerary(int num) {
		return itinerarymapper.deleteItinerary(num);
	}
	
	//5. 여행계획 세부내용 보기
	@Override
	public ItineraryDto selectOne(Integer num) {
		return itinerarymapper.selectOne(num);
	}
	
	//6.여행계획댓글 작성하기
	@Override
	public Integer reviewItinerary(ItineraryReviewDto itineraryreviewdto) {
		return itinerarymapper.reviewItinerary(itineraryreviewdto);
	}


	
}
