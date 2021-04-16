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
<title>商户出账提现</title>
</head>
<script type="text/javascript">
	function changeCashType(cashType,totalAmount,cashAmount,frozenAmount){
		if(cashType==1){
			$("#amount").text(cashAmount);
			$("#operatedAmount").val(cashAmount);
			$("#deductionType").hide();
		}else if(cashType==2){
			$("#amount").text(totalAmount);
			$("#operatedAmount").val(totalAmount);
			$("#deductionType").hide();
		}else if(cashType==3){
			$("#amount").text(frozenAmount);
			$("#operatedAmount").val(frozenAmount);
			$("#deductionType").hide();
			
		}else if(cashType==4){
			$("#amount").text(totalAmount);
			$("#operatedAmount").val(totalAmount);
			$("#deductionType").show();
			
		}else if(cashType==5){
			$("#amount").text(totalAmount);
			$("#operatedAmount").val(totalAmount);
			$("#deductionType").hide();
			
		}
	}
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/settlemgr/updateMerchantAccount" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="accountId" value="${info.id }">
        <input type="hidden" name="currency" value="${info.currency }">
        <input type="hidden" name="accountType" value="${info.accountType }">
        <input type="hidden" name="operatedAmount" id="operatedAmount" value="${info.cashAmount}">
        <div class="pageFormContent" layoutH="56">
         <p>
                <label>商户号：</label>
                <input name="merNo" type="text" size="30" class="required number" value="${info.merNo }" readonly="readonly"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" type="text" size="30" class="required number" value="${info.terNo }" readonly="readonly"/>
            </p>
             <p>
                <label>提现账户：</label>
				${info.accountType=='0'?'交易账户':'保证金账户' }
            </p>
            <p>
                <label>提现类型：</label>
				<select name="cashType" class="combox" onchange="changeCashType(this.value,${info.totalAmount },${info.cashAmount },${info.frozenAmount })" >
				<option value="1">提现</option>
				<option value="2">冻结</option>
				<option value="3">解冻</option>
				<option value="4">扣款</option>
				<option value="5">返款</option>
				</select>
            </p>
            <p id="deductionType" style="display: none;">
            	<label>扣款类型</label>
            	<select name=deductionType  class="combox">
            		<c:forEach items="${dtList }" var="dt">
            			<option value="${dt.deductionType }">${dt.deductionType}</option>
            		</c:forEach>
            	</select>
            </p>
            <p>
                <label>可操作金额：</label>
                <span id="currency">${info.currency }</span>&nbsp;<span id="amount" style="color:red;">${info.cashAmount}</span>
            </p>
            <p>
            	<label>操作金额：</label>
               <input name="operateAmount" type="text" size="30" class="required number" value="0.00"/>
               <span id="des"></span>
            </p>
            <p>
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