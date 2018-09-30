package com.qianfeng.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Department;
import com.qianfeng.service.IDepartmentService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class DepartmentController {
	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping(value="findDepartInPage", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> departpage(Integer page,Integer limit){
		Map<String, Object> map = new HashMap<>();
		
		try {
			PageBean<Department> pageBean = departmentService.findAllDepartment(page, limit);
			map.put("code", 0);
			map.put("count", pageBean.getCount());
			map.put("data", pageBean.getPageInfos());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		
		return map;
	}
	
	@RequestMapping(value="deleteDepart", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> departdelete(int id){
		Map<String, Object> map = new HashMap<>();
		
		try {
			int ret = departmentService.deleteDepartmentById(id);
			if (ret == 1) {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}else {
				map.put("code", 1);
				map.put("msg", "部门下面还有员工，无法删除");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}
	
	@RequestMapping(value="updateDepart", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> departupdate(String id, String name){
		Department  dept = new Department();
		dept.setId(Integer.parseInt(id));
		dept.setName(name);
		Map<String, Object> map = new HashMap<>();
		try {
			departmentService.updateDepartment(dept);
			map.put("code", 0);
			map.put("msg", "编辑成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "编辑失败");
		}
		return map;
	}
	
	@RequestMapping(value="addDepart", method=RequestMethod.POST)
	@ResponseBody	
	public JsonBean addDept(String name, String Createtime) {
		
		Department dept = new Department();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		dept.setFlag(1);
		dept.setName(name);
		try {
			Date date = sd.parse(Createtime);
			dept.setCreatetime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			int ret = departmentService.addDepartment(dept);
			if(ret == 1) {
				return JsonUtils.writeJosnInfo(0, "添加成功");
			}else {
				return JsonUtils.writeJosnInfo(1, "该部门已存在，不能添加");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(1, e.getMessage());
		}
		
	}
	
	@RequestMapping(value="findAllDepart", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> departall(){
		Map<String, Object> map = new HashMap<>();
		try {
			List<Department> list = departmentService.findAllDepart();
			map.put("code", 0);
			map.put("data", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
}
