package com.enjoytrip.board.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileInfoDto {
	private int board_file_id;
	private String file_url;
	private int board_id ;
}