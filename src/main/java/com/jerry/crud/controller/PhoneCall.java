package com.jerry.crud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.crud.untils.HttpRequestUtil;
import com.jerry.crud.untils.MD5Util;


/**
 * @author 向博文
 * @date 2018年8月6日
 */
@Controller
public class PhoneCall {
	
	
	@ResponseBody
	@RequestMapping("/call")
	public Object call(String cno,String tel) {
		Map<String, String> res = new HashMap<>();
		if("4321".equals(cno) || "6008".equals(cno) || "4210".equals(cno)) {
			String url = "https://api-test-2.cticloud.cn/interface/v10/previewOutcall";
			Map<String, String> param = new HashMap<>();
			param.put("validateType", "2");
			param.put("enterpriseId", "7000002");
			long timestamp = System.currentTimeMillis()/1000;
			param.put("timestamp",timestamp + "");
			param.put("sign", MD5Util.md5Password("7000002"+timestamp+"2ff82f103ba7aac0055da123d56168c6"));
			param.put("cno", cno);
			param.put("tel", tel);
			HttpRequestUtil.sendPost(url, param);
		}
		return res;
	}

}
