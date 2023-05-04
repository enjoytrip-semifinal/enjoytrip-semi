package com.enjoytrip.hotplace.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.hotplace.model.FileInfoDto;
import com.enjoytrip.hotplace.model.HotplaceDto;

@Mapper
public interface HotplaceMapper {

	// 1. 핫 플레이스 등록
	int writeHotplace(HotplaceDto hotplaceDto) throws SQLException;

	// 2. 핫 플레이스 삭제
	int deleteHotplace(int reviewId) throws SQLException;

	// 3. 핫 플레이스 갱신(수정)
	int modifyHotplace(HotplaceDto hotplaceDto) throws SQLException;

	// 4. 좋아요 수 증가
	int updateLike(int reviewId) throws SQLException;

	// 5. 조회수 증가
	int updateHit(int reviewId) throws SQLException;

	// 6. 전체 목록 불러오기, 현재 광관지 유형을 따라서 보여줄 것이다.
	List<HotplaceDto> listHotplace(Map<String, Integer> param) throws SQLException;
	//List<HotplaceDto> listHotplace() throws SQLException;

	// 7. 파일 등록
	void registerFile(HotplaceDto hotplaceDto) throws Exception;

	// 8. 하나의 게시글만 봄
	HotplaceDto getHotplace(int reviewId) throws SQLException;

	// 9. 파일만 전체 가져오기
	List<FileInfoDto> fileInfoList(int reviewId) throws Exception;

	// 10. 파일 삭제
	int deleteFile(int reviewId) throws Exception;
	
	//11. 페이징 처리를 위해 전체 게시글 수를 구해준다.
	int getTotalHotplaceCount(Map<String, Object> param) throws SQLException;
}
