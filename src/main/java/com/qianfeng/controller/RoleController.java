package com.qianfeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Role;
import com.qianfeng.service.IRoleService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;

@Controller
public class RoleController {

	@Autowired
	private IRoleService rService;
	
	// 查找角色
	@RequestMapping(value="findRole", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findRole() {
		try {
			List<Role> list = rService.findRoleAll();
			return JsonUtils.writeJosnInfo(1, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="updateRoleId",method=RequestMethod.GET)
	@ResponseBody
	public JsonBean updateRole(Integer id, String rids) {
		try {
			rService.addRoleService(id, rids);
			return JsonUtils.writeJosnInfo(1, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.writeJosnInfo(0, e.getMessage());
		}
	}
}
