package com.jerry.crud.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 向博文
 * @date 2018年6月24日
 */
public class Msg {
	
	private int code;
	private String msg;
	private Map<String, Object> extend = new HashMap<String, Object>();
	
	public static Msg success() {
		Msg success = new Msg();
		success.setCode(1);
		success.setMsg("成功");
		return success;
	}
	
	public static Msg faild() {
		Msg faild = new Msg();
		faild.setCode(-1);
		faild.setMsg("失败");
		return faild;
	}
	
	public Msg add(String key,Object value) {
		this.extend.put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getExtend() {
		return extend;
	}
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	
	

}
