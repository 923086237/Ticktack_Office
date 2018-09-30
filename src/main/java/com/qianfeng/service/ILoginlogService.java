package com.qianfeng.service;



import com.qianfeng.entity.LoginLog;
import com.qianfeng.vo.PageBean;

public interface ILoginlogService {
	
	/**
	 * 登录名查询日志
	 * @param no
	 * @return
	 */
	public PageBean<LoginLog> findLoginlog(int page, int limit);
	
	
	/**
	 * 添加日志
	 * @param log
	 */
	public void addLog(LoginLog log);
}
