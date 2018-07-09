/**
 * @author 向博文
 */
var curWwwPath = window.document.location.href;
var pathName =  window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
var localhostPaht = curWwwPath.substring(0,pos);
var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
window.basePath =localhostPaht + projectName;
var keydownTime = 0;
//更改部门
function changeDept(deptId,deptName){
	$("#dept").attr("dept",deptId);
	$("#deptName").html(deptName);
}

//更改性别
function changeGender(gender,genderName){
	$("#gender").attr("gender",gender);
	$("#genderName").html(genderName);
}

//更改每页展示数据条数
function changePdn(pdn){
	$("#pdn").attr("pdn",pdn);
	$("#pdnName").html(pdn);
}

//获取所有部门信息
function getDepts(){
	$.ajax({
		url:basePath+"/depts",
		type:"GET",
		success:function(data){
			if(data.code == 1){
				$.each(data.extend.depts,function(index,item){
					$("#dept").append("<li><a href='javascript:changeDept("+item.deptId+",\""+item.deptName+"\")'>"+item.deptName+"</a></li>");
					$("#addDepts").append("<option value="+item.deptId+">"+item.deptName+"</option>");
					$("#updateDepts").append("<option value="+item.deptId+">"+item.deptName+"</option>");
				})
			}
		}
	})
}

//获取员工数据（根据查询条件）
function search(pn){
	var dept = $("#dept").attr("dept");
	var gender = $("#gender").attr("gender");
	var queryCondition = $("#queryCondition").val();
	var dName = $("#deptName").html();
	var pdn = $("#pdn").attr("pdn");
	var url = basePath+"/emps";
	$.ajax({
		url:url,
		type:"GET",
		data:"dId="+dept+"&dName="+dName+"&gender="+gender+"&queryCondition="+queryCondition+"&pn="+pn+"&pdn="+pdn,
		success:function(data){
			if(data.code == 1){
				//渲染数据
				renderData(data);
				//渲染分页数据
				renderPageInfoArea(data);
				//渲染分页导航栏
				renderPageNavArea(data);
			}
			
		}
	})
}

//渲染分页数据
function renderPageInfoArea(data){
	$("#pageInfoArea").html("当前页 <span id='pageNum'>"+data.extend.pageInfo.pageNum+"</span>：,总记录数：<span id='totalEmp'>"+data.extend.pageInfo.total+"</span>,总页数："+data.extend.pageInfo.pages)
}

//渲染分页导航栏
function renderPageNavArea(data){
	var html = "<nav aria-label='Page navigation'><ul class='pagination'>";
	if(!data.extend.pageInfo.hasPreviousPage){
		html += "<li class='disabled'><a href='#'>首页</a></li>";
		html += "<li class='disabled'><a href='#' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>"
	}else{
		html += "<li ><a href='javascript:search(1)'>首页</a></li>";
		html += "<li ><a href='javascript:search("+(data.extend.pageInfo.pageNum - 1)+")' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>"
	}
	$.each(data.extend.pageInfo.navigatepageNums,function(index,item){
		if(data.extend.pageInfo.pageNum == item){
			html += '<li class="active"><a href="#">'+item+'</a></li>';
		}else{
			html += '<li><a href="javascript:search('+item+')">'+item+'</a></li>';
		}
	})
	if(!data.extend.pageInfo.hasNextPage){
		html += "<li class='disabled'><a href='#' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>"
		html += "<li class='disabled'><a href='#'>末页</a></li>";
	}else{
		html += "<li ><a href='javascript:search("+(data.extend.pageInfo.pageNum + 1)+")' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>"
		html += "<li ><a href='javascript:search("+data.extend.pageInfo.pages+")'>末页</a></li>";
	}
	html += "</ul></nav>";
	$("#pageNavArea").html(html);
}

//渲染表格数据
function renderData(data){
	var html = "";
	$.each(data.extend.pageInfo.list,function(index,item){
		html += '<tr>';
		if(isAdmin){
			html += '<td><input type="checkbox" class="checkOne" onclick="checkOne()"></td>';
		}
		html += '<td>'+item.empId+'</td>'+
		'<td>'+item.empName+'</td>'+
		'<td>'+(item.gender=="M"?"男":(item.gender=="F"?"女":"未知"))+'</td>'+
		'<td>'+(item.email==null?"":item.email)+'</td>'+
		'<td>'+(item.department==null?"无":item.department.deptName)+'</td>'+
		'<td>';
		if(isManager){
			html += '<button class="btn btn-primary btn-sm btn-update" empId="'+item.empId+'">'+
			'<span class="glyphicon glyphicon-pencil" aria-hidden="true" ></span>'+
			'修改</button>';
		}
		if(isAdmin){
			html += '<button class="btn btn-danger btn-sm btn-delete" empId="'+item.empId+'">'+
			'<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>'+
			'删除</button>';
		}
		'</td>'+
		'</tr>';
	})
	$("#dataArea tbody").html(html);
}

//删除员工
function deleteEmp(){
	var empId = $(this).attr("empId");
	layer.confirm('确认删除【'+$(this).parent().prevAll().eq(3).text()+'】？', {icon: 1, title:'删除'}, function(index){
		layer.close(index);
		var ii = layer.load();
		setTimeout(function(){
	      layer.close(ii);
	    }, 5000);
		$.ajax({
			url:basePath+"/emp/"+empId,
			type:"DELETE",
			success:function(data){
				layer.close(ii);
				if(data.code == 1){
					layer.msg('删除成功', {icon: 6});
					search($("#pageNum").text());
				}else{
					layer.msg('删除失败，请联系管理员', {icon: 5}); 
				}
			}
		});	
	});
}

