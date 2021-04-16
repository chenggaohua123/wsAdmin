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
<title>生成汇率审核信息</title>
</head>
<script type="text/javascript">
	function changeGroupNames(path){
		$.ajax({
			url:path+'/exchangRate/queryRateGroupNamesByType?type='+$("#type").val(),
			type:'post',
			dataType:'json',
			data:'text',
			success:function(data){
				 $("#groupName").empty();
				for(var i=0;i<data.length;i++){
					var option = $("<option>").text(data[i]).val(data[i]);
				 	$("#groupName").append(option);
				}
			}
		});	
	}
	
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/exchangRate/createCheckBankSourceRate" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<p>
                <label>银行汇率类型：</label>
				<select name="bankRateType" >
					<c:forEach items="${bankRateTypes }" var="bankRateType">
						<option value="${bankRateType }" >${funcs:getStringColumnKey('bankRateType',bankRateType,'未知状态')}</option>
					</c:forEach>
				</select>
            </p>
           <p>
                <label>原始币种：</label>
                <select name="sourceCurrency" >
                  	<option value="">所有</option>
					<c:forEach items="${sourceCurrencys }" var="targetCurrency">
					<option value="${targetCurrency }" >${targetCurrency }</option>
					</c:forEach>
				</select>
            </p>
            <p>
                <label>目标币种：</label>
                  <select name="targetCurrency" >
               		<option value="">所有</option>
					<c:forEach items="${targetCurrencys }" var="targetCurrency">
					<option value="${targetCurrency }" >${targetCurrency }</option>
					</c:forEach>
				</select>
            </p>
            <p>
                <label>商户汇率类型：</label>
                <select name="merRateType"  onchange="changeGroupNames('<%=path %>')" id="type">
					<option value="">所有</option>
					<option value="bus" >交易汇率</option>
					<option value="settle">结算汇率</option>
				</select>
            </p>
            <p>
             <label>汇率组：</label>
              <select name="groupName"  id="groupName" >
             	 <option value="">所有</option>
                <c:forEach items="${groupNames }" var="groupName" >
                <option value="${groupName }">${groupName }</option>
                </c:forEach>
				</select>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">生成</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>