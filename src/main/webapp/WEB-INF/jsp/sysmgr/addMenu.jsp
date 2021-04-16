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
<title>添加菜单</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/sysmgr/addMenuInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           <input type="hidden" name="systemId" value="${systemId }"/>
           <input type="hidden" name="parentNo" value="${parentNo }"/>
            <p>
                <label>菜单名称：</label>
                <input name="menuName" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>菜单编号：</label>
                <input name="menuNo" type="text" size="30" value="${menuNo }" class="required number"/>
            </p>
             <p>
                <label>菜单类型：</label>
               	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MENUTYPE" relValue="columnKey"  relText="columnvalue" name="menuType">
			    </select>
            </p>
             <p>
                <label>是否有效：</label>
               	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MENUSTATUS" relValue="columnKey" relText="columnvalue" name="flag">
			    </select>
            </p>
             <p>
                <label>URL：</label>
                <input name="actionName" type="text" size="30" class="required"/>
            </p>
             <p>
                <label>菜单排序：</label>
                <input name="orderBy" type="text" size="30" class="required number"/>
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