package com.qianfeng.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IGradeDao;
import com.qianfeng.dao.IStaffDao;
import com.qianfeng.dao.IStudentDao;
import com.qianfeng.entity.Grade;
import com.qianfeng.entity.Staff;
import com.qianfeng.entity.Student;
import com.qianfeng.service.IStudentService;
import com.qianfeng.utils.IsXls;
import com.qianfeng.vo.PageBean;
@Service
public class StudentServiceImpl implements IStudentService{
	@Autowired
	private IStudentDao studentDao;
	@Autowired
	private IStaffDao staffDao;
	@Autowired
	private IGradeDao gradeDao;

	@Override
	public void studentadd(Student student) {
		// TODO Auto-generated method stub
		studentDao.studentadd(student);
	}

	@Override
	public PageBean<Student> findAllStudent(Integer page,Integer limit) {
		// TODO Auto-generated method stub
		PageBean<Student> pageInfo = new PageBean<>();
		
		pageInfo.setPageSize(limit);
		
		Map<String,Object> map = new HashMap<>();
		
		// 获取表中的记录总数
		int count = studentDao.count();
		// 设置总记录数
		pageInfo.setCount(count);
		int index = (page - 1) * pageInfo.getPageSize();
		
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		List<Student> list = studentDao.findAllStudent(map);
		pageInfo.setPageInfos(list);
		
		return pageInfo;
	}

	@Override
	public void studentdelete(String no) {
		// TODO Auto-generated method stub
		Student student = null;
		try {
			student = studentDao.findStudentByNo(no);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (student != null) {
			try {
				studentDao.deleteStudentByNo(no);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void importExcel(String fileName, InputStream inputStream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
	



}
