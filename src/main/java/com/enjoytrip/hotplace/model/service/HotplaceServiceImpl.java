package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.hotplace.model.HotplaceDto;
import com.enjoytrip.hotplace.model.HotplaceFileInfoDto;
import com.enjoytrip.hotplace.model.HotplaceReplyDto;
import com.enjoytrip.hotplace.model.mapper.HotplaceMapper;
import com.enjoytrip.hotplace.model.mapper.HotplaceReplyMapper;
import com.enjoytrip.user.model.service.UserService;
import com.enjoytrip.util.PageNavigation;
import com.enjoytrip.util.SizeConstant;

@Service
public class HotplaceServiceImpl implements HotplaceService {

	private final UserService userService;
	private HotplaceMapper hotplaceMapper;
	private HotplaceReplyMapper hotplaceReplyMapper;

	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper, HotplaceReplyMapper hotplaceReplyMapper, UserService userService) {
		this.userService = userService;
		this.hotplaceMapper = hotplaceMapper;
		this.hotplaceReplyMapper = hotplaceReplyMapper;
	}

	@Override
	@Transactional
	public int insertHotplace(HotplaceDto hotplaceDto, String url[]) throws Exception {

		if (url != null && url.length > 0) { // 올릴 파일이 존재한다면?
			// 파일 정보가 있다면

			// 1. url을 리스트 형태로 만들어준다.
			List<HotplaceFileInfoDto> fileList = new ArrayList<HotplaceFileInfoDto>();
			for (String str : url) {
				HotplaceFileInfoDto file = new HotplaceFileInfoDto();
				file.setHotplaceId(hotplaceDto.getHotplaceId());
				file.setUrl(str);
				fileList.add(file);
			}

			hotplaceMapper.registerFile(fileList);
		}

		int result = hotplaceMapper.insertHotplace(hotplaceDto);

		return result;
	}

	@Override
	@Transactional
	public int deleteHotplace(int hotplaceId) throws Exception {
		System.out.println(hotplaceId);

		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(hotplaceId));
		// 해당 게시글에 있는 file을 먼저 제거해준다.
		List<HotplaceFileInfoDto> fileList = hotplaceMapper.fileInfoList(hotplaceId);
		List<HotplaceReplyDto> replyList = hotplaceReplyMapper.listReply(map);

		if (fileList!=null || !fileList.isEmpty()) {
			hotplaceMapper.deleteFileAll(hotplaceId);
			System.out.println("관련 파일이 모두 삭제되었습니다.");
		}
		if(replyList!=null || !replyList.isEmpty()) {
			hotplaceReplyMapper.deleteReply(hotplaceId);
			System.out.println("관련 댓글이 모두 삭제되었습니다.");
		}

		// 파일과 댓글을 모두 삭제한후 게시글을 삭제해준다.
		int result = hotplaceMapper.deleteHotplace(hotplaceId);
		

		return result;
	}

	@Override
	public int updateHotplace(HotplaceDto hotplaceDto) throws SQLException {
		// TODO Auto-generated method stub
		
		return hotplaceMapper.updateHotplace(hotplaceDto);
	}

	@Override
	@Transactional
	public int likeHotplace(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		/*좋아요를 눌러 줬으니까 user_like_hotplace에 user_id와 hotplace_id를 추가해줘야한다*/
		int userid = userService.getUser_idbyId();	//int userid를 받아 온다.
		Map<String , Object> map = new HashMap<>();
		map.put("userId", userid);
		map.put("hotplaceId", hotplaceId);
		int add = hotplaceMapper.userLikeHotplace(map);
		
		if(add>0)
			System.out.println("add "+userid+" and "+hotplaceId+" into user_like_hotplace table");
		
		return hotplaceMapper.likeHotplace(hotplaceId);
	}
	
	@Override
	@Transactional
	public int hateHotplace(int hotplaceId) throws Exception {
		/*좋아요를 취소 했으니까 user_like_hotplace에 user_id와 hotplace_id를 추가해줘야한다*/
		
		int userid = userService.getUser_idbyId();	//int userid를 받아 온다.
		Map<String , Object> map = new HashMap<>();
		map.put("userId", userid);
		map.put("hotplaceId", hotplaceId);
		int del = hotplaceMapper.userLikeHotplace(map);
		if(del>0)
			System.out.println("del "+userid+" and "+hotplaceId+" into user_like_hotplace table");
		
		return hotplaceMapper.hateHotplace(hotplaceId);
	}

	@Override
	@Transactional
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
		// 페이징 할 때 필요한 값이 뭐가 있을까? -> int sido, int gugun, int type and pgno = pagenumber
		// 오맛 int로 설정해놓으면 null값이 있을 리가 없고 기본적으로 0이 들어간다.
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);

		Map<String, Object> param = new HashMap<String, Object>();

		// -------------------------------------------------------------
		// int sido, int gugun, int type 가 만약 없다면? 임시로 0이라는 값을 넣어주고 이 값이 들어오면 전체 검색을
		// 진행한다.

		int sido = 0;
		if(map.get("sido").equals("") || map.get("sido").isEmpty()) {
			sido=0;
		}else {
			sido = Integer.parseInt(map.get("sido"));
		}
		
		int gugun = 0;
		if(map.get("gugun").equals("") || map.get("gugun").isEmpty()) {
			gugun=0;
		}else {
			gugun = Integer.parseInt(map.get("sido"));
		}
		
		int type = 0;
		if(map.get("type").equals("") || map.get("type").isEmpty()) {
			type=0;
		}else {
			type = Integer.parseInt(map.get("sido"));
		}
		int season = 0;
		if(map.get("season").equals("") || map.get("season").isEmpty()) {
			season=0;
		}else {
			season = Integer.parseInt(map.get("sido"));
		}
	



		param.put("sido", sido);
		param.put("gugun", gugun);
		param.put("type", type);
		param.put("season", season);

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

		param.put("sido", map.get("sido") == null ? 0 : map.get("sido"));
		param.put("gugun", map.get("gugun") == null ? 0 : map.get("gugun"));
		param.put("type", map.get("type") == null ? 0 : map.get("type"));
		param.put("season", map.get("season") == null ? 0 : map.get("season"));

		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;

		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return hotplaceMapper.listHotplace(param);
	}

	



}
