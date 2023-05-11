package com.enjoytrip.itinerary.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryDto;
import com.enjoytrip.itinerary.model.ItineraryReviewDto;

@Mapper
public interface ItineraryMapper {
	//1. 여행계획 전체 리스트 가져오기
	List<ItineraryDto> listItinerary(Map<String, Object> map);
	
	//2.여행계획 작성하기
	int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	
	//3.여행계획 수정하기
	int modifyItinerary(ItineraryDetailDto itinerarydetaildto);
	
	//4. 여행계획 삭제하기
	int deleteItinerary(int num);
	
	//5. 여행계획 세부내용 보기
	ItineraryDto selectOne(Integer num);
	
	//6.여행계획댓글 작성하기
	Integer reviewItinerary(ItineraryReviewDto itineraryreviewdto);


}
