package com.enjoytrip.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {
	private int boardid;
	private String registertime;
	private int hit;
	private String title;
	private String content;
	private int userid;
}