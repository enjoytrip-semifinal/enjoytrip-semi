package com.enjoytrip.itinerary.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.itinerary.model.ItineraryDetailDto;
import com.enjoytrip.itinerary.model.ItineraryDto;
import com.enjoytrip.itinerary.model.ItineraryReviewDto;
import com.enjoytrip.itinerary.model.service.ItineraryService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/itinerary")
@Api(tags="여행계획")
public class ItineraryController {
	
	private ItineraryService itineraryService;
	
	@Autowired
	public ItineraryController(ItineraryService itineraryService) {
		super();
		this.itineraryService = itineraryService;
	}
	//1. 여행계획 전체 리스트 가져오기
	@GetMapping(value="/list")
	public ResponseEntity<?> list() throws Exception {
	      try {
	    	  List<ItineraryDto> list = itineraryService.listItinerary(null);
	         return new ResponseEntity<List<ItineraryDto>>(list, HttpStatus.OK);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	         return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	//2.여행계획 작성하기
	@PostMapping(value="/write")
	public ResponseEntity<?> write(@RequestBody ItineraryDetailDto Itinerarydetaildto) throws Exception {
	      try {
	         return new ResponseEntity<Integer>(itineraryService.writeItinerary(Itinerarydetaildto), HttpStatus.OK);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	         return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
	
	//3.여행계획 수정하기
	@PutMapping(value="/modify")
	public ResponseEntity<?> modify(@RequestBody ItineraryDetailDto Itinerarydetaildto) throws Exception {
		try {
			return new ResponseEntity<Integer>(itineraryService.modifyItinerary(Itinerarydetaildto), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//4. 여행계획 삭제하기
	@DeleteMapping(value="/list/{itineraryid}")
	public ResponseEntity<?> delete(@PathVariable("itineraryid")Integer num) throws Exception {
		try {
			return new ResponseEntity<Integer>(itineraryService.deleteItinerary(num), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//5. 여행계획 세부내용 보기
	@GetMapping(value="/list/{itineraryid}")
	public ResponseEntity<?> listOneView(@PathVariable("itineraryid") Integer num) throws Exception {
		try {
			return new ResponseEntity<ItineraryDto>(itineraryService.selectOne(num), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//6.여행계획댓글 작성하기
	@PostMapping(value="/review")
	public ResponseEntity<?> review(@RequestBody ItineraryReviewDto itineraryreviewdto) throws Exception {
	      try {
	         return new ResponseEntity<Integer>(itineraryService.reviewItinerary(itineraryreviewdto), HttpStatus.OK);
	      }catch(Exception e) {
	    	  e.printStackTrace();
	         return new ResponseEntity<String>("서버 오류",HttpStatus.INTERNAL_SERVER_ERROR);
	      }
	}
}
