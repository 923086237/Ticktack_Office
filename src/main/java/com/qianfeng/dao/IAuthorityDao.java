package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.entity.Authority;
import com.qianfeng.vo.AuBean;
import com.qianfeng.vo.MapBean;


public interface IAuthorityDao {
    
	/**
	 * 根据no 查询用户权限
	 * @param no 用户登录名
	 * @return
	 */
	public List<AuBean> findAuthorityByNo(String no);
	
	/**
	 * 根据查询父类title列表
	 * @param id
	 * @return
	 */
	public List<Authority> findByParentid();
	
	/**
	 * 返回一个map格式的列表
	 * @param map map的键值对
	 * @return
	 */
	public List<Authority> findTitle(Map<String, Object> map);
	
	/**
	 * 查询所有一级权限列表
	 * @param username
	 * @return
	 */
	public List<Authority> findAllAuth(String username);
	
	/**
	 * 根据ro_id查找二级权限列表
	 * @param ro_id
	 * @return
	 */
	public List<Authority> findRoleAuth(int ro_id);
	
	/**
	 * 查找所有二级权限的列表
	 * @return
	 */
	public List<Authority> findRoleAllAuth();
	
	/**
	 * 根据父类id查找权限
	 * @param parentId
	 * @return
	 */
	public List<Authority> findRoleAuById(int parentId);
	
	/**
	 * 根据id查找权限
	 * @param id
	 * @return
	 */
	public Authority findById(Integer id);
	
	/**
	 * 根据aid和rid删除角色权限关联关系
	 * @param map
	 */
	public void deleteRoleAuthAll(List<Integer> map);
	
	/**
	 * 删除角色权限关联关系
	 * @param rid
	 */
	public void deleteRoleAll(int rid);
	
	/**
	 * 根据id删除权限
	 * @param id
	 */
	public void deleteRoleAuById(int id);
	
	/**
	 * 增加角色权限关联关系
	 * @param mapBeans
	 */
	public void addRoleAuthAll(List<MapBean> mapBeans);
	
	/**
	 * 更新权限
	 * @param auth
	 */
	public void update(Authority auth);
	
	/**
	 * 根据增加权限
	 * @param auth
	 */
	public void addRoleAuById(Authority auth);
	public void addRoleAuByIdTotalId(Authority auth);
	
	//分页用的
	public int count();
	public int countByParent();
	
	public List<Authority> findAuthLimit(Map<String, Object> map);

}















