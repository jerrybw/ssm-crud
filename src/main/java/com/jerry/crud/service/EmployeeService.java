package com.jerry.crud.service;

import java.util.List;

import com.jerry.crud.bean.Employee;

/**
 * @author 向博文
 * @date 2018年7月3日
 */
public interface EmployeeService {

	public List<Employee> getAll(String gender, String queryCondition,Integer dId);
	
	public void addEmp(Employee employee);
	
	public boolean userNameCheck(String empName);
	
	public Employee getEmp(Integer id);
	
	public boolean updateEmp(Employee employee);
	
	public boolean deleteEmp(String ids);
	
}
