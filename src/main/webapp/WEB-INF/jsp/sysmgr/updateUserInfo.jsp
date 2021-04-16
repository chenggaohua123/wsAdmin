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
<title>更新用户</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/sysmgr/updateUserInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>用 户 名：</label>
                <input name="userName" type="text" size="30" class="required" value="${userInfo.userName }"/>
            </p>
            <p>
                <label>密    码：</label>
                <input name="passWord" id="pWord" type="password"size="30"/>
            </p>
              <p>
                <label>联系电话：</label>
                <input name="phoneNo" readonly="readonly" type="text" value="${userInfo.phoneNo}" size="30" class=""/>
            </p>
            <p>
                <label>动态密钥：</label>
                <input name="seed" type="text" value="${userInfo.seed }" size="30" class="required"/>
            </p>
            <p>
                <label>真实姓名：</label>
                <input name="realName" type="text" value="${userInfo.realName }" size="30" class=""/>
            </p>
             <p>
	             <label>登录方式</label>
	             <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=VERTION" relValue="columnKey"  selectedValue="${userInfo.verificationType }"  relText="columnvalue" name="verificationType">
			     </select>
            </p>
            <p> 
               <label>是否关闭</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=USERSTATUS" relValue="columnKey"  selectedValue="${userInfo.enabled }"  relText="columnvalue" name="enabled">
			   </select>
            </p>
            <p>
               <label>选择系统</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=SYSTEMID" relValue="columnKey"  selectedValue="${userInfo.systemId }"  relText="columnvalue" name="systemId">
		       </select>
            </p>
            <p> 
                <label>邮    箱：</label>
                <input name="email" type="text" value="${userInfo.email}" size="30" class="email"/>
            </p>
            <p>
                <label>联系地址：</label>
                <input name="address" type="text" value="${userInfo.address}" size="30" class=""/>
                <input name="id" type="hidden" value="${userInfo.id}" />
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