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
<title>添加账户结算条件</title>
</head>
<body>
<script type="text/javascript">
$(document).ready(function(){
	$("#gatherType").change(function(){
		if($("#gatherType").val()==2){
			$("#disRate").show();
			$("#isAllOrOver").show();
			$("#isAllOrOver1").hide();
			$("#refundHour").hide();
			$("#disRateStep").hide();
		}else if($("#gatherType").val()==4){
			$("#disRate").hide();
			$("#isAllOrOver").hide();
			$("#refundHour").show();
			$("#disRateStep").hide();
			$("#isAllOrOver1").show();
		}else{
			$("#isAllOrOver").hide();
			$("#disRate").hide();
			$("#refundHour").hide();
			$("#disRateStep").hide();
			$("#isAllOrOver1").hide();
		}
	});
	$("#enabled").change(function(){
		if($("#enabled").val()==0){
			$("#amount").hide();
			$("#currency").hide();
		}else{
			$("#amount").show();
			$("#currency").show();
		}
	});
	$("#isAllOrOver2").change(function(){
		if($("#isAllOrOver2").val()==2||$("#isAllOrOver2").val()==3){
			$("#disRate").hide();
			$("#disRateStep").show();
			$("#amount").hide();
		}else{
			$("#disRate").show();
			$("#disRateStep").hide();
			$("#amount").show();
		}
	});
	$("#add").click(function(){
		if(parseFloat($("input[name='end1']").val())!=-1&&parseFloat($("input[name='end1']").val())>0&&parseFloat($("input[name='start2']").val())==0){
			$("#step2").show();
			$("input[name='start2']").val($("input[name='end1']").val());
			return;
		}
		if(parseFloat($("input[name='end2']").val())!=-1&&parseFloat($("input[name='end2']").val())>parseFloat($("input[name='start2']").val())&&parseFloat($("input[name='start3']").val())==0){
			$("#step3").show();
			$("input[name='start3']").val($("input[name='end2']").val());
			return;
		}
		if(parseFloat($("input[name='end3']").val())!=-1&&parseFloat($("input[name='end3']").val())>parseFloat($("input[name='start3']").val())&&parseFloat($("input[name='start4']").val())==0){
			$("#step4").show();
			$("input[name='start4']").val($("input[name='end3']").val());
			return;
		}
		if(parseFloat($("input[name='end4']").val())!=-1&&parseFloat($("input[name='end4']").val())>parseFloat($("input[name='start4']").val())&&parseFloat($("input[name='start5']").val())==0){
			$("#step5").show();
			$("input[name='start5']").val($("input[name='end4']").val());
			return;
		}
		alert("条件不符");
	});
});

	
</script>
<div class="pageContent">
	<form method="post" action="<%=path %>/settlemgr/addExceptionSettleTypeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="settleService" value="merchantAccountCashByTN" id="settleService">
        <div class="pageFormContent" layoutH="56">
         <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack?type=exceptionType" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required number" readonly="readonly"/>
            </p>
             <p>
                <label>银行名称：</label>
                <select class="combox" selectedValue="0"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
            </p>
           <p>
            	<label>卡种：</label>
				<select id="cardType" class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=cardType" relValue="columnKey"  relText="columnvalue" name="cardType">
				<option value="">所有</option>
				</select>
            </p>
            <p>
            	<label>条件类型：</label>
				<select id="gatherType" class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=GATHERTYPE" relValue="columnKey"  relText="columnvalue" name="gatherType">
				</select>
            </p>
            <p>
                <label>收取类型：</label>
                <select name="enabled" id="enabled">
                <option value="1">有效</option>
                <option value="0">无效</option>
				</select>
            </p>
            <p id="currency">
                <label>收取币种：</label>
                 <input name="currency"  id="currency" type="text" size="30" class="required" readonly="readonly"/>
            </p>
            <p id="amount">
                <label>收取金额：</label>
               <input name="amount" type="text" size="30" class="required number" value="0"/>
            </p>
             <p id="isAllOrOver" style="display: none;">
                <label>收取类型：</label>
                <select name="isAllOrOver" id="isAllOrOver2">
                <option value="0">部分收取</option>
                <option value="1">全部收取</option>
                <option value="2">阶梯收取</option>
                <option value="3">按阶梯收取全部订单</option>
				</select>
            </p>
             <p id="isAllOrOver1" style="display: none;">
                <label>收取类型：</label>
                <select name="refundReturn">
                <option value="0">部分返还</option>
                <option value="1">部分不返还</option>
				</select>
            </p>
              <p id="disRate" style="display: none;">
                <label>百分比条件：</label>
               <input name="disRate" type="text" size="30" class="required number" value="0"/>
              <br><br><font color='red'>注：百分比收取方式按照商户号总拒付率计算收取。例如输入5%，当商户号中拒付率大于5%时，则该条件才生效。</font>
            </p>
            <div id="disRateStep" style="display: none;">
           		 <p>
	                <label>
	                &nbsp;
	                </label>
	               <input type="text" size="8"   value="起" readonly="readonly" border="0"/>
	               <input type="text" size="8"   value="止,-1无穷大" readonly="readonly" border="0"/>
	               <input type="text" size="8"   value="金额" readonly="readonly" border="0"/>
	               <img alt="添加" id="add" src="<%=path%>/dwz/uploadify/img/add.png">
	            </p>
	            <p id="step1">
	                <label>阶梯一：</label>
	               <input name="start1" type="text" size="8" class="required number" value="0" alt="起" readonly="readonly"/>
	               <input name="end1" type="text" size="8" class="required number" value="-1" alt="止,-1无穷大"/>
	               <input name="stepAmount1" type="text" size="8" class="required number" value="0" alt="金额"/>
	            </p>
	             <p id="step2" style="display: none;">
	                <label>阶梯二：</label>
	               <input name="start2" type="text" size="8" class="required number" value="0" alt="起" readonly="readonly"/>
	               <input name="end2" type="text" size="8" class="required number" value="-1" alt="止,-1无穷大"/>
	               <input name="stepAmount2" type="text" size="8" class="required number" value="0" alt="金额"/>
	            </p>
	             <p id="step3" style="display: none;">
	                <label>阶梯三：</label>
	               <input name="start3" type="text" size="8" class="required number" value="0" alt="起" readonly="readonly"/>
	               <input name="end3" type="text" size="8" class="required number"  value="-1"  alt="止,-1无穷大"/>
	               <input name="stepAmount3" type="text" size="8" class="required number" value="0" alt="金额"/>
	            </p>
	             <p id="step4" style="display: none;">
	                <label>阶梯四：</label>
	               <input name="start4" type="text" size="8" class="required number" value="0" alt="起" readonly="readonly"/>
	               <input name="end4" type="text" size="8" class="required number" value="-1"   alt="止,-1无穷大"/>
	               <input name="stepAmount4" type="text" size="8" class="required number" value="0" alt="金额"/>
	            </p>
	             <p id="step5" style="display: none;">
	                <label>阶梯五：</label>
	               <input name="start5" type="text" size="8" class="required number" value="0" alt="起" readonly="readonly"/>
	               <input name="end5" type="text" size="8" class="required number" value="-1" alt="止,-1无穷大" readonly="readonly"/>
	               <input name="stepAmount5" type="text" size="8" class="required number" value="0" alt="金额"/>
	            </p>
            </div>
           
              <p id="refundHour"  style="display: none;">
                <label>有效时间：</label>
               <input name="refundHour" type="text" size="30" class="required number" value="0"/>
               <br><br><font color='red'>例:输入24，商户提交退款申请的时间与交易时间相差不超过24小时的时候，此条件才生效。</font>
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