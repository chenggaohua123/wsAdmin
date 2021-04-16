<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加投诉类型</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/complaint/addComplaintTypeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>key：</label>
                <input name="cKey" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>value：</label>
                <input type="text" name="cValue" size="30" class="required"/>
            </p>
             <p>
                <label>有效性：</label>
                <select name="enabled">
					<option value="1">有效</option>
					<option value="0">无效</option>
				</select>
            </p>
             <p>
                <label>原因类型：</label>
                <select name="type">
					<option value="0">调查单</option>
					<option value="1">拒付单</option>
					<option value="2">投诉单</option>
				</select>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>