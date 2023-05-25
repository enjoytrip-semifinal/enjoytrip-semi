package com.enjoytrip.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.hotplace.model.HotplaceFileInfoDto;
import com.enjoytrip.hotplace.model.HotplaceDto;

@Mapper
public interface HotplaceMapper {

	// 1. 핫 플레이스 등록
	int insertHotplace(HotplaceDto hotplaceDto) throws SQLException;

	// 2. 핫 플레이스 삭제
	int deleteHotplace(int hotplaceId) throws SQLException;

	// 3. 핫 플레이스 갱신(수정)
	int updateHotplace(HotplaceDto hotplaceDto) throws SQLException;

	//===============================================================
	// 4. 좋아요 수 증가
	int likeHotplace(int hotplaceId) throws SQLException;
	
	//좋아요 수가 증가하면 좋아하는 유저와 게시물을 테이블에 저장해준다,
	int userLikeHotplace(Map<String, Object> param) throws SQLException;
	
	//좋아요 수 감소
	int hateHotplace(int hotplaceId) throws SQLException;
	
	//좋아요 수가 감소하면 해당 게시물과 유저의 정보를 테이블에서 삭제해준다,
	int userHateHotplace(Map<String, Object> param) throws SQLException;
	//===============================================================
	
	// 5. 조회수 증가
	int updateHit(int hotplaceId) throws SQLException;

	// 6. 전체 목록 불러오기, 현재 광관지 유형을 따라서 보여줄 것이다.
	List<HotplaceDto> listHotplace(Map<String, Object> param) throws SQLException;

	// 7. 파일 등록
	int registerFile(List<String> fileList) throws Exception;

	// 8. 하나의 게시글만 봄
	HotplaceDto getHotplaceById(int hotplaceId) throws SQLException;

	// 9. 파일만 전체 가져오기
	List<HotplaceFileInfoDto> fileInfoList(int hotplaceId) throws Exception;

	// 10. 파일 삭제
	int deleteFileAll(int hotplaceId) throws Exception;
	
	//11. 페이징 처리를 위해 전체 게시글 수를 구해준다.
	int getTotalHotplaceCount(Map<String, Object> param) throws SQLException;
	
	int getTop3() throws Exception;
	
}
