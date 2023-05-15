package com.enjoytrip.itinerary.model;

import lombok.Data;

@Data
public class ItineraryDetailDto {
	private int itineraryId;
	private int userid;
	private String date;
	private String startDate;
	private String endDate;
	private String title;
	private String content;
}
