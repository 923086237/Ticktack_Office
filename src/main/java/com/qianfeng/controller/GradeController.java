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

import com.qianfeng.entity.Grade;
import com.qianfeng.service.IGradeService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class GradeController {
	@Autowired
	private IGradeService gradeService;
	
	@RequestMapping(value="findAllGrade", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAllGrade() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		try {
			List<Grade> list = gradeService.findAllGrade();
			map.put("code", 0);
			map.put("msg", null);
			map.put("data", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
	
	@RequestMapping(value="findGradeInPage", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> gradepage(Integer page,Integer limit){
		Map<String, Object> map = new HashMap<>();
		
		try {
			PageBean<Grade> pageBean = gradeService.findAllGradeAndCourse(page, limit);
			map.put("code", 0);
			map.put("data", pageBean.getPageInfos());
			map.put("count", pageBean.getCount());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 0);
		}
		
		return map;
	}
	
	@RequestMapping(value="deleteGrade", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gradedelete(int id){
		Map<String, Object> map = new HashMap<>();
		try {
			int ret = gradeService.deleteGradeById(id);
			if (ret == 0) {
				map.put("code", 1);
				map.put("msg", "班级下面还有对应的学生，不能删除");
			}else {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "删除失败");
		}
		return map;
	}
	
	@RequestMapping(value="updateGrade", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> gradeupdate(Grade grade){
		Map<String, Object> map = new HashMap<>();
		try {
			gradeService.updateGradeById(grade);
			map.put("code", 0);
			map.put("msg", "编辑成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
	
	@RequestMapping(value="/addGrade", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean addGrade(String name, String cid, String createdate, String week, String location) {
		
		Grade grade = new Grade();
		SimpleDateFormat sd = new SimpleDateFormat("yy-MM-dd");
		try {
			Date date = sd.parse(createdate);
			grade.setCreatedate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grade.setName(name);
		grade.setCid(Integer.parseInt(cid));
		grade.setFlag(1);
		grade.setWeek(Integer.parseInt(week));
		grade.setLocation(location);
		
		try {
			int ret = gradeService.addGrade(grade);
			if(ret == 1) {
				return JsonUtils.writeJosnInfo(0, "添加成功");
			}else {
				return JsonUtils.writeJosnInfo(1, "添加失败，该班级可能已经存在或者班级位置已经被占用");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(1, e.getMessage());
		}
		
		
	}
}