//删除员工
function deleteBatchEmp(){
	var empIds = "";
	var empNames = "";
	$.each($(".checkOne:checked"),function(index,item){
		empIds += $(item).parent().next().text() + "-";
		empNames += $(item).parent().nextAll().eq(1).text() + ",";
	})
	empIds = empIds.substring(0,empIds.length-1);
	empNames = empNames.substring(0,empNames.length-1);
	layer.confirm('确认删除【'+empNames+'】？', {icon: 1, title:'删除'}, function(index){
		layer.close(index);
		var ii = layer.load();
		setTimeout(function(){
	      layer.close(ii);
	    }, 5000);
		$.ajax({
			url:basePath+"/emp/"+empIds,
			type:"DELETE",
			success:function(data){
				layer.close(ii);
				if(data.code == 1){
					layer.msg('删除成功', {icon: 6});
					search($("#pageNum").text());
					$(".checkAll").prop("checked",false);
				}else{
					layer.msg('删除失败，请联系管理员', {icon: 5}); 
				}
			}
		});	
	});
}

//全选全不选
function checkAll(){
	var checked = $(".checkAll").prop("checked");
	$.each($(".checkOne"),function(index,item){
		$(item).prop("checked",checked);
	})
	
}

//选中单个
function checkOne(){
	var checkedLen = $(".checkOne:checked").length;
	var checkBoxLen = $(".checkOne").length;
	$(".checkAll").prop("checked",checkedLen==checkBoxLen);
}

//绑定新增模态框
function bindModal(){
	resetForm($("#addEmp"));
	$("#employeeAddModal").modal({
		backdrop:"static"
	})
}

//绑定修改模态框
function bindUpdateModal(){
	resetForm($("#updateEmp"));
	getEmp($(this).attr("empId"));
	$("#updateSaveBtn").attr("empId",$(this).attr("empId"));
	$("#employeeUpdateModal").modal({
		backdrop:"static"
	});
}

//更新员工
function updateEmp(){
	if(validateEmail("emailUpdate")){
		$.ajax({
			url:basePath+"/emp/"+$("#updateSaveBtn").attr("empId"),
			type:"PUT",
			data:$("#updateEmp").serialize(),
			success:function(data){
				if(data.code == 1){
					$('#employeeUpdateModal').modal('hide');
					search($("#pageNum").text());
				}else{
					alert("修改失败");
				}
			}
		})
	}
}

//根据员工id获取员工信息并渲染到修改模态框
function getEmp(empId){
	$.ajax({
		url:basePath+"/emp/"+empId,
		type:"GET",
		success:function(data){
			$("#empNameUpdate").html(data.extend.emp.empName);
			$("#emailUpdate").val(data.extend.emp.email);
			$("#updateEmp input[name='gender']").val([data.extend.emp.gender]);
			$("#updateDepts").val([data.extend.emp.dId]);
		}
	})
}

//保存员工
function saveEmp(){
	if(validateEmpData()){
		$.ajax({
			url:basePath+"/emp",
			type:"POST",
			data:$("#addEmp").serialize(),
			success:function(data){
				if(data.code == 1){
					$('#employeeAddModal').modal('hide');
					resetCondition();
					search($("#totalEmp").text());
				}else{
					$.each(data.extend.errors,function(){
						renderValidateResult($("#"+this.key),false,this.value);
					})
				}
			}
		})
	}
}

//数据校验
function validateEmpData(){
	var regEmpName = validateEmpName();
	var regEmail = validateEmail("email");
	return regEmpName && regEmail;
}

//员工姓名校验
function validateEmpName(){
	var regEmpName = /(^[a-zA-Z0-9_]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
	var empName = $("#empName").val();
	var flag = renderValidateResult($("#empName"),regEmpName.test(empName),"员工姓名必须由6-16位大小写字母数字以及下划线或者2-5位中文组成");
	if(flag){
		return validateEmpNameIsAble(empName);
	}else{
		return false;
	}
}

//验证与员工名是否可用
function validateEmpNameIsAble(empName){
	var flag = true;
	$.ajax({
		url:basePath+"/checkUserName",
		data:{
			"empName":empName
		},
		type:"POST",
		success:function(data){
			if(data.code != 1){
				renderValidateResult($("#empName"),false,data.extend.validateRes);
				flag = false
			}
		}
	})
	return flag;
}


//邮箱校验
function validateEmail(email){
	var regEmail = 	/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
	var emailStr = $("#"+email).val();
	return renderValidateResult($("#"+email),regEmail.test(emailStr),"邮箱格式不正确");
}

//渲染校验结果
function renderValidateResult(ele,isSuccess,msg){
	ele.parent().removeClass("has-error has-success");
	ele.next("span").text("");
	if(isSuccess){
		ele.parent().addClass("has-success");
		return true;
	}else{
		ele.parent().addClass("has-error");
		ele.next("span").text(msg);
		return false;
	}
}


//重置表单
function resetForm(ele){
	ele.find("*").removeClass("has-error has-success");
	ele.find(".help-block").text("");
	ele[0].reset();
}


//重置查询条件
function resetCondition(){
	$("#dept").attr("dept","");
	$("#deptName").html("部门");
	$("#gender").attr("gender","");
	$("#genderName").html("性别");
	$("#queryCondition").val("");
}