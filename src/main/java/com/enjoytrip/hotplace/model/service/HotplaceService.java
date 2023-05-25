package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.util.PageNavigation;

public interface HotplaceService {
	List<HotplaceDto> listHotplace(Map<String, String> map) throws SQLException;

	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;

	HotplaceDto getHotplaceById(int hotplaceId)throws Exception;

	int insertHotplace(HotplaceDto hotplace) throws Exception;

	int updateHotplace(HotplaceDto hotplace) throws Exception;

	int updateHit(int hotplaceId) throws Exception;

	int deleteHotplace(int hotplaceId) throws Exception;

	int likeHotplace(int hotplaceId) throws Exception;
	
	int hateHotplace(int hotplaceId) throws Exception;
	
	int getTop3() throws Exception;

}
