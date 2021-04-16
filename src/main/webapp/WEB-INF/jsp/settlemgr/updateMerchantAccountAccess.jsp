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
<title>商户出账提现审核</title>
</head>
<script type="text/javascript">
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/settlemgr/updateMerchantAccountAccess" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="cashType" value="${info.cashType }">
       <input type="hidden" name="totalAmount" value="${info.totalAmount }">
       <input type="hidden" name="bondAmount" value="${info.bondAmount }">
        <div class="pageFormContent" layoutH="56">
         <p>
                <label>虚拟账户：</label>
                <input name="accountID" type="text" size="30" class="required number" value="${info.accountID }" readonly="readonly"/>
            </p>
            <p>
                <label>流水号：</label>
                <input name="id" type="text" size="30" class="required" value="${info.id }" readonly="readonly"/>
            </p>
            <p>
                <label>提现类型：</label>
				<select disabled="disabled">
				<option value="1" ${info.cashType==1?'selected':'' }>提现</option>
				<option value="2" ${info.cashType==2?'selected':'' }>保证金提现</option>
				<option value="3" ${info.cashType==3?'selected':'' }>解冻</option>
				</select>
            </p>	
             <p>
                <label>币种：</label>
               <input name="currency" type="text" size="30" value="${info.currency }" class="required" readonly="readonly"/>
            </p>
            <p>
            	<label>操作金额：</label>
            	<c:if test="${info.cashType==1}">
            		<input name="cashAmount" value="${info.cashAmount }" size="30"  type="text" readonly="readonly" class="required" >
            	</c:if>
            	<c:if test="${info.cashType==2}">
            		<input name="bondCashAmount" value="${info.bondCashAmount }" size="30"  type="text" readonly="readonly" class="required" >
            	</c:if>
            	<c:if test="${info.cashType==3}">
            		<input name="frozenAmount" value="${info.frozenAmount }" size="30"  type="text" readonly="readonly" class="required" >
            	</c:if>
            </p>
             <p>
                <label>审核：</label>
               <select name="status" class="comBox" onchange="show(this.value)">
               <option value="1">审核通过</option>
               <option value="2">审核不通过</option>
               </select>
            </p>
            <p id="remark" >
                <label>备注：</label>
               <input name="remark" type="text" size="30" class="required"/>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit"  id="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>