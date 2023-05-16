package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.board.model.BoardReviewDto;
import com.enjoytrip.hotplace.model.HotplaceReplyDto;
import com.enjoytrip.hotplace.model.mapper.HotplaceReplyMapper;
import com.enjoytrip.util.SizeConstant;

import io.jsonwebtoken.io.IOException;

@Service
public class HotplaceReplyServiceImpl implements HotplaceReplyService {

	private HotplaceReplyMapper replyMapper;

	public HotplaceReplyServiceImpl(HotplaceReplyMapper replyMapper) {
		super();
		this.replyMapper = replyMapper;
	}

	@Override
	@Transactional
	public int writeReply(HotplaceReplyDto replyDto) throws SQLException {
		// TODO Auto-generated method stub

		System.out.println("HotplaceReplyServiceImpl(write) called!!");
		return replyMapper.writeReply(replyDto);
	}

	@Override
	@Transactional
	public int deleteReply(int replyId) throws SQLException {
		// TODO Auto-generated method stub
		return replyMapper.deleteReply(replyId);
	}

	@Override
	@Transactional
	public int modifyReply(HotplaceReplyDto replyDto) throws SQLException {
		// TODO Auto-generated method stub
		return replyMapper.modifyReply(replyDto);
	}

	@Override
	@Transactional
	public int deleteReplyAll(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		return replyMapper.deleteReplyAll(hotplaceId);
	}

	// 리뷰 반환
	@Override
	public List<HotplaceReplyDto> allReply(Map<String, String> map) {

		Map<String, Object> param = new HashMap<String, Object>();

		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;

		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		param.put("boardid", map.get("boardid"));

		return replyMapper.allReply(param);
	}

}
