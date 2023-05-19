package com.enjoytrip.itinerary.model;

import lombok.Data;

@Data
public class ItineraryReplyDto {
	private int itineraryReplyId;
	private String content;
	private String date;
	private int itineraryId;
	private int userId;
}
