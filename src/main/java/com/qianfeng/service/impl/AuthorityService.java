package com.qianfeng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qianfeng.dao.IAuthorityDao;
import com.qianfeng.entity.Authority;
import com.qianfeng.service.IAuthorityService;
import com.qianfeng.vo.AuBean;
import com.qianfeng.vo.MapBean;
import com.qianfeng.vo.PageBean;

@Service
public class AuthorityService implements IAuthorityService{
	
	@Autowired
	private IAuthorityDao aDao;

	@Override
	public List<AuBean> findAuthorityByNo(String no) {
		// TODO Auto-generated method stub
		List<AuBean> alist = null;
		try {
			alist = aDao.findAuthorityByNo(no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alist;
	}

	@Override
	public List<Authority> findTitle(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		List<Authority> list = null;
		try {
			list = aDao.findTitle(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return list;
	}

	@Override
	public List<Authority> findAllAuthService() {
		// TODO Auto-generated method stub
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String username = (String) request.getSession(false).getAttribute("no");
		return aDao.findAllAuth(username);
	}

	@Override
	public List<Authority> findRoleAuthService(Integer roid) {
		// TODO Auto-generated method stub
		return aDao.findRoleAuth(roid);
	}

	@Override
	public List<Authority> findRoleAuthServiceAll() {
		// TODO Auto-generated method stub
		return aDao.findRoleAllAuth();
	}

	@Override
	public void deleteRoleAuthAllService(List<Integer> map) {
		// TODO Auto-generated method stub
		aDao.deleteRoleAuthAll(map);
	}

	@Override
	public void inserRoleAuthAllService(List<Integer> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserTotalAuthService(int rid, String aids) {
		// TODO Auto-generated method stub
		aDao.deleteRoleAll(rid);
		String[] ids = aids.split(",");
		Map<String, Object> map = new HashMap<>();
		List<MapBean> mapBeans = new ArrayList<>();
		for (String aid : ids) {
			MapBean mapBean = new MapBean();
			mapBean.setAid(aid);
			mapBean.setRid(rid);
			mapBeans.add(mapBean);
		}
		aDao.addRoleAuthAll(mapBeans);
	}

	@Override
	public PageBean<Authority> findAuthLimitService(int page, int limit) {
		// TODO Auto-generated method stub
		PageBean<Authority> pageInfo = new PageBean<>();
		pageInfo.setCurrentPage(page);
		pageInfo.setPageSize(limit);
		int count = aDao.count();
		pageInfo.setCount(count);
		
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Authority> list = aDao.findAuthLimit(map);
		pageInfo.setPageInfos(list);
		return pageInfo;
	}

	@Override
	public void updateService(Authority authority) {
		// TODO Auto-generated method stub
		aDao.update(authority);
	}

	@Override
	public Authority findByIdService(Integer id) {
		// TODO Auto-generated method stub
		return aDao.findById(id);
	}

	@Override
	public List<Authority> findByParentIdService() {
		// TODO Auto-generated method stub
		return aDao.findByParentid();
	}

	@Override
	public void deleteRoleAuById(Integer id) {
		// TODO Auto-generated method stub
		List<Authority> list = aDao.findRoleAuById(id);
		if(list.size() > 0) {
			throw new RuntimeException("此权限下有子权限，无法删除");
		}else {
			aDao.deleteRoleAuById(id);
		}
		
	}

	@Override
	public List<Authority> seletRoleAuById(Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRoleAuByIdService(Authority authority) {
		// TODO Auto-generated method stub
		if(authority.getId() == 0) {
			int countByParent = aDao.countByParent();
			authority.setId(countByParent);
			aDao.addRoleAuById(authority);
		}else {
			aDao.addRoleAuById(authority);
		}
	}
	
	
	
	
}
