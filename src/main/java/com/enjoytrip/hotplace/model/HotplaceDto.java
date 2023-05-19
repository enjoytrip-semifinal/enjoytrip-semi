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
	private int hotplaceId;
	private int likeCount;
	private int hitCount;
	private int latitude;
	private int longitude;
	private String title;
	private String content;
	private String regTime;
	private String userId;
	private int sido;
	private int gugun;
	private int type;
	private String address;
	private List<HotplaceFileInfoDto> fileInfos;
}
 