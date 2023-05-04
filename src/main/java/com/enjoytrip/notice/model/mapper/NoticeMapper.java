package com.enjoytrip.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.notice.model.NoticeDto;

@Mapper
public interface NoticeMapper {
	List<NoticeDto> listNotice() throws SQLException;

	NoticeDto getNotice(int noticeid) throws SQLException;

	int write(NoticeDto noticeDto) throws SQLException;

	int delete(int noticeid) throws SQLException;

	int modify(NoticeDto notice) throws SQLException;
	
	int updateNoticeHit(int noticeid) throws SQLException;
}
