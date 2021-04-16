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
	<form method="post" action="<%=path %>/merchantmgr/updateAgentInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>父级代理号：</label>
                <input name="parentAgentNo" type="text" size="30" value="${agentInfo.parentAgentNo}" readonly="readonly" class="required"/>
                <a style="color: red;">0为顶级商户</a>
            </p>
            <p> 
                <label>商户名称：</label>
                <input name="agentName" type="text" size="30" value="${agentInfo.agentName }" class="required"/>
                <input name="id" type="hidden" value="${agentInfo.id}">
            </p>
            <p> 
                <label>来源系统：</label>
                <input name="sysSource" type="text" size="30" value="${agentInfo.sysSource}" readonly="readonly"  class="required"/>
            </p>
            <p>
                <label>手机号码：</label> 
                <input name="phoneNo" type="text"  size="30" value="${agentInfo.phoneNo}" readonly="readonly"  class="required"/>
            </p>
             <p>
                <label>银行帐号：</label>
                <input name="accountNo" type="text"  size="30" value="${agentInfo.accountNo}" class="required"/>
            </p>
             <p>
                <label>状态：</label>
                <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=AGENT_STATUS" selectedValue="${agentInfo.enabled }" relValue="columnKey"  relText="columnvalue" name="enabled">
			    </select>
            </p>
            <p>
                <label>账户名：</label>
                <input name="accountName" type="text"  size="30" value="${agentInfo.accountName }" class="required"/>
            </p>
            <p>
                <label>账户省份：</label>
                <input name="accountState" type="text"  size="30" value="${agentInfo.accountState }"/>
            </p>
            <p>
                <label>账户城市：</label>
                <input name="accountCity" type="text"  size="30"  value="${agentInfo.accountCity }"/>
            </p>
            <p>
                <label>账户国家：</label>
                <input name="accountContryCode" type="text"  size="30" value="${agentInfo.accountContryCode }"/>
            </p>
            <p>
                <label>开户行：</label>
                <input name="accountAddress" type="text"  size="30" value="${agentInfo.accountAddress }"/>
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