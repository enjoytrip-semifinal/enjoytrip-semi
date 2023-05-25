package com.enjoytrip.itinerary.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.itinerary.model.mapper.ItineraryReplyMapper;
import com.enjoytrip.util.SizeConstant;

@Service
public class ItineraryReplyServiceImpl implements ItineraryReplyService {

	private ItineraryReplyMapper itineraryReplyMapper;
	
	@Autowired
	public ItineraryReplyServiceImpl(ItineraryReplyMapper itineraryReplyMapper) {
		super();
		this.itineraryReplyMapper = itineraryReplyMapper;
	}
	
	// 댓글 리스트형식으로 가져오기
	@Override
	public List<ItineraryReplyDto> listReply(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("itineraryId", map.get("itineraryId"));
		
		return itineraryReplyMapper.listReply(param);
	}

	//  댓글 쓰기
	@Override
	public int writeReply(ItineraryReplyDto itineraryReply) {
		return itineraryReplyMapper.writeReply(itineraryReply);
	}
	
	//  댓글  삭제
	@Override
	public int deleteReply(int itineraryReplyId) {
		return itineraryReplyMapper.deleteReply(itineraryReplyId);
	}
	
	//  댓글 수정
	@Override
	public int modifyReply(ItineraryReplyDto itineraryReply) {
		return itineraryReplyMapper.modifyReply(itineraryReply);
	}
	
	// 해당 게시글의 댓글 갯수 가져오기
	@Override
	public int getTotalAllItineraryReplyCount(int itineraryId) {
		return itineraryReplyMapper.getTotalAllItineraryReplyCount(itineraryId);
	}

}
