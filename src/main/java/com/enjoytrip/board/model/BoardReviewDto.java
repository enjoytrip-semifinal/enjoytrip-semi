package com.enjoytrip.board.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardReviewDto {
	private int board_reply_id;
	private String content;
	private String register_date;
	private String nickname;
	private int board_id;
	private int user_id;
}
