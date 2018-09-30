package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IUsersDao;
import com.qianfeng.entity.Users;
import com.qianfeng.service.IUsersService;
import com.qianfeng.utils.MD5Utils;
import com.qianfeng.vo.PageBean;

@Service
public class UsersService implements IUsersService{

	@Autowired
	private IUsersDao uDao;
	
	// 登录 用不上。。
	@Override
	public Users loginUsers(String no, String password, Integer flag) {
		// TODO Auto-generated method stub
		
		Users user = uDao.findByName(no);
		if(user.getPassword().equals(MD5Utils.getMD5(password))) {
			return user;
		};

		return null;
	}

	// 根据登录名查找用户
	@Override
	public Users findUserByNo(String no) {
		// TODO Auto-generated method stub
		return uDao.findByName(no);
	}

	// 删除用户
	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		uDao.deleteById(id);
	}

	// 更新用户
	@Override
	public void updateUser(Integer id, String name) {
		// TODO Auto-generated method stub
		Users user = new Users();
		user.setId(id);
		user.setName(name);
		uDao.updateUser(user);	
	}

	// 分页之查询所有
	@Override
	public PageBean<Users> findAllUser(int page, int limit) {
		// TODO Auto-generated method stub
		
		PageBean<Users> pageInfo = new PageBean<Users>();
		pageInfo.setCurrentPage(page);
		pageInfo.setPageSize(limit);
		int count = uDao.count();
		pageInfo.setCount(count);
		
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Users> list = uDao.findAllUsers(map);
		pageInfo.setPageInfos(list);
		
		return pageInfo;
	}

	@Override
	public PageBean<Users> findByNoOrFlag(int page, int limit, String no, Integer flag, String name, String ro_id) {
		// TODO Auto-generated method stub
		
		PageBean<Users> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		pageInfo.setPageSize(limit);
		int count = uDao.count();
		pageInfo.setCount(count);
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		map.put("no", no);
		map.put("flag", flag);
		map.put("name", name);
		map.put("ro_id", ro_id);
		List<Users> list = uDao.findByNoOrFlag(map);
		System.out.println("duoshao"+list.size());
		pageInfo.setPageInfos(list);
		
		return pageInfo;
	}

	@Override
	public Users findByUserId(Integer id) {
		// TODO Auto-generated method stub
		return uDao.findById(id);
	}


}
