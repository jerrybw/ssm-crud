package com.jerry.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jerry.crud.bean.Employee;
import com.jerry.crud.bean.Msg;
import com.jerry.crud.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeServiceImpl;
	
//	private Logger logger = logfa
	/**
	 * 分页展示员工
	 * @param pn 请求第几页的数据 
	 * @param pdn 每页展示几条数据
	 * @param queryConditions 查询条件
	 * @param model 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="emps",method=RequestMethod.GET)
	public Msg emps(@RequestParam(value="pn",defaultValue="1")int pn,
			@RequestParam(value="pdn",defaultValue="5")int pdn,
			@RequestParam(required=false,defaultValue="")String gender,
			@RequestParam(required=false)String queryCondition,
			@RequestParam(required=false)Integer dId) {
//		logger.info("获取所有员工");
		//开始的页数，以及每页数据条数，调用此方法后的第一次查询操作会变成分页查询
		PageHelper.startPage(pn,pdn);
		//查询数据
		List<Employee> emps= employeeServiceImpl.getAll(gender,queryCondition,dId);
		//将emps放入pageInfo中，并指定要显示的页数
		PageInfo<Employee> pageInfo = new PageInfo<Employee>(emps,5);
		//封装结果
		Msg result = Msg.success().add("pageInfo", pageInfo);
		return result;
	}
	
	
	//新增员工
	@ResponseBody
	@RequestMapping(value="emp",method=RequestMethod.POST)
	public Object emp(@Valid Employee employee,BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Msg faild = Msg.faild();
			List<Map<String, String>> errorList = new ArrayList<>();
			for (FieldError fieldError : fieldErrors) {
				Map<String, String> error = new HashMap<>();
				error.put("key", fieldError.getField());
				error.put("value", fieldError.getDefaultMessage());
				errorList.add(error);
			}
			faild.add("errors", errorList);
			return faild;
		}
		if(employee.getGender() == null) {
			employee.setGender("S");
		}
		employeeServiceImpl.addEmp(employee);
		return Msg.success();
	}

	/**
	 * 验证员工姓名是否可用
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="checkUserName",method=RequestMethod.POST)
	public Object checkUserName(String empName) {
		boolean isAble = employeeServiceImpl.userNameCheck(empName);
		if(isAble) {
			return Msg.success();
		}else {
			return Msg.faild().add("validateRes", "员工姓名已存在");
		}
	}
	
	/**
	 * 根据empId获取员工数据
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="emp/{id}",method=RequestMethod.GET)
	public Object getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeServiceImpl.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	
	/**
	 * 根据员工id修改员工
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="emp/{empId}",method=RequestMethod.PUT)
	public Object updateEmp(Employee employee) {
		boolean success = false;
		try {
			success = employeeServiceImpl.updateEmp(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success?Msg.success():Msg.faild();
	}
	
	
	@ResponseBody
	@RequestMapping(value="emp/{ids}",method=RequestMethod.DELETE)
	public Object deleteEmp(@PathVariable("ids")String ids) {
		boolean success = false;
		try {
			success = employeeServiceImpl.deleteEmp(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success?Msg.success():Msg.faild();
	}

}
