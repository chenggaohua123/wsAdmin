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
<title>取消复核</title>
</head>
<script type="text/javascript">
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/settlemgr/cancelReCheckMerchantAccountAccess" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="id" value="${info.id }">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" size="30" class="required" value="${info.merNo }" readonly="readonly"/>
            </p>
             <p>
                <label>终端号：</label>
                <input name="terNo" type="text" size="30" class="required" value="${info.terNo }" readonly="readonly"/>
            </p>
             <p>
                <label>账户类型：</label>
               <select name="accountType" class='combox' disabled="disabled">
				<option value="0" ${info.accountType==0?'selected':'' }>交易账户</option>
				<option value="1" ${info.accountType==1?'selected':'' }>保证金账户</option>
				</select>
            </p>
            <p>
                <label>提现类型：</label>
				<select name="cashType" class='combox' disabled="disabled">
				<option value="1" ${info.cashType==1?'selected':'' }>提现</option>
				<option value="2" ${info.cashType==2?'selected':'' }>冻结</option>
				<option value="3" ${info.cashType==3?'selected':'' }>解冻</option>
				<option value="3" ${info.cashType==4?'selected':'' }>罚款</option>
				<option value="3" ${info.cashType==5?'selected':'' }>返款</option>
				</select>
            </p>	
             <p>
                <label>币种：</label>
               <input name="currency" type="text" size="30" value="${info.currency }" class="required" readonly="readonly"/>
            </p>
            <p>
            	<label>操作金额：</label>
            	<input name="amount" value="${info.amount }" size="30"  type="text" readonly="readonly" class="required" >
            </p>
             <p>
                <label>执行操作：</label>
               <select name="status" class="comBox" >
               <!-- <option value="3">复核通过</option> -->
               <option value="6" style="color:'red'" >取消复核(返回审核状态)</option>
               </select>
            </p>
            <p id="reCheckRemark" >
                <label>备注：</label>
               <input name="reCheckRemark" type="text" size="30" class="required"/>
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