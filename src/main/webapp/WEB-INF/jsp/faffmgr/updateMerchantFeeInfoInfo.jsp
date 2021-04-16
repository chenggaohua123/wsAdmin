<%@page import="org.springframework.web.context.request.RequestAttributes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改异常信息</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/faffmgr/updateMerchantFeeInfoInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input type="hidden" name="id" value="${info.id }"/>
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" value="${info.merNo }" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" value="${info.terNo }" class="required number" readonly="readonly"/>
            </p>
             <p>
                <label>关联通道：</label>
                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" value="${info.currencyName }"/>
                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" class="required" value="${info.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
            </p>
            <p>
                <label>收支类型：</label>
                 <select name="incomeType">
                	<option <c:if test="${info.incomeType=='0' }">selected</c:if> value="0">收入</option>
                	<option <c:if test="${info.incomeType=='1' }">selected</c:if> value="1">支出</option>
                </select>
            </p>
            <p>
                <label>费用类型：</label>
                <input type="text" name="feeType" value="${info.feeType }" size="30"  class="required"/>
            </p>
            <p>
                <label>金额：</label>
                <input type="text" name="amount" value="${info.amount }" size="30"  class="required doubles"/>
            </p>
            <p>
                <label>币种：</label>
                <select name="currency">
                	<option <c:if test="${info.currency=='CNY' }">selected</c:if> value="CNY">CNY</option>
                	<option <c:if test="${info.currency=='USD' }">selected</c:if> value="USD">USD</option>
                </select>
            </p>
            <p>
                <label>收入时间：</label>
                <input type="text" name="settleDate"  id = "settleDate" readonly="readonly" size="30" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${funcs:formatDate(info.settleDate,'yyyy-MM-dd HH:mm:ss')}"/>
            </p>
            <p>
                <label>备注：</label>
                 <input type="text" name="remark" value="${info.remark }" size="30"  class="required"/>
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