package com.enjoytrip.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.notice.model.NoticeDto;
import com.enjoytrip.notice.model.NoticeFileInfoDto;

@Mapper
public interface NoticeMapper {

	List<NoticeDto> listNotice(Map<String, Object> param) throws SQLException;

	NoticeDto getNotice(int noticeid) throws SQLException;

	int write(NoticeDto noticeDto) throws SQLException;

	int delete(int noticeid) throws SQLException;

	int modify(NoticeDto notice) throws SQLException;

	int updateNoticeHit(int noticeid) throws SQLException;

	int getTotalNoticeCount(Map<String, Object> param) throws SQLException;

	int registFiles(List<NoticeFileInfoDto> files) throws SQLException;
	
	List<NoticeFileInfoDto> getFiles(int noticeid) throws SQLException;

}
