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
<title>更新银行</title>
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
	<form method="post" action="<%=path %>/bankMgr/updateBankInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>银行名称：</label>
                <input name="bankName" type="text" value="${bankInfo.bankName }" size="30" class="required"/>
                <input name="id" type="hidden" value="${bankInfo.id }">
            </p>
            <p> 
                <label>主机端口：</label>
                <input name="port" type="text" value="${bankInfo.port}" size="30" class="required"/>
            </p>
            <p>
                <label>主机地址：</label>
                <input name="host" type="text" value="${bankInfo.host }" size="30" class="required"/>
            </p>
            <p>
                <label>是否关闭：</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=BANKSTATUS" relValue="columnKey"  relText="columnvalue" name="enabled">
			   </select> 
            </p>
             <p>
               <label>是否是使用代理</label>
                <select class="combox" id="isProxy" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=ISPROXY" relValue="columnKey" 
                 relText="columnvalue" name="isProxy" onchange="onchangeInfo()">
			   </select> 
            </p>
            <p>
                <label>代理主机地址：</label>
                <input name="proxyHost" id="proxyHost" type="text" value="${bankInfo.proxyHost}" size="30" class="required"/>
            </p>
            <p>
                <label>代理主机端口：</label>
                <input name="proxyPort" id="proxyPort" type="text" value="${bankInfo.proxyPort}" size="30" class="required"/>
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