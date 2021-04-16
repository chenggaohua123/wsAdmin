<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="funcs" uri="funcs" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加账户结算条件</title>
</head>
<body>
<script type="text/javascript">
$(document).ready(function(){
	$("#enabled").change(function(){
		if($("#enabled").val()==0){
			$("#amount").hide();
			$("#currency").hide();
		}else{
			$("#amount").show();
			$("#currency").show();
		}
	});
});
	
</script>
<div class="pageContent">
	<form method="post" action="<%=path %>/settlemgr/updateExceptionSettleTypeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="id" value="${info.id }" >
        <div class="pageFormContent" layoutH="56">
         <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required" value="${info.merNo } "readonly="readonly"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required" value="${info.terNo }" readonly="readonly"/>
            </p>
             <p>
                <label>银行名称：</label>
               <select class="combox" selectedValue="${info.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
							</select>
            </p>
             <p>
            	<label>卡种：</label>
				<select id="cardType" class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=cardType" relValue="columnKey"  
				selectedValue="${info.cardType }" relText="columnvalue" name="cardType">
				<option value="">所有</option>
				</select>
            </p>
            <p>
            	<label>条件类型：</label>
				<input type="text" value="${funcs:getStringColumnKey('GATHERTYPE',info.gatherType,'未知状态')}" readonly="readonly" >
            </p>
            <p>
                <label>收取类型：</label>
                <select name="enabled" id="enabled"  class="combox"  >
                <option value="1" ${info.enabled==1?'selected':'' }>有效</option>
                <option value="0" ${info.enabled==0?'selected':''}>无效</option>
				</select>
            </p>
            <c:if test="${info.enabled==1}">
            	 <p id="currency">
                <label>收取币种：</label>
                 <input name="currency"  id="currency" type="text" size="30" class="required" readonly="readonly" value="${info.currency }"/>
	            </p>
	            <p id="amount">
	                <label>收取金额：</label>
	               <input name="amount" type="text" size="30" class="required number" value="${info.amount }"/>
	            </p>
            </c:if>
            <c:if test="${info.enabled==0}">
            <p id="currency" style="display: none;">
                <label>收取币种：</label>
                 <input name="currency"  id="currency" type="text" size="30" class="required" readonly="readonly" value="${info.currency }"/>
            </p>
            <p id="amount"  style="display: none;">
                <label>收取金额：</label>
               <input name="amount" type="text" size="30" class="required number"  value="${info.amount }"/>
            </p>
            </c:if>
            <c:if test="${info.gatherType==2 }">
              <p id="isAllOrOver" >
                <label>收取类型：</label>
                <select name="isAllOrOver" id="isAllOrOver">
                <option value="0" ${info.isAllOrOver==0?'selected':''}>部分收取</option>
                <option value="1" ${info.isAllOrOver==1?'selected':''}>全部收取</option>
				</select>
            </p>
              <p id="disRate" >
                <label>百分比条件：</label>
               <input name="disRate" type="text" size="30" class="required number" value="${info.disRate>0.0?info.disRate:0 }"/>
              <br><br><font color='red'>注：百分比收取方式按照商户号总拒付率计算收取。例如输入5%，当商户号中拒付率大于5%时，则该条件才生效。</font>
            </p>
            </c:if>
            <c:if test="${info.gatherType==4 }">
             <p id="isAllOrOver1" >
                <label>收取类型：</label>
                <select name="refundReturn">
                <option value="0" ${info.refundReturn==0?'selected':''}>部分返还</option>
                <option value="1" ${info.refundReturn==1?'selected':''}>部分不返还</option>
				</select>
            </p>
              <p id="refundHour"  >
                <label>有效时间：</label>
               <input name="refundHour" type="text" size="30" class="required number"  value="${info.refundHour}"/>
               <br><br><font color='red'>例:输入24，商户提交退款申请的时间与交易时间相差不超过24小时的时候，此条件才生效。</font>
            </p>
            </c:if>
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