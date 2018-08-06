package com.jerry.crud.factory;

import java.util.LinkedHashMap;

/**
 * @author 向博文
 * @date 2018年7月4日
 */
public class FilterChainDefinitionMapBuilder {

	/*
                /login.jsp = anon
                /shiro/login = anon
                /shiro/logout = logout
                /static/** = anon
                /user.jsp = roles[user]
                /admin.jsp = roles[admin]
                
                # everything else requires authentication:
                /** = authc
	 */
	
	public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/login.jsp", "anon");
		map.put("/shiro/login", "anon");
		map.put("/test/json", "anon");
		map.put("/call", "anon");
		map.put("/shiro/logout", "logout");
		map.put("/static/**", "anon");
		map.put("/user.jsp", "roles[user]");
		map.put("/admin.jsp", "roles[admin]");
		map.put("/**", "authc");
		return map;
	}
	
}
