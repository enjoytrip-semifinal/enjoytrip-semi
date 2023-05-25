package com.enjoytrip.hotplace.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class HotplaceFileInfoDto {
	private int fileId;
	private int hotplaceId;
	private String fileName;
}
 