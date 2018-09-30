package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.ICheckDao;
import com.qianfeng.dao.ICourseDao;
import com.qianfeng.entity.Check;
import com.qianfeng.entity.Student;
import com.qianfeng.service.ICheckService;
import com.qianfeng.vo.PageBean;
@Service
public class CheckServiceImpl implements ICheckService{
	@Autowired
	private ICheckDao checkDao;

	@Override
	public PageBean<Check> findAllCheck(Integer page,Integer limit,String startno) {
		// TODO Auto-generated method stub
		PageBean<Check> pageInfo = new PageBean<>();
		
		pageInfo.setPageSize(limit);
		
		Map<String,Object> map = new HashMap<>();
		
		// 获取表中的记录总数
		int count = checkDao.count();
		// 设置总记录数
		pageInfo.setCount(count);
		int index = (page - 1) * pageInfo.getPageSize();
		map.put("startno", startno);
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Check> list = checkDao.findAllCheck(map);
		pageInfo.setPageInfos(list);;
		
		return pageInfo;
	}

}
