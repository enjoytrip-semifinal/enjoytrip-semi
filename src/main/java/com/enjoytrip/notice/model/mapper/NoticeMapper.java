package com.enjoytrip.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.enjoytrip.notice.model.NoticeDto;

public interface NoticeMapper {
	List<NoticeDto> listNotice() throws SQLException;

	NoticeDto getNotice(int noticeid) throws SQLException;

	int write(NoticeDto noticeDto) throws SQLException;

	int delete(int noticeid) throws SQLException;

	int modify(NoticeDto notice) throws SQLException;
	
	int updateNoticeHit(int noticeid) throws SQLException;
}
