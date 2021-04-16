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
<title>添加伪冒单信息</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/complaint/addFakeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input name="complaintType" type="hidden" id="cId">
        	<input name="status" type="hidden" value="0">
        	<input name="type" type="hidden" value="3">
            <p>
                <label>流水号：</label>
                <input name="tradeNo" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>伪冒单通知时间：</label>
                <input type="text" name="complaintDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date required"/>
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