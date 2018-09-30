package com.qianfeng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IRoleDao;
import com.qianfeng.entity.Role;
import com.qianfeng.service.IRoleService;
import com.qianfeng.vo.MapBean2;

@Service
public class RoleService implements IRoleService{

	@Autowired
	private IRoleDao rDao;
	
	@Override
	public List<Role> findRoleAll() {
		// TODO Auto-generated method stub
		
		return rDao.findRoleAll();
	}

	@Override
	public void addRoleService(Integer id, String rid) {
		// TODO Auto-generated method stub
		rDao.deleteRole(id);
		String[] ri = rid.split(",");
		
		List<MapBean2> mapBeans = new ArrayList<>();
		for (String rd : ri) {
			MapBean2 mapBean = new MapBean2();
			mapBean.setId(id);
			mapBean.setRid(rd);
			mapBeans.add(mapBean);
		}
		rDao.addRole(mapBeans);
	}

}
