package com.enjoytrip.hotplace.model.service;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.hotplace.model.HotplaceFileInfoDto;
import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.hotplace.model.HotplaceReplyDto;
import com.enjoytrip.hotplace.model.mapper.HotplaceMapper;
import com.enjoytrip.hotplace.model.mapper.HotplaceReplyMapper;
import com.enjoytrip.util.PageNavigation;
import com.enjoytrip.util.SizeConstant;

import io.swagger.models.auth.In;

@Service
public class HotplaceServiceImpl implements HotplaceService {


	private HotplaceMapper hotplaceMapper;
	private HotplaceReplyMapper hotplaceReplyMapper;

	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper, HotplaceReplyMapper hotplaceReplyMapper) {
		// TODO Auto-generated constructor stub
		super();
		this.hotplaceMapper = hotplaceMapper;
		this.hotplaceReplyMapper = hotplaceReplyMapper;
	}
	

	@Override
	@Transactional
	public int writeHotplace(HotplaceDto hotplaceDto) throws Exception {

		int result = hotplaceMapper.writeHotplace(hotplaceDto);

		List<HotplaceFileInfoDto> fileInfos = hotplaceDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			hotplaceMapper.registerFile(hotplaceDto);
		}

		return result;
	}

	@Override
	@Transactional
	public int deleteHotplace(int hotplaceId, String path) throws Exception {
		
		
		// TODO Auto-generated method stub

		// 해당 게시글에 맞는 file을 먼저 가져와준다
		List<HotplaceFileInfoDto> fileList = hotplaceMapper.fileInfoList(hotplaceId);
		//삭제할 때 댓글도 함께 삭제해줘야 한다. -> 해당 게시글에 있는 댓글 정보를 불러온다.
		Map<String, String> map = new HashMap<String, String>();
		map.put("hotplaceid",String.valueOf(hotplaceId));
		List<HotplaceReplyDto> replyList = hotplaceReplyMapper.listReply(map);
		
		// 게시글에 존재하는 파일을 삭제해준다.
		if (!fileList.isEmpty()) {
			hotplaceMapper.deleteFile(hotplaceId);
		}
		
		if(!replyList.isEmpty()) {	//댓글이 존재한다.
			hotplaceReplyMapper.deleteReplyAll(hotplaceId);
		}
		// 게시글을 먼저 삭제해준다
		int result = hotplaceMapper.deleteHotplace(hotplaceId);

		for(HotplaceFileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
		
		return result;
	}

	@Override
	public int updateHotplace(HotplaceDto hotplaceDto) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.updateHotplace(hotplaceDto);
	}

	@Override
	@Transactional
	public int updateLike(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.updateLike(hotplaceId);
	}

	@Override
	public int updateHit(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.updateHit(hotplaceId);
	}

	@Override
	public HotplaceDto getHotplaceById(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		return hotplaceMapper.getHotplaceById(hotplaceId);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		//페이징 할 때 필요한 값이 뭐가 있을까? -> int sido, int gugun, int type and pgno = pagenumber
		//오맛 int로 설정해놓으면 null값이 있을 리가 없고 기본적으로 0이 들어간다.
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage =Integer.parseInt( map.get("pgno")==null?"1":map.get("pgno"));
		
		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		
		Map<String, Object> param = new HashMap<String, Object>();

		// ------------------------------------------------------------- 
		//int sido, int gugun, int type 가 만약 없다면? 임시로 0이라는 값을 넣어주고 이 값이 들어오면 전체 검색을 진행한다.

		int sido = Integer.parseInt( map.get("sido")==null?"0":map.get("sido"));
		
		int gugun = Integer.parseInt( map.get("gugun")==null?"0":map.get("gugun"));
		
		int type =  Integer.parseInt( map.get("type")==null?"0":map.get("type"));
		
		
		param.put("sido", sido);
		param.put("gugun", gugun);
		param.put("type",type);
		
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
	public List<HotplaceDto> listHotplace(Map<String, String> map) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>(); 
		
		param.put("sido", map.get("sido")==null?0:map.get("sido"));
		param.put("gugun", map.get("gugun")==null?0:map.get("gugun"));
		param.put("type", map.get("type")==null?0:map.get("type"));
		
		int pgNo =  Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return hotplaceMapper.listHotplace(param);
	}

}
