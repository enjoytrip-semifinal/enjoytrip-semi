package com.enjoytrip.itinerary.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.itinerary.model.mapper.ItineraryMapper;
import com.enjoytrip.util.PageNavigation;
import com.enjoytrip.util.SizeConstant;

@Service
public class ItineraryServiceImpl implements ItineraryService{

	private ItineraryMapper itinerarymapper;
	
	@Autowired
	public ItineraryServiceImpl(ItineraryMapper itinerarymapper) {
		super();
		this.itinerarymapper = itinerarymapper;
	}
	
	// 여행계획 전체 리스트 가져오기
	@Override
	public List<ItineraryDetailDto> listItinerary(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		
		return itinerarymapper.listItinerary(param);
	}
	// 여행계획 작성하기
	@Override
	public int writeItinerary(ItineraryDetailDto itinerarydetaildto) {
		int result = itinerarymapper.writeItinerary(itinerarydetaildto);
		return result;
	}
	
	// 여행계획 수정하기
	@Override
	public int modifyItinerary(ItineraryDetailDto itinerarydetaildto) {
		return itinerarymapper.modifyItinerary(itinerarydetaildto);
	}
	
	// 여행계획 삭제하기
	@Override
	public int deleteItinerary(int num) {
		return itinerarymapper.deleteItinerary(num);
	}
	
	// 여행계획 세부내용 보기
	@Override
	public ItineraryDetailDto selectOne(Integer num) {
		return itinerarymapper.selectOne(num);
	}
	
	// 페이징 처리
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		
		if ("userid".equals(key))
			key = "user_id";
		
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		
		int totalCount = itinerarymapper.getTotalItineraryCount(param);
		pageNavigation.setTotalCount(totalCount);
		
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	
}
