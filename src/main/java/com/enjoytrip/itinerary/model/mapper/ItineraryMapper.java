package com.enjoytrip.itinerary.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;

@Mapper
public interface ItineraryMapper {
	//1. 여행계획 전체 리스트 가져오기
	List<ItineraryDetailDto> listItinerary(Map<String, Object> param);
	
	//2.여행계획 작성하기
	int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	
	//3.여행계획 수정하기
	int modifyItinerary(ItineraryDetailDto itinerarydetaildto);
	
	//4. 여행계획 삭제하기
	int deleteItinerary(int num);
	
	//5. 여행계획 세부내용 보기
	ItineraryDetailDto selectOne(Integer num);
	
	int getTotalItineraryCount(Map<String, Object> param);

	void registerplace(ItineraryDetailDto itinerarydetaildto);

	List<ItineraryPlaceDto> selectPlace(Integer num);


}
