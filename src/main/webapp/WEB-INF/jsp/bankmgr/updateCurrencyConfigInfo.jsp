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
<title>Insert title here</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/bankMgr/updateCurrencyConfig" id="pagerForm" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>配置名称：</label>
                <input name="configName" type="text" size="30" value="${configInfo.configName }" class="required"/>
                <input name="id" type="hidden" size="30" value="${configInfo.id }">
            </p>
             <p>
                <label>配置值：</label>
                <input name="configValue" type="text" size="30" value="${configInfo.configValue }" class="required"/>
            </p>
            <p>
                <label>备注：</label>
                <input name="remark" type="text" size="30" value="${configInfo.remark }" />
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