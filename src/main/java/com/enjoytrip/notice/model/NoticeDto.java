package com.enjoytrip.notice.model;

import lombok.Data;

@Data
public class NoticeDto {
	private int noticeid;
	private String registertime;
	private int hit;
	private String title;
	private int userid;
}
