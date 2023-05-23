package com.enjoytrip.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	private int board_id;
	private String register_date;
	private int hit;
	private String title;
	private String content;
	private int user_id;
	private String nickname;
}