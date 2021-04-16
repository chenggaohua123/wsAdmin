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
	<form method="post" action="<%=path %>/faffmgr/updateIncomeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input type="hidden" name="id" value="${income.id }"/>
        	  <p>
                <label>通道：</label>
                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" value="${income.currencyName }"/>
                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" class="required" value="${income.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
            </p>
            <!-- p>
                <label>银行名称：</label>
                <input type="text" name="bankName" value="${income.bankName }" size="30"  class="required"/>
            </p -->
            <p>
                <label>收支类型：</label>
                	<form:select path="income.type">
                		<form:option value="0">收入</form:option>
                		<form:option value="1">支出</form:option>
                	</form:select>
            </p>
            <p>
                <label>收支名目：</label>
                <input type="text" name="incomeDesc" value="${income.incomeDesc }" size="30"  class="required"/>
            </p>
            <p>
                <label>金额：</label>
                <input type="text" name="amount" value="${income.amount }" size="30"  class="required doubles"/>
            </p>
            <p>
                <label>币种：</label>
                <select name="currency">
                	<option <c:if test="${income.currency=='CNY' }">selected</c:if> value="CNY">CNY</option>
                	<option <c:if test="${income.currency=='USD' }">selected</c:if> value="USD">USD</option>
                </select>
            </p>
            <p>
                <label>收支日期：</label>
                <input type="text" name="incomeDate"  id = "incomeDate" readonly="readonly" size="30" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${funcs:formatDate(income.incomeDate,'yyyy-MM-dd HH:mm:ss')}"/>
            </p>
            <p>
                <label>备注：</label>
                 <input type="text" name="remark" value="${income.remark }" size="30"  class="required"/>
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