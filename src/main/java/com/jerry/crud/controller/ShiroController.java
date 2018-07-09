package com.jerry.crud.controller;

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

	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("username")String username,@RequestParam("password")String password) {
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			token.setRememberMe(true);
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("登录失败！"+e.getMessage());
			}
		}
		return "index";
	}
}
