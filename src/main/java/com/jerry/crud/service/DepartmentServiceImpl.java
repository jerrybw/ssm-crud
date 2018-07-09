package com.jerry.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerry.crud.bean.Department;
import com.jerry.crud.dao.DepartmentMapper;

/**
 * @author 向博文
 * @date 2018年6月24日
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	DepartmentMapper departmentMapper;

	/**
	 * @return
	 */
	public List<Department> getAll() {
		// TODO Auto-generated method stub
		return departmentMapper.selectByExample(null);
	}

}
