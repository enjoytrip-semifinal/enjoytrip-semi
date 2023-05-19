package com.enjoytrip.board.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardReviewDto {
	private int boardreviewid;
	private String title;
	private String content;
	private String date;
	private String nickname;
	private int userid;
	private int boardid;
}
