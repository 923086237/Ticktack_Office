package com.qianfeng.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.service.ICheckService;

@Controller
public class CheckController {
	@Autowired
	private ICheckService checkService;
	
	@RequestMapping(value="processlist", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> processlist(Integer page,Integer limit,HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		String startno  = (String) request.getSession().getAttribute("no");
		try {
			checkService.findAllCheck(page, limit, startno);
			map.put("code", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 map.put("code", 1);
		}
		return map;
	}
	

}
