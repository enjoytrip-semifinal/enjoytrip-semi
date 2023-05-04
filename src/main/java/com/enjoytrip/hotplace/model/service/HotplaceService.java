package com.enjoytrip.hotplace.model.service;

import java.util.List;
import java.util.Map;

import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.util.PageNavigation;

import io.swagger.models.auth.In;

public interface HotplaceService {
	// 1. 핫 플레이스 등록
	int writeHotplace(HotplaceDto hotplaceDto) throws Exception;

	// 2. 핫 플레이스 삭제
	int deleteHotplace(int reviewId, String path) throws Exception;

	// 3. 핫 플레이스 갱신(수정)
	int modifyHotplace(HotplaceDto hotplaceDto) throws Exception;

	// 4. 좋아요 수 증가
	int updateLike(int reviewId) throws Exception;

	// 5. 조회수 증가
	int updateHit(int reviewId) throws Exception;

	// 6. 전체 목록 불러오기, 현재 광관지 유형을 따라서 보여줄 것이다.
	List<HotplaceDto> listHotplace(Map<String, String> param) throws Exception;
	//List<HotplaceDto> listHotplace() throws Exception;

	// 7. 파일 등록
	//void registerFile(HotplaceDto hotplaceDto) throws Exception;

	// 8. 하나의 게시글만 봄
	HotplaceDto getHotplace(int reviewId) throws Exception;
	
	//9. 페이징 처리인것 같음
	PageNavigation makePageNavigation(Map<String, Integer> map) throws Exception;
}
