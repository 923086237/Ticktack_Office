package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.entity.Users;

public interface IUsersDao {
	
	public List<Users> findAllUsers(Map<String, Object> info); 
   
	public Users findByName(String no);

	public List<String> findRoleByUname(String name);

	public List<String> findPermitByUname(String name);
	
	public int count();
	
	public void deleteById(Integer id);
	
	public Users findById(Integer id);
	
	public void updateUser(Users user);
	
	public List<Users> findByNoOrFlag(Map<String, Object> info);
	
}