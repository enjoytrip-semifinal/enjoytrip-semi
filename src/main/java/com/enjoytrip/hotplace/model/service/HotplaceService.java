package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.hotplace.model.HotplaceFileInfoDto;
import com.enjoytrip.util.PageNavigation;

public interface HotplaceService {
	List<HotplaceDto> listHotplace(Map<String, String> map) throws SQLException;

	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;

	HotplaceDto getHotplaceById(int hotplaceId)throws Exception;

	int insertHotplace(HotplaceDto hotplace, String url[]) throws Exception;

	int updateHotplace(HotplaceDto hotplace) throws Exception;

	int updateHit(int hotplaceId) throws Exception;

	int deleteHotplace(int hotplaceId) throws Exception;

	int updateLike(int hotplaceId) throws Exception;

}
