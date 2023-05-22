package com.enjoytrip.itinerary.model;

import java.util.List;

import lombok.Data;

@Data
public class ItineraryDetailDto {
	private int itineraryId;
	private int userId;
	private String registerDate;
	private String startDate;
	private String endDate;
	private String title;
	private String content;
	private String nickname;
	private List<ItineraryPlaceDto> ItineraryPlaces;
}
