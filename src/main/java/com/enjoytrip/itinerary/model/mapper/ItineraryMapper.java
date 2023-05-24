package com.enjoytrip.itinerary.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;

@Mapper
public interface ItineraryMapper {
	
	// 여행계획 전체 리스트 가져오기
	List<ItineraryDetailDto> listItinerary(Map<String, Object> param);
	
	// 여행계획 작성
	int writeItinerary(ItineraryDetailDto itinerarydetaildto);
	
	// 여행계획 수정
	int modifyItinerary(ItineraryDetailDto itinerarydetaildto);
	
	// 여행계획 삭제
	int deleteItinerary(int num);
	
	// 여행계획 글 하나 조회
	ItineraryDetailDto selectOne(Integer num);
	
	// 여행계획 글에 포함되는 장소들 조회
	List<ItineraryPlaceDto> selectPlace(Integer num);
	
	// 여행계획 작성에 포함되는 장소들 등록
	void writePlace(ItineraryPlaceDto itineraryPlace);
	
	// 여행계획 수정에 해당되는 장소들 수정
	void modifyPlace(ItineraryPlaceDto itineraryPlace);
	
	//총 여행계획 갯수 가져오기
	int getTotalAllItineraryCount();

}
