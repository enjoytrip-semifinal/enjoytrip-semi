package com.enjoytrip.itinerary.model;

import java.util.List;

import lombok.Data;

@Data
public class ItineraryDetailDto {
	private int itineraryId;
	private int userId;
	private String date;
	private String startDate;
	private String endDate;
	private String title;
	private String content;
	private List<ItineraryPlaceDto> ItineraryPlaces;
}
