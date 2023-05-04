package com.enjoytrip.notice.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.notice.model.NoticeDto;

public interface NoticeService {
	List<NoticeDto> listNotice() throws Exception;

	NoticeDto getNotice(int noticeid) throws Exception;

	int write(NoticeDto notice) throws Exception;

	int delete(int noticeid) throws Exception;

	int modify(NoticeDto notice) throws Exception;

	int updateNoticeHit(int noticeid) throws Exception;
}
