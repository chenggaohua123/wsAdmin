<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加汇率</title>
</head>
<script type="text/javascript">
	function getBankRate(){
		var rateType = $("#rateType").val();
		var sourceCurrency = $("#sourceCurrency").val();
		var targetCurrency = $("#targetCurrency").val();
		if(!sourceCurrency){
			alert("请填写原始币种");
			return false;
		}
		if(!targetCurrency){
			alert("请填写目标币种");
			return false;
		}
		var url="<%=path %>"+"/exchangRate/getBankRate?rateType="+rateType+"&sourceCurrency="+sourceCurrency+"&targetCurrency="+targetCurrency;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:'text',
			success:function(data){
				if(data){
					$("#rate").val(data);
				}else{
					alert("获取汇率失败，请确认该汇率是否存在");
				}
			}
		});
	}
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/exchangRate/addExchangRate" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <input type="hidden" name="operationType" value="insert">
        <div class="pageFormContent" layoutH="56">
        	<p>
							<label>银行名称：</label>
							<select class="combox" selectedValue="0"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
							</select>
						</p>
			<p>
				<label>组名：</label>
				<input name="groupName" type="text" size="30" class="required" />
			</p>
           <p>
                <label>中行汇率类型：</label>
				<select name="rateType" id="rateType">
				<option value="0">不同步中行汇率</option>
					<c:forEach items="${bankRateTypes }" var="bankRateType">
						<option value="${bankRateType}" >${funcs:getStringColumnKey('bankRateType',bankRateType,'未知状态')}</option>
					</c:forEach>
				</select>
            </p>
           <p>
                <label>原始币种：</label>
                <input name="sourceCurrency" id="sourceCurrency" type="text" size="30" class="required" />
            </p>
            <p>
                <label>目标币种：</label>
                <input name="targetCurrency" id="targetCurrency" type="text" size="30"  class="required"/>
            </p>
            <p>
                <label>汇率：</label>
                <input name="rate" id="rate" type="text" size="30" class="required number" style="width:22%;"/>
                <button type="button" onclick="getBankRate()">获取中行汇率</button>
            </p>
            <p>
                <label>偏移值：</label>
                <input name="offsetValue" type="text" size="30" class="required number"/>
            </p>
            <p>
             <label>类型：</label>
              <select name="type">
					<option value="bus">交易汇率</option>
					<option value="settle">结算汇率</option>
				</select>
            </p>
             <p>
             <label>是否有效：</label>
              <select name="enabled">
					<option value="1">有效</option>
					<option value="0">无效</option>
				</select>
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