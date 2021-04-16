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
	<form method="post" action='<%=path %>/sysmgr/saveBankRespMsgMgrInfo' class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <input type="hidden" name="id" value="${info.id }">
            <p>
                <label>银行返回信息：</label>
                 <input name="bankRespMsg" type="text" size="30" class="required" value="${info.bankRespMsg }"/>
            </p>
            <p>
                <label>自定义信息：</label>
                <input name="respMsg" type="text" size="30" class="required" value="${info.respMsg }"/>
            </p>
             <p>
                <label>原因解释：</label>
                <input name="remark" type="text" size="30" class="required" value="${info.remark }"/>
            </p>
             <p>
                <label>建议：</label>
                <input name="suggest" type="text" size="30" class="required" value="${info.suggest }"/>
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