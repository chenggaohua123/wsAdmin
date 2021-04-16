<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<div class="pageContent">
	<form method="post" action='<%=path %>/sysmgr/batchUpdateCardPayLimit' class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <input type="hidden" name="id" value="${info.id }">
        	<p>
        		 <label>商户号：</label>
                 <input name="merNo" type="text" size="30" class="required" value="${info.merNo }"/>
        	</p>
        	<p>
        		 <label>终端号：</label>
                 <input name="terNo" type="text" size="30" class="required" value="${info.terNo }"/>
        	</p>
             <p>
                <label>支付次数限制：</label>
                <input name="countLimit" type="text" size="30" class="required" value="${info.countLimit }"/>
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