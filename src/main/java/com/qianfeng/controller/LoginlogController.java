package com.qianfeng.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.LoginLog;
import com.qianfeng.service.ILoginlogService;
import com.qianfeng.vo.PageBean;

@Controller
public class LoginlogController {
	
	@Autowired
	private ILoginlogService logService;
	
	@RequestMapping(value="findByLoginlog", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByLoginlog(int page, int limit){
		Map<String, Object> map = new HashMap<>();
		PageBean<LoginLog> pageInfo = logService.findLoginlog(page, limit);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", pageInfo.getCount());
		map.put("data", pageInfo.getPageInfos());
		
		return map;
	}
}
