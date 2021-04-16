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
<title>修改商户结算周期</title>
</head>
<body>
<div class="pageContent" layoutH="0">
 <form method="post" action="<%=path %>/merchantmgr/updateMerchantSettleCycle" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <input type="hidden" name="id" value="${msc.id }">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text"  size="30"  class="required" value="${msc.merNo }"/>
            </p>
            <p>
                <label>结算次数：</label>
                <input name="settleCount" type="text"  size="30" class="required" value="${msc.settleCount }"/>
            </p>
            <p>
                <label>首次结算周期：</label>
                <input name="firstSettleT" type="text" id="agentNo" size="30"  class="required" value="${msc.firstSettleT }"/>
            </p>
             <p>
                <label>常规结算周期：</label>
                <input name="commonSettleT" type="text" id="agentNo" size="30"  class="required" value="${msc.commonSettleT }"/>
            </p>
             <p>
                <label>每月结算日期：</label>
                <input name="settleDay" type="text" id="agentNo" size="30"  class="required" value="${msc.settleDay }"/>
            </p>
             <p>
                <label>划款币种：</label>
                <input name="settleCurrency" type="text" id="agentNo" size="30"  class="required"  value="${msc.settleCurrency }"/>
            </p>
             <p>
                <label>备注：</label>
                <input name="remark" type="text" id="agentNo" size="30"  class="required"  value="${msc.remark }"/>
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