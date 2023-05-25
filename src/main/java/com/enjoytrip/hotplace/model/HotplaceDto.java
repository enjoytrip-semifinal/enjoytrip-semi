package com.enjoytrip.hotplace.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class HotplaceDto {
	private int hotplaceId;
	private int likeCount;
	private int hitCount;
	private int latitude;
	private int longitude;
	private String title;
	private String content;
	private String regDate;
	private int userId;
	private int type;
	private String address;
	private List<String> fileList;
	private int season;
	private String nickname;
}
