package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.ILoginlogDao;
import com.qianfeng.entity.LoginLog;
import com.qianfeng.service.ILoginlogService;
import com.qianfeng.vo.PageBean;

@Service
public class LoginlogService implements ILoginlogService {

	@Autowired
	private ILoginlogDao logDao;

	@Override
	public void addLog(LoginLog log) {
		// TODO Auto-generated method stub
		try {
			logDao.addLoginlog(log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PageBean<LoginLog> findLoginlog(int page, int limit) {
		// TODO Auto-generated method stub
		PageBean<LoginLog> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		pageInfo.setPageSize(limit);
		int count = logDao.count();
		pageInfo.setCount(count);
		// 根据页面计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		
		List<LoginLog> list = logDao.findLoginlog(map);
		pageInfo.setPageInfos(list);
		return pageInfo;
	}

}
