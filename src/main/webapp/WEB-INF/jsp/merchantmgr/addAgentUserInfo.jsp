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
<title>修改代理商用户</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/saveAgentUserInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>代理编号：</label>
                <input type="text" value="${agentNo }" size="30" name="agentNo" class="required" readonly="readonly"/>
                <%-- <input name="agentNo" type="text" size="30" value="${agentUser.agentNo}" class="required"/> --%>
            </p>
            <p> 
                <label>登陆名：</label>
                <input name="loginAccount" type="text" size="30" value="${agentUser.loginAccount }" class="required"/>
            </p>
            <p> 
                <label>登陆密码：</label>
                <input name="loginPass" type="password" size="30" value="${agentUser.loginPass }" class="required"/>
            </p>
            <p>
                <label>用户名：</label> 
                <input name="userName" type="text"  size="30" value="${agentUser.userName}" class="required"/>
            </p>
             <p>
                <label>用户邮箱：</label>
                <input name="email" type="text"  size="30" value="${agentUser.email}" class="required"/>
            </p>
             <p>
                <label>用户电话：</label>
                <input name="phone" type="text"  size="30" value="${agentUser.phone}" class="required"/>
            </p>
             <p>
                <label>状态：</label>
                <c:if test="${agentUser.status==1 }">
	                <select class="combox" selectedValue="${agentUser.status }" relValue="columnKey"  relText="columnvalue" name="status">
                	<option value="1" selected="selected">正常</option>
                	<option value="2">关闭</option>
				    </select>
                </c:if>
                <c:if test="${agentUser.status==2 }">
                 	<select class="combox" selectedValue="${agentUser.status }" relValue="columnKey"  relText="columnvalue" name="status">
                	<option value="1">正常</option>
                	<option value="2" selected="selected">关闭</option>
                	</select>
                </c:if>
                <c:if test="${agentUser.status==null }">
                 	<select class="combox" selectedValue="${agentUser.status }" relValue="columnKey"  relText="columnvalue" name="status">
                	<option value="1">正常</option>
                	<option value="2">关闭</option>
                	</select>
                </c:if>
            </p>
            <p>
                <label>备注：</label>
                <textarea rows="5" cols="30" name="remark">${agentUser.remark }</textarea>
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