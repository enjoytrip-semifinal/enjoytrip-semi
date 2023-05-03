package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.hotplace.model.FileInfoDto;
import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.hotplace.model.mapper.HotplaceMapper;
import com.enjoytrip.util.PageNavigation;
import com.enjoytrip.util.SizeConstant;

@Service
public class HotplaceServiceImpl implements HotplaceService {

	private HotplaceMapper hotplaceMapper;

	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper) {
		// TODO Auto-generated constructor stub
		super();
		this.hotplaceMapper = hotplaceMapper;
	}

	@Override
	@Transactional
	public int writeHotplace(HotplaceDto hotplaceDto) throws Exception {

//		System.out.println("글입력 전 dto : " + hotplaceDto);
		int result = hotplaceMapper.writeHotplace(hotplaceDto);
//		System.out.println("글입력 후 dto : " + hotplaceDto);

		List<FileInfoDto> fileInfos = hotplaceDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			hotplaceMapper.registerFile(hotplaceDto);
		}

		return result;
	}

	@Override
	@Transactional
	public int deleteHotplace(int reviewId, String path) throws Exception {
		// TODO Auto-generated method stub

		// 해당 게시글에 맞는 file을 먼저 가져와준다
		List<FileInfoDto> fileList = hotplaceMapper.fileInfoList(reviewId);

		// 게시글을 먼저 삭제해준다
		int result = hotplaceMapper.deleteHotplace(reviewId);

		// 게시글에 존재하는 파일을 삭제해준다.
		hotplaceMapper.deleteFile(reviewId);

		return result;
	}

	@Override
	public int modifyHotplace(HotplaceDto hotplaceDto) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.modifyHotplace(hotplaceDto);
	}

	@Override
	public int updateLike(int reviewId) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.updateLike(reviewId);
	}

	@Override
	public int updateHit(int reviewId) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.updateHit(reviewId);
	}

	/*
	 * @Override public List<HotplaceDto> listHotplace(Map<String, Integer> map)
	 * throws SQLException { // TODO Auto-generated method stub
	 * 
	 * Map<String, Object> param = new HashMap<String, Object>(); if (map.isEmpty())
	 * { param.put("sido", 0); param.put("gugun", 0); param.put("type", 0); }
	 * 
	 * // -------------------------------------------------------------yellow!! else
	 * { int sido = map.get("sido"); int gugun = map.get("gugun"); int type =
	 * map.get("type"); param.put("sido", sido); param.put("gugun", gugun);
	 * param.put("type", type); } //
	 * -------------------------------------------------------------
	 * 
	 * int pgNo = map.get("pgno") == 0 ? 1 : map.get("pgno"); int start = pgNo *
	 * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE; param.put("start", start);
	 * param.put("listsize", SizeConstant.LIST_SIZE);
	 * 
	 * return hotplaceMapper.listHotplace(param); }
	 */

	@Override
	public HotplaceDto getHotplace(int reviewId) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.getHotplace(reviewId);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, Integer> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = map.get("pgno");

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();

		// -------------------------------------------------------------yellow!!
		int sido = map.get("sido");
		int gugun = map.get("gugun");
		int type = map.get("type");
		param.put("sido", sido);
		param.put("gugun", gugun);
		param.put("type", type);
		// -------------------------------------------------------------

		int totalCount = hotplaceMapper.getTotalHotplaceCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public List<HotplaceDto> listHotplace(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Integer> param = new HashMap<>();

		Integer sido = map.get("sido")==null ? 0 :Integer.parseInt( map.get("sido"));
		Integer gugun = map.get("gugun") == null ? 0 : Integer.parseInt( map.get("gugun"));
		Integer type = map.get("type") == null ? 0 :Integer.parseInt( map.get("type"));

		param.put("sido", sido);
		param.put("gugun", gugun);
		param.put("type", type);

		int pgNo = map.get("pgno") == null ? 1 :Integer.parseInt( map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return hotplaceMapper.listHotplace(param);
	}

}
