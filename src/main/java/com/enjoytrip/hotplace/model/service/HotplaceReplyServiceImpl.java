package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.hotplace.model.HotplaceReplyDto;
import com.enjoytrip.hotplace.model.mapper.HotplaceReplyMapper;

@Service
public class HotplaceReplyServiceImpl implements HotplaceReplyService{
	
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
	public int modifyReply(int replyId) throws SQLException {
		// TODO Auto-generated method stub
		return replyMapper.modifyReply(replyId);
	}

	@Override
	public int deleteReplyAll(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		return replyMapper.deleteReplyAll(hotplaceId);
	}


}
