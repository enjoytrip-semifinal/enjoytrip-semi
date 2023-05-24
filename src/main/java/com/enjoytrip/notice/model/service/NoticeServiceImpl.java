package com.enjoytrip.notice.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enjoytrip.notice.model.NoticeDto;
import com.enjoytrip.notice.model.NoticeFileInfoDto;
import com.enjoytrip.notice.model.mapper.NoticeMapper;
import com.enjoytrip.util.PageNavigation;
import com.enjoytrip.util.SizeConstant;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeMapper noticeMapper;

	@Autowired
	public NoticeServiceImpl(NoticeMapper noticeMapper) {
		super();
		this.noticeMapper = noticeMapper;
	}

	@Override
	public List<NoticeDto> listNotice(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");

		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));

		int pgNo = Integer.parseInt(map.get("pgno"));
		int start = (pgNo - 1) * SizeConstant.LIST_SIZE;

		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return noticeMapper.listNotice(param);
	}

	@Override
	public Map<String, Object> getNotice(int noticeid) throws Exception {
		Map<String, Object> serviceMap = new HashMap<>();
		
		serviceMap.put("notice", noticeMapper.getNotice(noticeid));
		serviceMap.put("files", noticeMapper.getFiles(noticeid));
		
		return serviceMap;
	}

	@Override
	@Transactional
	public int write(NoticeDto notice) throws Exception {
		int result = noticeMapper.write(notice);
		
		List<String> uploadFiles = notice.getFileInfos();
		
		if (notice.getFileInfos() != null && notice.getFileInfos().size() > 0) {
			List<NoticeFileInfoDto> files = new ArrayList<>();
			for (String fileName : uploadFiles) {
				NoticeFileInfoDto file = new NoticeFileInfoDto();
				file.setFileUrl(fileName);
				files.add(file);
				System.out.println("efefef");
			}
			
			noticeMapper.registFiles(files);
		}
		
		return result;
	}

	@Override
	public int delete(int noticeid) throws Exception {
		return noticeMapper.delete(noticeid);
	}

	@Override
	public int modify(NoticeDto notice) throws Exception {
		return noticeMapper.modify(notice);
	}

	@Override
	public int updateNoticeHit(int noticeid) throws Exception {
		return noticeMapper.updateNoticeHit(noticeid);
	}

	@Override
	public PageNavigation makePageNav(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);

		Map<String, Object> param = new HashMap<>();
		String key = map.get("key");

		if ("userid".equals(key))
			key = "user_id";

		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));

		int totalCount = noticeMapper.getTotalNoticeCount(param);
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
}
