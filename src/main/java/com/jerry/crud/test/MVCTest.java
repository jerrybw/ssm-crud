package com.jerry.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.jerry.crud.bean.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/springDispatcherServlet-servlet.xml"})
public class MVCTest {
	
	@Autowired
	WebApplicationContext wContext;
	
	//虚拟mvc请求
	MockMvc mockMvc;
	
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wContext).build();
	}
	
	@Test
	public void testPageList() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps?test1=test1&test2=test2&test3=test3").param("fn", "1")).andReturn();
		@SuppressWarnings("unchecked")
		PageInfo<Employee> pageInfo = (PageInfo<Employee>) result.getRequest().getAttribute("pageInfo");
		System.out.println("当前页码="+pageInfo.getPageNum());
		System.out.println("总页码="+pageInfo.getPages());
		System.out.println("总记录数="+pageInfo.getTotal());
		System.out.println("需要连续显示的页码=");
		int[] is = pageInfo.getNavigatepageNums();
		for (int i : is) {
			System.out.print(" "+i);
		}
		System.out.println();
		List<Employee> list = pageInfo.getList();
		for (Employee employee : list) {
			System.out.println("ID:"+employee.getEmpId()+"-->Name:"+employee.getEmpName());
			System.out.println("ID:"+employee.getEmpId()+"-->Name:"+employee.getDepartment().getDeptName());
		}
	}
	

}
