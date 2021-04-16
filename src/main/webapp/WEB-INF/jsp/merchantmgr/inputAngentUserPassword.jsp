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
<title>请输入密码</title>
</head>
<body>
	<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/updateAgentUserInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>用户名：</label>
                <input name="loginAccount" type="text" value="${agentUser.userName }" readonly="readonly" size="30" class="required"/>
            </p>
            <p>
                <label>请输入密码：</label>
                <input name="loginPass" type="password"  size="30" class="required"/>
                <input name="id" type="hidden"  value="${agentUser.id }"  size="30" class="required"/>
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