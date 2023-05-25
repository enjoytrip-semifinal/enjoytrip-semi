package com.enjoytrip.hotplace.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@Service
public class HotplaceServiceImpl implements HotplaceService {

	@Autowired
	private final UserService userService;
	private HotplaceMapper hotplaceMapper;
	private HotplaceReplyMapper hotplaceReplyMapper;

	public HotplaceServiceImpl(HotplaceMapper hotplaceMapper, HotplaceReplyMapper hotplaceReplyMapper,
			UserService userService) {
		this.userService = userService;
		this.hotplaceMapper = hotplaceMapper;
		this.hotplaceReplyMapper = hotplaceReplyMapper;
	}

	@Override
	@Transactional
	public int insertHotplace(HotplaceDto hotplace) throws Exception {

		hotplace.setUserId(userService.getUser_idbyId());

		int result = hotplaceMapper.insertHotplace(hotplace);
		
		if (hotplace.getFileList() != null &&hotplace.getFileList().size()>0) { // 올릴 파일이 존재한다면?
			// 파일 정보가 있다
			System.out.println(hotplace.getFileList().size());
			System.out.println(Arrays.deepToString(hotplace.getFileList().toArray()));
			int file = hotplaceMapper.registerFile(hotplace.getFileList());
			if(file>0) System.out.println("야~호~");
		}

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

		if (fileList != null || !fileList.isEmpty()) {
			hotplaceMapper.deleteFileAll(hotplaceId);
			System.out.println("관련 파일이 모두 삭제되었습니다.");
		}
		if (replyList != null || !replyList.isEmpty()) {
			hotplaceReplyMapper.deleteReply(hotplaceId);
			System.out.println("관련 댓글이 모두 삭제되었습니다.");
		}

		// 파일과 댓글을 모두 삭제한후 게시글을 삭제해준다.
		int result = hotplaceMapper.deleteHotplace(hotplaceId);

		return result;
	}

	@Override
	public int updateHotplace(HotplaceDto hotplaceDto) throws Exception {
		// TODO Auto-generated method stub

		// 그럼 일단 원래 있던 파일들을 다 삭제한다.
//		int countDelFile = hotplaceMapper.deleteFileAll(hotplaceDto.getHotplaceId());
//		System.out.println("저장된 파일 "+countDelFile+"개 삭제 완료");
		// 그리고 파일 빼고 다 업데이트 해준다
		int updateCount = hotplaceMapper.updateHotplace(hotplaceDto);
		// 그리고 지금 얻어온 파일 이름을 다시 넣어준다.
//		int fileCount = hotplaceMapper.registerFile(hotplaceDto.getFileList(), hotplaceDto.getHotplaceId());
//		System.out.println("새로 등록된 파일 "+fileCount+"개 등록 완료");
		return updateCount;
	}

	@Override
	@Transactional
	public int likeHotplace(int hotplaceId) throws SQLException {
		// TODO Auto-generated method stub
		/* 좋아요를 눌러 줬으니까 user_like_hotplace에 user_id와 hotplace_id를 추가해줘야한다 */
		int userid = userService.getUser_idbyId(); // int userid를 받아 온다.
		Map<String, Object> map = new HashMap<>();

		map.put("userId", userid);
		map.put("hotplaceId", hotplaceId);

		int add = hotplaceMapper.userLikeHotplace(map);

		if (add > 0)
			System.out.println("add " + userid + " and " + hotplaceId + " into user_like_hotplace table");

		return hotplaceMapper.likeHotplace(hotplaceId);
	}

	@Override
	@Transactional
	public int hateHotplace(int hotplaceId) throws Exception {
		/* 좋아요를 취소 했으니까 user_like_hotplace에 user_id와 hotplace_id를 추가해줘야한다 */

		int userid = userService.getUser_idbyId(); // int userid를 받아 온다.
		Map<String, Object> map = new HashMap<>();

		map.put("userId", userid);
		map.put("hotplaceId", hotplaceId);

		int del = hotplaceMapper.userHateHotplace(map);
		if (del > 0)
			System.out.println("del " + userid + " and " + hotplaceId + " into user_like_hotplace table");

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

		param.put("key", map.get("key") == null ? "" : map.get("key"));
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		param.put("season", map.get("season") == null ? "0" : map.get("season"));
		param.put("type", map.get("type") == null ? "0" : map.get("type"));

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

		// 검색 조건은 주소검색, 계절검색, 작성자 검색이 있다!! 계절은 Int이고 주소는 String, 작성자는 String검색이다!!
		// 검색조건 주소 넣이
		param.put("key", map.get("key") == null ? "" : map.get("key"));
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		param.put("season", map.get("season") == null ? "0" : map.get("season"));
		param.put("type", map.get("type") == null ? "0" : map.get("type"));

		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;

		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return hotplaceMapper.listHotplace(param);
	}

	@Override
	public int getTop3() throws Exception {
		// TODO Auto-generated method stub
		return hotplaceMapper.getTop3();
	}

}
