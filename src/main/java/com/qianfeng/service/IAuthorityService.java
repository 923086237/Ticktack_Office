package com.qianfeng.service;

import java.util.List;
import java.util.Map;

import com.qianfeng.entity.Authority;
import com.qianfeng.vo.AuBean;
import com.qianfeng.vo.PageBean;

public interface IAuthorityService {

	/**
	 * 根据 登录名 查询权限
	 * @param no 登录名
	 * @return
	 */
	public List<AuBean> findAuthorityByNo(String no);
	
	/**
	 * 查找所有权限
	 * @param map
	 * @return
	 */
	public List<Authority> findTitle(Map<String, Object> map);
	
	public List<Authority> findAllAuthService();
	
	public List<Authority> findRoleAuthService(Integer roid);
	
	public List<Authority> findRoleAuthServiceAll();
	
	public void deleteRoleAuthAllService(List<Integer> map);
	
	public void inserRoleAuthAllService(List<Integer> map);
	
	public void inserTotalAuthService(int rid,String aids);
	
	public PageBean<Authority> findAuthLimitService(int page, int limit);
	
	public void updateService(Authority authority);
	
	public Authority findByIdService(Integer id);
	
	public List<Authority> findByParentIdService();
	
	public void deleteRoleAuById(Integer id);
	
	public List<Authority> seletRoleAuById(Integer parentId);
	
	public void insertRoleAuByIdService(Authority authority);
}
