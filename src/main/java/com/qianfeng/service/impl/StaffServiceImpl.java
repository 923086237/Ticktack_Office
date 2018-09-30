package com.qianfeng.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IDepartmentDao;
import com.qianfeng.dao.IStaffDao;
import com.qianfeng.entity.Department;
import com.qianfeng.entity.Grade;
import com.qianfeng.entity.Staff;
import com.qianfeng.entity.Student;
import com.qianfeng.service.IStaffService;
import com.qianfeng.utils.IsXls;
import com.qianfeng.vo.PageBean;
@Service
public class StaffServiceImpl implements IStaffService{
	@Autowired
	private IStaffDao staffDao;
	@Autowired
	private IDepartmentDao departmentDao;

	@Override
	public List<Staff> findAllStaff() {
		// TODO Auto-generated method stub
		List<Staff> list = null;
		try {
			list = staffDao.findAllStaff();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageBean<Staff> findAllStaffByPage(Integer page, Integer limit) {
		// TODO Auto-generated method stub
		PageBean<Staff> pageInfo = new PageBean<>();
		
		pageInfo.setPageSize(limit);
		
		Map<String,Object> map = new HashMap<>();
		
		// 获取表中的记录总数
		int count = staffDao.count();
		// 设置总记录数
		pageInfo.setCount(count);
		int index = (page - 1) * pageInfo.getPageSize();
		
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Staff> list = staffDao.findAllStaffByPage(map);
		
		pageInfo.setPageInfos(list);
		
		return pageInfo;
	}

	@Override
	public void deleteStaff(String no) {
		// TODO Auto-generated method stub
		try {
			Staff staff = staffDao.findStaffByNo(no);
			if (staff.getFlag() == 1) {
				staffDao.updateStaffFlagByNo(no);
			} else {
				staffDao.deleteStaffByNo(no);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	public void updateStaff(Staff staff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importExcel(String fileName, InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStaff(Staff staff) {
		// TODO Auto-generated method stub
		try {
			staffDao.addStaff2(staff);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String findLast() {
		String string = null;
		try {
			string = staffDao.findLast();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return string;
	}
}
