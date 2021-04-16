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
<title>添加银行信息</title>
</head>
<script type="text/javascript">
	function onchangeInfo(){
		var isProxy = $("#isProxy").val();
		if(0 == isProxy){
			$("#proxyHost").removeClass("required"); 
			$("#proxyPort").removeClass("required"); 
		}
		if(1 == isProxy){
			$("#proxyHost").addClass("required");
			$("#proxyPort").addClass("required");
		}
	}
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/bankMgr/addBankInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           <p>
                <label>银行名称：</label>
                <input name="bankName" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>主机地址：</label>
                <input name="host" type="text" size="30"  class="required"/>
            </p>
            <p>
                <label>主机端口：</label>
                <input name="port" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>是否是使用代理：</label>
               	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=ISPROXY" relValue="columnKey" 
               	 relText="columnvalue" name="isProxy" id="isProxy" onchange="onchangeInfo()">
			   </select> 
            </p>
            <p>
                <label>代理主机地址：</label>
                <input name="proxyHost" id="proxyHost" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>代理主机端口：</label>
                <input name="proxyPort" id="proxyPort" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>是否关闭：</label>
               	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTCONFIG" relValue="columnKey"  relText="columnvalue" name="enabled">
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