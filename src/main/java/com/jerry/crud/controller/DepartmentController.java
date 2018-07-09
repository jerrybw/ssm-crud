package com.jerry.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.crud.bean.Department;
import com.jerry.crud.bean.Msg;
import com.jerry.crud.service.DepartmentService;

/**
 * @author 向博文
 * @date 2018年6月24日
 */
@Controller
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentServiceImpl;

	@ResponseBody
	@RequestMapping(value="depts",method=RequestMethod.GET)
	public Msg getDepts() {
		List<Department> departments = departmentServiceImpl.getAll();
		Msg result = Msg.success().add("depts", departments);
		return result;
	}
}
