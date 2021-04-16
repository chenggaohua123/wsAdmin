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
	$(function(){
		var rateType= "${exchangRateInfo.rateType }";
		if(rateType){
			$("#rateType").val(rateType);
		}
	});

</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/exchangRate/updateExchangRate" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="id" value="${exchangRateInfo.id }">
       <input type="hidden" name="operationType" value="update">
        <div class="pageFormContent" layoutH="56">
        	<p>
							<label>银行名称：</label>
							<select class="combox" selectedValue="${exchangRateInfo.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
							</select>
						</p>
        	<p>
                <label>组名：</label>
                <input name="groupName" type="text" size="30" class="required" value="${exchangRateInfo.groupName }" readonly="readonly" />
            </p>
            <p>
                <label>中行汇率类型：</label>
				<select name="rateType" id="rateType">
                <option value="0"  ${exchangRateInfo.rateType=='0'?'selected':'' }>不同步中行汇率</option>
					<c:forEach items="${bankRateTypes }" var="bankRateType">
						<option value="${bankRateType}" ${exchangRateInfo.rateType==bankRateType?'selected':'' }>${funcs:getStringColumnKey('bankRateType',bankRateType,'未知状态')}</option>
					</c:forEach>
				</select>
            </p>
           	<p>
                <label>原始币种：</label>
                <input name="sourceCurrency" id="sourceCurrency" type="text" size="30" class="required" value="${exchangRateInfo.sourceCurrency }" />
            </p>
            <p>
                <label>目标币种：</label>
                <input name="targetCurrency" id="targetCurrency" type="text" size="30"  class="required" value="${exchangRateInfo.targetCurrency }"/>
            </p>
            <p>
                <label>汇率：</label>
                <input name="rate" id="rate" type="text" size="30" class="required number" value="${exchangRateInfo.rate }" style="width:22%;"/>
                <button type="button" onclick="getBankRate()">获取中行汇率</button>
            </p>
            <p>
                <label>偏移值：</label>
                <input name="offsetValue" type="text" size="30" class="required number" value="${exchangRateInfo.offsetValue }"/>
            </p>
            <p>
             <label>类型：</label>
              <select name="type">
					<option value="bus" ${exchangRateInfo.type=='bus'?'selected':'' }>交易汇率</option>
					<option value="settle" ${exchangRateInfo.type=='settle'?'selected':'' }>结算汇率</option>
				</select>
            </p>
             <p>
             <label>是否有效：</label>
              <select name="enabled">
					<option value="1" ${exchangRateInfo.enabled=='1'?'selected':'' }>有效</option>
					<option value="0" ${exchangRateInfo.enabled=='0'?'selected':'' }>无效</option>
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