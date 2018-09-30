package com.qianfeng.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonToken;
import com.qianfeng.entity.Authority;
import com.qianfeng.service.IAuthorityService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;



@Controller
public class AuthorityController {

	@Autowired
	private IAuthorityService aService;
	
	@RequestMapping(value="findtitles", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findTitle(HttpServletRequest request) {
		// 获取session域中的用户登录名
		String no = (String) request.getSession().getAttribute("no");
		// 放入到map集合里面， 查询一级权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("parentid", 0);
		// 把查询到一级权限map集合放入到List集合里边
		List<Authority> list = aService.findTitle(map);
		
		List<Map<String, Object>> autho = new ArrayList<>();
		for (Authority auth : list) {
			// 遍历集合把一级权限放入集合中
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("parentAuto", auth);
			
			// 通过一级权限的id获取到二级权限的parentid;
			Map<String, Object> map2 = new HashMap<>();
			map2.put("no", no);
			Integer parentid = auth.getId();
			map2.put("parentid", parentid);
			List<Authority> list1 = aService.findTitle(map2);
			// 把二级权限map集合放入到对应的一级权限下
			map1.put("childAuto", list1);
			autho.add(map1);
		}
		
		return JsonUtils.writeJosnInfo(1, autho);
		
	}
	
	
	@RequestMapping(value="findAuthById", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findAuthById(Integer id) {
		Authority authority = aService.findByIdService(id);
		return JsonUtils.writeJosnInfo(1, authority);
	}
	
	@RequestMapping(value="findRoleAuth",method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findRoleAuth(int roid) {
		List<Authority> list = aService.findRoleAuthService(roid);
		return JsonUtils.writeJosnInfo(1, list);
	}
	
	@RequestMapping(value="findAuthLimit",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAuthLimit(int page, int limit) {
		PageBean<Authority> pageInfo = aService.findAuthLimitService(page,limit);
		Map<String, Object> map = new HashMap<>();
			
		map.put("code", 0);// 针对layui的表格，0表示成功
		map.put("msg", "");
		map.put("count", pageInfo.getCount());
		map.put("data", pageInfo.getPageInfos());
			
		return map;	
	}
	
	@RequestMapping(value="addRoleAuByIdService",method=RequestMethod.GET)
	@ResponseBody
	public JsonBean insertRoleAuByIdService(Authority authority) {
		try {
			aService.insertRoleAuByIdService(authority);
			return JsonUtils.writeJosnInfo(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(0, null);
		}
	}
	
	@RequestMapping(value="findRoleAuthAll", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findRoleAuthAll() {
		List<Authority> list = aService.findRoleAuthServiceAll();
		return JsonUtils.writeJosnInfo(1, list);
	}
	
	@RequestMapping(value="deleteRoleAuthController",method=RequestMethod.GET)
	@ResponseBody
	public JsonBean deleteRoleAuthController(Integer id) {
		try {
			aService.deleteRoleAuById(id);
			return JsonUtils.writeJosnInfo(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="updateService",method=RequestMethod.GET)
	@ResponseBody
	public JsonBean updateService(Authority authority) {
		aService.updateService(authority);
		return JsonUtils.writeJosnInfo(1, null);
	}

	@RequestMapping(value="findByParentIdController",method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findByParentId() {
		List<Authority> list = aService.findByParentIdService();
		return JsonUtils.writeJosnInfo(1, list);
	}
	
	@RequestMapping(value="updateAuthServiceAll")
	@ResponseBody
	public JsonBean updateAuthServiceAll(String rids, Integer id) {
		aService.inserTotalAuthService(id, rids);
		return JsonUtils.writeJosnInfo(1, null);
	}
	
	
	
}















