package com.enjoytrip.notice.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class NoticeDto {
	private int noticeid;
	private String registertime;
	private int hit;
	private String title;
	private String content;
	private int adminid;
}
