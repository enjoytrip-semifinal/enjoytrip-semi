package com.enjoytrip.itinerary.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.enjoytrip.board.model.FileInfoDto;
import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryPlaceDto;
import com.enjoytrip.itinerary.model.ItineraryReplyDto;
import com.enjoytrip.itinerary.model.service.ItineraryService;
import com.enjoytrip.util.PageNavigation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/itinerary")
@Api(tags = "여행계획")
public class ItineraryController {

	private ItineraryService itineraryService;

	@Autowired
	public ItineraryController(ItineraryService itineraryService) {
		super();
		this.itineraryService = itineraryService;
	}

	// 1. 여행계획 전체 리스트 가져오기
	@GetMapping(value = "/list")
	public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
		List<ItineraryDetailDto> list = itineraryService.listItinerary(map);
		PageNavigation pageNavigation = itineraryService.makePageNavigation(map);

		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("itineraryList", list);
		returnMap.put("navigation", pageNavigation);

		returnMap.put("pgno", map.get("pgno"));
		returnMap.put("key", map.get("key"));
		returnMap.put("word", map.get("word"));

		if (list != null && !list.isEmpty()) {
			return new ResponseEntity<List<ItineraryDetailDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

	}

	// 2.여행계획 작성하기
	@PostMapping(value = "/write")
	public ResponseEntity<?> write(@RequestBody ItineraryDetailDto Itinerarydetaildto,
			MultipartHttpServletRequest multipartRequest) throws Exception {

		List<ItineraryPlaceDto> itineraryPlaces = new ArrayList<ItineraryPlaceDto>();
		ItineraryPlaceDto itineraryPlaceDto = new ItineraryPlaceDto();
		itineraryPlaceDto.setPlaceOrder(0);
		itineraryPlaceDto.setPlaceName(multipartRequest.getParameter("placeName"));
		itineraryPlaceDto.setPlaceComment(multipartRequest.getParameter("placeComment"));
		itineraryPlaceDto.setPlaceAddress(multipartRequest.getParameter("placeAddress"));
		
		itineraryPlaces.add(itineraryPlaceDto);
		Itinerarydetaildto.setItineraryPlaces(itineraryPlaces);
		
		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");

		int result = itineraryService.writeItinerary(Itinerarydetaildto);
		if (result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}

	}

	// 3.여행계획 수정하기
	@PutMapping(value = "/modify/{itinersryid}")
	public ResponseEntity<?> modify(@RequestBody ItineraryDetailDto Itinerarydetaildto) throws Exception {
		int result = itineraryService.modifyItinerary(Itinerarydetaildto);

		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");

		if (result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	// 4. 여행계획 삭제하기
	@DeleteMapping(value = "/delete/{itineraryId}")
	public ResponseEntity<?> delete(@PathVariable("itineraryId") Integer num) throws Exception {
		int result = itineraryService.deleteItinerary(num);

		// 페이징 처리를 위한 Map
		Map<String, String> pageMap = new HashMap<String, String>();
		pageMap.put("pgno", "1");

		if (result > 0) {
			return new ResponseEntity<Map>(pageMap, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	// 5. 여행계획 세부내용 보기
	@GetMapping(value = "/detail/{itineraryid}")
	public ResponseEntity<?> listOneView(@PathVariable("itineraryid") Integer num) throws Exception {
		ItineraryDetailDto itinerary = itineraryService.selectOne(num);
		List<ItineraryPlaceDto> itineraryPlaces = new ArrayList<ItineraryPlaceDto>();
		itineraryPlaces = itineraryService.selectPlace(num);
		
		itinerary.setItineraryPlaces(itineraryPlaces);
		
		if (itinerary != null) {
			return new ResponseEntity<ItineraryDetailDto>(itinerary, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
