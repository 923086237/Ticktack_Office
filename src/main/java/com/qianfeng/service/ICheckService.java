package com.qianfeng.service;

import com.qianfeng.entity.Check;
import com.qianfeng.vo.PageBean;

public interface ICheckService {
	
	public PageBean<Check> findAllCheck(Integer page,Integer limit,String startno);

}
