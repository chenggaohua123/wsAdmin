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
<title>修改字典数据</title>
</head>
<body>
	<div class="pageContent">
	<form method="post" action="<%=path %>/sysmgr/updateBaseDateInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>字典名称：</label>
                <input name="id" type="hidden" value="${baseInfo.id }">
                <input name="tableName" type="text" size="30" value="${baseInfo.tableName }" class="required alphanumeric"/>
            </p>
            <p>
                <label>KEY：</label>
                <input name="columnKey" type="text" value="${baseInfo.columnKey }" class="alphanumeric" size="30"/>
            </p>
            <p>
                <label>VALUE：</label>
                <input name="columnvalue" type="text" value="${baseInfo.columnvalue }" size="30" />
            </p>
             <p>
                <label>字典数据表KEY列名：</label>
                 <input name="columnKeyName" type="text" value="${baseInfo.columnKeyName }" size="30" />
            </p>
             <p>
                <label>字典数据表VALUE列名：</label>
                <input name="columnVauleName" type="text" value="${baseInfo.columnVauleName }" size="30" />
            </p>
             <p>
                <label>备注信息：</label>
                <textarea rows="2" name="remark" cols="30">${baseInfo.remark }</textarea>
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