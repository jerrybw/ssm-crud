package com.jerry.crud.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
	public Object testJson(@RequestBody Map<String, Object> map,HttpServletRequest request) {
		System.out.println("收到请求-------");
		String[] strings = {"床前明月光，疑是地上霜。举头望明月，低头思故乡。床前明月光，疑是地上霜。举头望明月，低头思故乡。床前明月光，疑是地上霜。举头望明月，低头思故乡。",
				"燕草如碧丝，秦桑低绿枝。当君怀归日，是妾断肠时。春风不相识，何事入罗帏。","疑是地上霜","秦桑低绿枝","举头望明月","当君怀归日","低头思故乡","是妾断肠时","死别已吞声","春风不相识","生别常恻恻","何事入罗帏"};
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("a", "asdas");
		Map<String, Object> res = new HashMap<>();
		Set<Entry<String, Object>> entrySet = map.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			System.out.println("key "+entry.getKey());
			System.out.println("value "+entry.getValue());
		}
		res.put("type", "playWait");
		Random random = new Random();
		res.put("playObject",strings[random.nextInt(2)]);
		res.put("variables",parameterMap);
		res.put("playType", "4");
		return res;
	}
	

}
