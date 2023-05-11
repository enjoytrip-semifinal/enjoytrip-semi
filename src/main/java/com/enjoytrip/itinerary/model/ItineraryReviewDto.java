package com.enjoytrip.itinerary.model;

import lombok.Data;

@Data
public class ItineraryReviewDto {
	private int itineraryreviewid;
	private String content;
	private String date;
	private int orders;
	private int itineraryid;
	private int userid;
}
