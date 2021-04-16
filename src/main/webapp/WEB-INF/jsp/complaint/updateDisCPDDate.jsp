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
<title>修改CPD日期</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/complaint/updateDisCPDDate" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <input type="hidden" name="id" value="${info.id }">
            <p>
                <label>流水号：</label>
                <input name="tradeNo" type="text" size="30" class="required" readonly="readonly"  value="${info.tradeNo }"/>
            </p>
             <p>
                <label>CPD日期：</label>
                <input type="text" name="CPDDate"  id = "transDateStart"   dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${info.CPDDate}"/>
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