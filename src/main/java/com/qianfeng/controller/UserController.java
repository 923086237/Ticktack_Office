package com.qianfeng.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qianfeng.dao.IUsersDao;
import com.qianfeng.entity.LoginLog;
import com.qianfeng.entity.Users;
import com.qianfeng.service.ILoginlogService;
import com.qianfeng.service.IUsersService;
import com.qianfeng.utils.AddressUtils;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.utils.MD5Utils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class UserController {
	
	@Autowired
	private IUsersDao uDao;
	
	@Autowired
	private ILoginlogService logService;
	
	@Autowired
	private IUsersService uService;
	
	// 登录操作
	@RequestMapping(value="/loginUsers", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean loginUsers(String no, String password, HttpSession session, HttpServletRequest request) {
		JsonBean bean = new JsonBean();
		Users user = uDao.findByName(no);
		String pwd = user.getPassword();
		//System.out.println(MD5Utils.getMD5(password));
		if(user.getPassword().equals(MD5Utils.getMD5(password))) { 
			UsernamePasswordToken token = new UsernamePasswordToken(no, pwd);
			Subject subject = SecurityUtils.getSubject(); 
			
			try {
				subject.login(token);
				addLog(no);
				session.setAttribute("no", no);
				System.out.println("获取session值是： " + session.getAttribute("no"));
				bean.setCode(1);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				bean.setCode(0);
				bean.setMsg(e);
			}		
		}else {
			bean.setCode(0);
			bean.setMsg("密码输入错误，请重新输入");
		}
	
		return bean;
	}
	
	public void addLog(String no) {
		LoginLog Loginlog = new LoginLog();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String hostAddress = AddressUtils.getIpAddr(request);
		System.out.println("hostAddr: " + hostAddress);
		
		Loginlog.setIp(hostAddress);
		Loginlog.setNo(no);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		Date dataNum = null;
		try {
			dataNum = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Loginlog.setCreatetime(dataNum);
		
		AddressUtils addrUtils = new AddressUtils();
		
		try {
			Loginlog.setLocation(addrUtils.getAddresses("ip="+hostAddress, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logService.addLog(Loginlog);
		
	}

	@RequestMapping(value="/findName", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findByName(HttpSession session) {
		String no = (String)session.getAttribute("no");
		return JsonUtils.writeJosnInfo(0, no);
	}
	
	@RequestMapping(value="findByUserId", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findByUserId(Integer id) {
		Users findByUserId = uService.findByUserId(id);
		return JsonUtils.writeJosnInfo(1, findByUserId);
	}
	
	// 注销登录
	@RequestMapping(value="/loginout", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean loginOut(HttpSession session) {
		
		JsonBean bean = new JsonBean();
		
		session.removeAttribute("no");
		bean.setCode(1);
		//System.out.println("删了没有啊？");
		return bean;	
	}
	
	
	@RequestMapping(value="/count", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean count(HttpSession session) {
		JsonBean bean = new JsonBean();
		int count = (int) session.getAttribute("Count");
		bean.setCode(1);
		bean.setMsg(count);
		return bean;
	}
   
	public JsonBean usermessage(HttpSession session) {
		JsonBean bean = new JsonBean();
		String no = (String) session.getAttribute("no");
		Users user = null;
		
		try {
			user = uService.findUserByNo(no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean.setCode(1);
		bean.setMsg(user);
		return bean;
		
	}
	
	//查询所有
	@RequestMapping(value="findAllUsers", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAllUsers(int page, int limit){
		PageBean<Users> pageInfo = uService.findAllUser(page, limit);
		
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);// 针对layui的表格，0表示成功
		map.put("msg", "");
		map.put("count", pageInfo.getCount());
		map.put("data", pageInfo.getPageInfos());
		
		return map;
	}
	
	@RequestMapping(value="findByUserNo", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findByUserNo() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		if((String) request.getSession(false).getAttribute("no") != null) {
			String no = (String) request.getSession(false).getAttribute("no");
			Users user = uService.findUserByNo(no);
			return JsonUtils.writeJosnInfo(1, user);
		}else {
			return JsonUtils.writeJosnInfo(0, null);
		}
	}
	
	@RequestMapping(value="deleteUser", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean deleteUser(Integer id) {
		try {
			uService.deleteUser(id);
			return JsonUtils.writeJosnInfo(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(0, e);
		}
	}
	
	@RequestMapping(value="updateUser", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean updateUser(Integer id, @RequestParam("uname") String name) {
		try {
			uService.updateUser(id, name);
			return JsonUtils.writeJosnInfo(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(0, e);
		}
	}
	
	@RequestMapping(value="findByIdOrFlag",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByIdOrFlag(int page, int limit, String no, Integer flag, String name, String ro_id){
		if(no != null && no.equals("") == true) {
			no = null;
		}
		if(name != null && name.equals("") == true) {
			name = null;
		}
		if(ro_id != null && ro_id.equals("") == true) {
			ro_id = null;
		}
		
		PageBean<Users> pageInfo = uService.findByNoOrFlag(page, limit, no, flag, name, ro_id);
		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);// 针对layui的表格，0表示成功
		map.put("msg", "");
		map.put("count", pageInfo.getCount());
		map.put("data", pageInfo.getPageInfos());
		return map;
		
	}
}
















