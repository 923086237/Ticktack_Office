package com.qianfeng.utils;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.qianfeng.vo.JsonBean;


public class JsonUtils {

	/**
	 * 返回json格式数据
	 * @param code
	 * @param msg
	 * @param response
	 */
	public static JsonBean writeJosnInfo(int code, Object msg){
		JsonBean bean = new JsonBean();  
		bean.setCode(code);
		bean.setMsg(msg);
		return bean;
	
	}
//	public static JsonBean writeDateJosnInfo(int code, Object msg){
//		JsonBean bean = new JsonBean();
//		bean.setCode(code);
//		bean.setMsg(msg);
//		Order order = new Order();
//		long timeStamp = order.getCreateDate();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
//		String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
//		        System.out.println(sd);//打印出你要的时间
//				return bean;
//	}
	
}
