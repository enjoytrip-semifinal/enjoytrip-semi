package com.enjoytrip.itinerary.model;

import lombok.Data;

@Data
public class ItineraryDetailDto {
	private int orders;
	private int itineraryid;
	private String placeid;
	private int userid;
	private String date;
	private String startdate;
	private String enddate;
	private String title;
	private String content;
}
