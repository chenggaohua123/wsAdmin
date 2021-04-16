<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户关联代理商</title>
</head>
<body>
<div class="pageContent" layoutH="0">
 <form method="post" action="<%=path %>/sysmgr/addUserRelAgent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>用户ID：</label>
                <input name="id" type="text" value="${id}" size="30" readonly="readonly" class="required"/>
            </p>
            <p>
                <label>关联代理商：</label>
                <input name="agentNo" type="text" id="agentNo" size="30"  class="required"/>
                <a class="btnLook" href="<%=path %>/sysmgr/userRelAgetnBack" width="1100" height="550"  rel="UserRelAgent" mask="true" lookupGroup="" lookupPk="agentNo">查找带回</a>
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