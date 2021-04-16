<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>参数值</title>
</head>
<body>
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
	<p>
         <label>参数名称：</label>
         ${info.paramName}
	</p>
		<p>
         <label>参数显示名称：</label>
         ${info.paramDescName}
	</p>
	
		<p>
         <label>参数类型：</label>
         ${info.type}
	</p>
	<!-- 
	<p>
         <label>状态：</label>
         ${info.status == '1' ? '生效':'待审核'}
	</p>
	 -->
	<p>
         <label>存储名称：</label>
         ${info.tableName}
	</p>
	<p>
	列表值：
	${funcs:mapToString(info.tableValue)}
	</p>
	</div>
</div>
</body>
</html>