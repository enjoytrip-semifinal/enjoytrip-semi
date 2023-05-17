package com.enjoytrip.board.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	private int board_id;
	private String register_time;
	private int hit;
	private String title;
	private String content;
	private int user_id;
	private String nickname;
	private List<FileInfoDto> fileInfos;
}