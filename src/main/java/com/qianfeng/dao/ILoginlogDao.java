package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.entity.LoginLog;

public interface ILoginlogDao {
	
	/**
	 * 根据登录名查询登录日志
	 * @param map格式的信息
	 * @return 日志列表
	 */
	public List<LoginLog> findLoginlog(Map<String, Object> info);
	
	public int count();
	/**
	 * 添加登录日志
	 * @param log
	 */
	public void addLoginlog(LoginLog log);
}
