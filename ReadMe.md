本项目是使用spring、springMVC、mybatis框架构建的简单的crm系统
数据库使用MySQL，前端展示使用了框架bootstrap与layer弹窗插件
使用了shiro框架简单实现了登陆与权限控制
部署在aws ec2实例上，数据库用的aws rds
普通用户（具有增加员工权限）：用户名：user 密码：123456
管理员用户（具有增加与修改员工权限）：用户名：manager 密码：123456
超级管理员（具有增加与修改与删除和批量删除员工权限）