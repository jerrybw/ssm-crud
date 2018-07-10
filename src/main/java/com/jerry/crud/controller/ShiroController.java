package com.jerry.crud.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 向博文
 * @date 2018年7月2日
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {
	
	private Logger logger = Logger.getLogger("LoginLogger");
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request,@RequestParam("username")String username,@RequestParam("password")String password) {
		Subject subject = SecurityUtils.getSubject();
		logger.info("接收到登陆请求，登陆请求ip为："+request.getLocalAddr());
		if(!subject.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			token.setRememberMe(true);
			try {
				subject.login(token);
				logger.info("登陆成功【username："+username+" password："+password + "】");
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("登陆失败【username："+username+" password："+password + "】");
			}
		}
		return "index";
	}
}
