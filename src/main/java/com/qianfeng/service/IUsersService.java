package com.qianfeng.service;

import com.qianfeng.entity.Users;
import com.qianfeng.vo.PageBean;

public interface IUsersService {

	public Users loginUsers(String no, String password, Integer flag);
	
	public Users findUserByNo(String no);
	
	public Users findByUserId(Integer id);
	
	public void deleteUser(Integer id);
	
	public void updateUser(Integer id, String name);
	
	public PageBean<Users> findAllUser(int page, int limit);
	
	public PageBean<Users> findByNoOrFlag(int page, int limit, String no, Integer flag, String name, String ro_id);
}
