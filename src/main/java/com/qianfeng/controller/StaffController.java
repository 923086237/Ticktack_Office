package com.qianfeng.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qianfeng.entity.Department;
import com.qianfeng.entity.Staff;
import com.qianfeng.service.IDepartmentService;
import com.qianfeng.service.IStaffService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;


@Controller
public class StaffController {
	@Autowired
	private IStaffService staffService;
	
	@Autowired
	private IDepartmentService departService;
	
	@RequestMapping(value="findStaffAll", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> staffall(){
		Map<String, Object> map = new HashMap<>();
		try {
			List<Staff> staffs = staffService.findAllStaff();
			map.put("code", 0);
			map.put("data", staffs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
	
	@RequestMapping(value="findStaffInPage", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> staffpage(Integer page,Integer limit){
		Map<String, Object> map = new HashMap<>();
		try {
			PageBean<Staff> pageBean = staffService.findAllStaffByPage(page, limit);
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
	
	@RequestMapping(value="deleteStaff", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> staffdelete(String no){
		Map<String, Object> map = new HashMap<>();
		try {
			staffService.deleteStaff(no);
			map.put("code", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
		}
		return map;
	}
	
	@RequestMapping("/staffbatch.do")
	@ResponseBody
	public Map<String, Object> studentbatch(@RequestParam MultipartFile mFile){
		Map<String, Object> map = new HashMap<>();
		// 获取上传的文件的文件名
		String filename = mFile.getOriginalFilename();
		try {
			// 获取文件的输入流
			InputStream inputStream = mFile.getInputStream();
			
			// 解析exel文件，进行导入操作
			staffService.importExcel(filename, inputStream);
			map.put("code", 0);
			map.put("msg", "导入成功");
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("code", 1);
			map.put("msg", "导入失败");
		}
		return map;
	}
	
	@RequestMapping(value="/photoupload", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> photoupload(@RequestParam MultipartFile file, String name){
		Map<String, Object> map = new HashMap<>();
		System.out.println(name);
		// 获取上传的文件的文件名
		String filename = file.getOriginalFilename();
		
		String path = "D:/upload";
		File pathFile = new File(path);
		// 如果文件夹不存在创建
		if(!pathFile.exists()){
			pathFile.mkdir();
		}
		File file1 = new File(path, filename);
		// 将上传的文件保存到服务器指定位置
		
		try {
			file.transferTo(file1);
			map.put("code", 0);
		} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value="/addsta", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean addsta(String photo, String no, String name, String did, String sex, String email, String phone, String qq, String createdate) {
		JsonBean bean = new JsonBean();
		Department department = departService.findById(Integer.parseInt(did));
		Staff staff = new Staff();
		staff.setNo(no);
		staff.setName(name);
		staff.setDid(department.getId());
		staff.setEmail(email);
		staff.setPhone(phone);
		staff.setQq(qq);
		staff.setSex(sex);
		staff.setPhoto("media/images/" + photo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(createdate);
			staff.setCreatedate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		staffService.addStaff(staff);
		bean.setCode(0);
		bean.setMsg("");
		return bean;
	}
	
	@RequestMapping(value="/last", method=RequestMethod.GET)
	@ResponseBody
	public String last() {
		String string = staffService.findLast();
		if (string == null) {
			string = "qf000001";
			return string;
		} else {
			string = string.substring(2, 8);
			int i = Integer.parseInt(string);
			i += 1;
			String str = String.format("%06d", i);
			str = "qf" + str;
			return str;
		}
	}
}
