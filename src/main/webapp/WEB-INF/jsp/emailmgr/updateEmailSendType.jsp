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
<title>添加邮件发送类型</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/emailmgr/updateEmailSendType" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input name="id" type="hidden" value="${info.id }"> 
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>邮件发送类型：</label>
                <input name="sendType" type="text" size="30" value="${info.sendType }" class="required"/>
            </p>
            <p>
                <label>发送处理类：</label>
                <input name="sendService" type="text" size="30" value="${info.sendService }"  class="required"/>
            </p>
            <p>
                <label>邮箱主题：</label>
                <input name="emailSubject" type="text" size="30"  class="required" value="${info.emailSubject }"/>
            </p>
            <p>
                <label>发送类型名：</label>
                <input name="name" type="text" size="30"   value="${info.name }"/>
            </p>
             <p>
                <label>持卡人/商户：</label>
                <select name="type">
                	<option value="1" ${info.type==1?'selected':'' }>发送给商户</option>
                	<option value="2" ${info.type==2?'selected':'' }>发送给持卡人</option>
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