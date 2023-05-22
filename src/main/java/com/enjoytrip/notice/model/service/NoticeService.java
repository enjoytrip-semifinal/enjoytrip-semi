package com.enjoytrip.notice.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.notice.model.NoticeDto;
import com.enjoytrip.util.PageNavigation;

public interface NoticeService {
	List<NoticeDto> listNotice(Map<String, String> map) throws Exception;

	NoticeDto getNotice(int noticeid) throws Exception;

	int write(NoticeDto notice, String[] url) throws Exception;

	int delete(int noticeid) throws Exception;

	int modify(NoticeDto notice) throws Exception;

	int updateNoticeHit(int noticeid) throws Exception;

	PageNavigation makePageNav(Map<String, String> map) throws Exception;
}
