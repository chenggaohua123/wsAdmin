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
<title>添加商户配置信息</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/addMerchantConfig" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           <p>
                <label>配置名称：</label>
                <input name="configName" type="text" size="30" class="required number"/>
                <input type="hidden" name="merNo" size="30" value="${terInfo.merNo }">
                <input type="hidden" name="terNo" size="30" value="${terInfo.terNo }">
            </p>
            <p>
                <label>配置的KEY：</label>
                <input name="configKey" type="text" size="30" class="required number"/>
            </p>
            <p>
                <label>配置值：</label>
                <input name="configValue" type="text" size="30" class="required"/>
            </p>
             <p>
                <label>备注：</label>
                 <textarea name="remark" cols="22" rows="2"></textarea>
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