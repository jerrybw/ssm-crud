<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SSM-CRUD</title>
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/list.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${APP_PATH}/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/list.css">
</head>
<body>
	<input type="hidden" id="basePath" value="${APP_PATH}">
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-6">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 操作按钮 -->
		<div class="row">
			<div class="col-md-8">
				<!-- 部门 -->
				<div class="col-md-2">
					<div class="dropdown">
					  <button class="btn btn-default dropdown-toggle" type="button" id="deptMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					    <span id="deptName">${dName }</span>	
					    <span class="caret"></span>
					  </button>
					  <ul id="dept" dept="${dId }" class="dropdown-menu" aria-labelledby="deptMenu">
					    <li><a href="javascript:changeDept(0,'请选择')">请选择</a></li>
					    <c:forEach items="${depts }" var="dept">
					    	<li><a href="javascript:changeDept(${dept.deptId},'${dept.deptName}')">${dept.deptName}</a></li>
					    </c:forEach>
					  </ul>
					</div>
				</div>
				<!-- 性別 -->
				<div class="col-md-2">
					<div class="dropdown">
					  <button class="btn btn-default dropdown-toggle" type="button" id="genderMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					    <span id="genderName">${genderName}</span>	
					    <span class="caret"></span>
					  </button>
					  <ul id="gender" gender="${gender }" class="dropdown-menu" aria-labelledby="genderMenu">
					    <li><a href="javascript:changeGender('','请选择')">请选择</a></li>
				    	<li><a href="javascript:changeGender('M','男')">男</a></li>
				    	<li><a href="javascript:changeGender('F','女')">女</a></li>
				    	<li><a href="javascript:changeGender('S','未知')">未知</a></li>
					  </ul>
					</div>
				</div>
				<!-- 每页显示数据条数 -->
				<div class="col-md-2">
					<div class="dropdown">
					  <button class="btn btn-default dropdown-toggle" type="button" id="pdnMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					    <span id="pdnName">${pdn}</span>	
					    <span class="caret"></span>
					  </button>
					  <ul id="pdn" pdn="${pdn }" class="dropdown-menu" aria-labelledby="pdnMenu">
					    <li><a href="javascript:changePdn(5)">5</a></li>
				    	<li><a href="javascript:changePdn(10)">10</a></li>
				    	<li><a href="javascript:changePdn(50)">50</a></li>
				    	<li><a href="javascript:changePdn(100)">100</a></li>
					  </ul>
					</div>
				</div>
				<!-- 模糊查询-->
				<div class="col-md-4">
					<div class="input-group">
					  	<input type="text" id="queryCondition" class="form-control" placeholder="查询条件，支持模糊查询" aria-describedby="searchBtn" value="${queryCondition }">
					  	<span class="input-group-addon" id="searchBtn" >
					  		<span class="glyphicon glyphicon-search" aria-hidden="true" onclick="search(1)"></span>
						</span>
					</div>
				</div>
			</div>
			
			<div class="col-md-4">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 表格数据 -->
		<div class="row data">
			<table class="table table-hover">
				<tr>
					<th>#</th>
					<th>name</th>
					<th>gender</th>
					<th>email</th>
					<th>deptName</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${pageInfo.list }" var="emp">
					<tr>
						<td>${emp.empId }</td>
						<td>${emp.empName }</td>
						<td>${emp.gender=="M"?"男":emp.gender=="F"?"女":"未知" }</td>
						<td>${emp.email }</td>
						<td>${emp.department.deptName }</td>
						<td>
							<button class="btn btn-primary btn-sm">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
							修改
							</button>
							<button class="btn btn-danger btn-sm">
							<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
							删除
							</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<!-- 分页信息 -->
		<div class="row">
			<div class="col-md-6">
				当前页：${pageInfo.pageNum },总记录数：${pageInfo.total },总页数：${pageInfo.pages }
			</div>
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				  	<li><a href="javascript:search(1)">首页</a></li>
				  	<c:if test="${pageInfo.hasPreviousPage }">
					  	<li>
					      <a href="javascript:search(${pageInfo.pageNum-1})" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
				  	</c:if>
				    
				    <c:forEach items="${pageInfo.navigatepageNums }" var="page_num">
				    	<c:if test="${page_num == pageInfo.pageNum }">
				    		<li class="active"><a href="javascript:search(${page_num })">${page_num }</a></li>
				    	</c:if>
				    	<c:if test="${page_num != pageInfo.pageNum }">
				    		<li><a href="javascript:search(${page_num })">${page_num }</a></li>
				    	</c:if>
				    </c:forEach>
				    <c:if test="${pageInfo.hasNextPage}">
					    <li>
					      <a href="javascript:search(${pageInfo.pageNum + 1})" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
				    </c:if>
   				  	<li><a href="javascript:search(${pageInfo.pages})">末页</a></li>
				  </ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>