<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="funcs" uri="funcs"%> 
 <%
	String path = request.getContextPath();
%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看新闻公告</title>
</head>

<body>
<div class="pageContent" layoutH="56">
        <h1 align="center">${info.newsTitle }</h1>
        <h6 align="right">发布时间：${info.lastModifyDate }</h6>
         <hr>
         ${info.newsContext}
	</div>
</body>
</html>