package com.jerry.crud.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import java.util.Set;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 向博文
 * @date 2018年7月5日
 */
@Controller
@RequestMapping("/test")
public class TestController {
	
	
	@ResponseBody
	@RequestMapping("/json")
	public Object testJson(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> res = new HashMap<>();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			System.out.println("key "+entry.getKey());
			System.out.println("value "+entry.getValue()[0]);
		}
		res.put("code", "200");
		return res;
	}

}
