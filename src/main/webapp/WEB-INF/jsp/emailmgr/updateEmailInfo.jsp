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
<title>添加邮箱信息</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/emailmgr/updateEmailInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input type="hidden" name="id" id="id" value ="${info.id}">
            <p>
                <label>服务器：</label>
                <input name="emailHost" type="text" size="30" class="required" value="${info.emailHost}"/>
            </p>
            <p>
                <label>邮箱账号：</label>
                <input name="emailAccount" type="text" size="30"  value="${info.emailAccount}" class="required"/>
            </p>
             <p>
                <label>邮箱密码：</label>
                <input name="emailPassword" type="text" size="30"  value="${info.emailPassword}" class="required"/>
            </p>
             <p>
                <label>端口号：</label>
                <input name="emailPort" type="text" size="30"  value="${info.emailPort}" class="required"/>
            </p>
             <p>
                <label>上限次数：</label>
                <input name="sendCountLimit" type="text" size="30"  value="${info.sendCountLimit}" class="required"/>
            </p>
            <p>
                <label>邮箱类型：</label>
                <select name="emailType">
					<option value="1" ${info.emailType=='1'?'selected':'' }>使用中</option>
					<option value="0" ${info.emailType=='0'?'selected':'' }>备用</option>
				</select>
            </p>
            <p>
                <label>有效性：</label>
                <select name="enabled">
					<option value="1" ${info.enabled=='1'?'selected':'' }>有效</option>
					<option value="0" ${info.enabled=='0'?'selected':'' }>无效</option>
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