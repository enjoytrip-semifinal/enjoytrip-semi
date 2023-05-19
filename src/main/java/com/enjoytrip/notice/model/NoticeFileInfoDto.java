package com.enjoytrip.notice.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class NoticeFileInfoDto {
	private String saveFolder;
	private String originalFile;
	private String saveFile;
}
