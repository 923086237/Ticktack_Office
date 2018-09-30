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

import com.qianfeng.entity.Course;
import com.qianfeng.service.ICourseService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class CourseController {
	@Autowired
	private ICourseService courseService;
	
	@RequestMapping(value="findAllCourse", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> courseall(){
		Map<String, Object> map	= new HashMap<>();
		
		try {
			List<Course> list = courseService.findAllCourse();
			map.put("code", 0);
			map.put("data", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
	
	
	@RequestMapping(value="findCourseInPage", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> coursepage(Integer page,Integer limit){
		Map<String, Object> map	= new HashMap<>();
		try {
			PageBean<Course> pageBean = courseService.findAllCourseByPage(page, limit);
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
	
	@RequestMapping(value="deleteCourse", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletecourse(Integer id){
		Map<String, Object> map	= new HashMap<>();
		try {
			int ret = courseService.deleteCourse(id);
			if (ret == 0) {
				map.put("code", 1);
				map.put("msg", "该课程下面存在修这门课程的班级，无法删除");
			}else {
				map.put("code", 0);
				map.put("msg", "删除成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
	
	
	@RequestMapping(value="addCourse", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean addCourse(String name, String createdate, String week, String type) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Course course = new Course();
		Date date;
		try {
			date = sf.parse(createdate);
			course.setCreatedate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = Integer.parseInt(week);
		int wt = Integer.parseInt(type);
		
		course.setFlag(1);
		course.setName(name);
		course.setType(wt);
		course.setWeek(w);
		
		int ret = courseService.addCourse(course);
		if(ret == 0) {
			return JsonUtils.writeJosnInfo(1, "添加失败");
		}else {
			return JsonUtils.writeJosnInfo(0, "添加成功");
		}
		
	}
	
	@RequestMapping(value="updateCourse", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean updateCourse(String id, String name, String createdate, String week, String type) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Course course = new Course();
		Date date;
		try {
			date = sf.parse(createdate);
			course.setCreatedate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = Integer.parseInt(week);
		int type1 = 0;
		if(type.equals("精品") || type.equals("2")) {
			 type1 = 2;
		}else if(type.equals("普通") || type.equals("1")) {
			 type1 = 1;
		}else if(type.equals("业余") || type.equals("3")) {
			 type1 = 3;
		}
		
		course.setId(Integer.parseInt(id));
		course.setFlag(1);
		course.setName(name);
		course.setType(type1);
		course.setWeek(w);
		try {
			courseService.updateCourse(course);
			//System.out.println("updatecourse: " + course.getName() + " " + course.getType() + " " + course.getWeek());
			return JsonUtils.writeJosnInfo(0, "修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(1, "修改失败");
		}
	}

}
