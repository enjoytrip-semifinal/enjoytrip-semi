package com.enjoytrip.hotplace.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HotplaceFileInfoDto {
	private int fileId;
	private int hotplaceId;
	private String url;
}
 