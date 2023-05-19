package com.enjoytrip.hotplace.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HotplaceReplyDto {
	private int replyId;
	private int userId;
	private int hotplaceId;
	private String content;
	private String regDate;
}
