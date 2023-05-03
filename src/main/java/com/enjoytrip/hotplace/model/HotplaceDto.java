package com.enjoytrip.hotplace.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HotplaceDto {
	private int reviewId;
	private int type;
	private int gugun;
	private int sido;
	private int latitude;
	private int longitude;
	private String title;
	private String content;
	private String regDate;
	private List<FileInfoDto> fileInfos;
	private String userId;
	private int like;
	private int hit;
}
 