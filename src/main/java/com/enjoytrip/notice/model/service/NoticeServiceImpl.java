package com.enjoytrip.notice.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoytrip.notice.model.NoticeDto;
import com.enjoytrip.notice.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService{

	private NoticeMapper noticeMapper;
	
	@Autowired
	public NoticeServiceImpl(NoticeMapper noticeMapper) {
		super();
		this.noticeMapper = noticeMapper;
	}
	
	@Override
	public List<NoticeDto> listNotice() throws Exception {
		return noticeMapper.listNotice();
	}

	@Override
	public NoticeDto getNotice(int noticeid) throws Exception {
		return noticeMapper.getNotice(noticeid);
	}

	@Override
	public int write(NoticeDto noticeDto) throws Exception {
		return noticeMapper.write(noticeDto);
	}

	@Override
	public int delete(int noticeid) throws Exception {
		return noticeMapper.delete(noticeid);
	}

	@Override
	public int modify(NoticeDto notice) throws Exception {
		return noticeMapper.modify(notice);
	}

	@Override
	public int updateNoticeHit(int noticeid) throws Exception {
		return noticeMapper.updateNoticeHit(noticeid);
	}
}
