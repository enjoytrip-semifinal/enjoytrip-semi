package com.enjoytrip.itinerary.model;

import lombok.Data;

@Data
public class ItineraryPlaceDto {
	private String placeName;
	private String placeAddress;
	private int placeOrder;
	private String placeComment;
	private int itineraryId;
}
