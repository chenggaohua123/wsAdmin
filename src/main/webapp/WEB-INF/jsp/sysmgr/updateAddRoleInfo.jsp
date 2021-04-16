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
<title>更新角色</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/sysmgr/updateAddRole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>所属系统：</label>
               <%-- 	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=SYSTEMID" selectedValue="${roleInfo.systemId }" 
               	relValue="columnKey" relText="columnvalue" name="systemId" readonly="readonly">
			    </select> --%>
			    <input type="hidden" name="systemId" value="${roleInfo.systemId}">
			    <input type="text" name="systemName" value='<c:if test="${roleInfo.systemId == '1'}">管理系统</c:if><c:if test="${roleInfo.systemId == '2'}">商户系统</c:if>' readonly="readonly"/>
            </p>
            <p>
                <label>角色名称：</label>
                <input name="roleName" type="text" value="${roleInfo.roleName }" size="30" class="required"/>
            </p>
            <p>
              <label>是否关闭：</label>
                <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=ROLESTATUS" selectedValue="${roleInfo.enabled }" relValue="columnKey" relText="columnvalue" name="enabled">
			    </select>
               <input type="hidden" value="${roleInfo.id }" name="id">
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