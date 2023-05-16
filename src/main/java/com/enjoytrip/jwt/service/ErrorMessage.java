package com.enjoytrip.jwt.service;

import java.util.Date;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ErrorMessage {
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;

	public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
}
