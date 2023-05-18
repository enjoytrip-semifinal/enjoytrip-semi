package com.enjoytrip.itinerary.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.itinerary.model.mapper.ItineraryReplyMapper;
import com.enjoytrip.util.SizeConstant;

@Service
public class ItineraryReplyServiceImpl implements ItineraryReplyService {

	private ItineraryReplyMapper itineraryReplyMapper;
	public ItineraryReplyServiceImpl(ItineraryReplyMapper itineraryReplyMapper) {
		super();
		this.itineraryReplyMapper = itineraryReplyMapper;
	}
	
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

	@Override
	public int writeReply(ItineraryReplyDto itineraryReply) {
		return itineraryReplyMapper.writeReply(itineraryReply);
	}

	@Override
	public int deleteReply(int itineraryReplyId) {
		return itineraryReplyMapper.deleteReply(itineraryReplyId);
	}

	@Override
	public int modifyReply(ItineraryReplyDto itineraryReply) {
		return itineraryReplyMapper.modifyReply(itineraryReply);
	}

}
