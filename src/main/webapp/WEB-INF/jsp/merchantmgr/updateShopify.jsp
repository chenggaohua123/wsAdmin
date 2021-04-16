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
<title>修改shopify(开/关)</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/updateShopify" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            
            <p>
                <label>商户号：</label>
                <input type="hidden" name="id" value="${data.id}">
                <input name="merchantNo" type="text" size="30" value="${data.merNo}" class="required" readonly="readonly"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" type="text" size="30" class="required" value="${data.terNo}" readonly="readonly"/>
            </p>
            
            <p>
                <label>shopify状态：</label>
               	<select name="shopifyOnOff">
               		<c:choose>
               			<c:when test="${data.shopifyOnOff=='0' }">
               				<option value="0" selected="selected">关</option>
               			</c:when>
               			<c:otherwise>
               				<option value="0">关</option>
               			</c:otherwise>
               		</c:choose>
               		<c:choose>
               			<c:when test="${data.shopifyOnOff=='1' }">
               				<option value="1" selected="selected">开</option>
               			</c:when>
               			<c:otherwise>
               				<option value="1" >开</option>
               			</c:otherwise>
               		</c:choose>
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