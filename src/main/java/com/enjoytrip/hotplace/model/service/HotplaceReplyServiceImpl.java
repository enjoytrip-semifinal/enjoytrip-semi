package com.enjoytrip.hotplace.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.enjoytrip.hotplace.model.HotplaceReplyDto;
import com.enjoytrip.hotplace.model.mapper.HotplaceReplyMapper;
import com.enjoytrip.util.SizeConstant;

@Service
public class HotplaceReplyServiceImpl implements HotplaceReplyService {

	private HotplaceReplyMapper replyMapper;

	public HotplaceReplyServiceImpl(HotplaceReplyMapper replyMapper) {
		super();
		this.replyMapper = replyMapper;
	}

	@Override
	public List<HotplaceReplyDto> listReply(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();

		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;

		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("id", Integer.parseInt(map.get("id")));

		return replyMapper.listReply(map);
	}

	@Override
	public int insertReply(HotplaceReplyDto replyDto) {
		return replyMapper.insertReply(replyDto);
	}

	@Override
	public int deleteReply(int replyId) {
		return replyMapper.deleteReply(replyId);
	}

	@Override
	public int updateReply(HotplaceReplyDto replyDto) {
		return replyMapper.updateReply(replyDto);
	}

	@Override
	public int deleteReplyAll(int hotplaceId) {
		return replyMapper.deleteReply(hotplaceId);
	}

	@Override
	public int getTotalCount(int hotplaceId) {
		// TODO Auto-generated method stub
		return replyMapper.getTotalCount(hotplaceId);
	}

	
	
}
