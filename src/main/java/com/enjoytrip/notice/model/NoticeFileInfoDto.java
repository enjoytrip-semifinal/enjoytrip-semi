package com.enjoytrip.notice.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class NoticeFileInfoDto {
	private int noticeFileId;
	private String fileUrl;
	private int noticeId;
}
