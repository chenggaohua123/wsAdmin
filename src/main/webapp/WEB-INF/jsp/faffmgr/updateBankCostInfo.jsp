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
<title>添加修改银行成本录入</title>
<script type="text/javascript">
$(function(){
	var count = $('#count').val();
	var type = $('#bankCostType').val();
	if(type=='按笔数'){
		$('#count').addClass('required');
		$('#countP').css('display','block');
	}
	$('#bankCostType').change(function(){
		if($(this).val()=='按笔数'){
			$('#count').addClass('required');
			$('#countP').css('display','block');
			$('#count').val(count);
		}
		if($(this).val()=='按金额'){
			$('#count').removeClass('required');
			$('#countP').css('display','none');
			$('#count').val(null);
		}
	});
});
</script>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/faffmgr/saveBankCostInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input type="hidden" name="id" value="${info.id }"/>
            <p>
                <label>成本类型：</label>
                <input type="text" name="costType" value="${info.costType }" size="30"  class="required"/>
            </p>
            <p>
                <label>通道：</label>
                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" value="${info.currencyName }"/>
                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" class="required" value="${info.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
            </p>
              <p>
                <label>关联商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" value="${info.merNo }" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>关联终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" value="${info.terNo }" class="required number" readonly="readonly"/>
            </p>
            <p id="typeP">
                <label>收取类型：</label>
                <select name="type" id="bankCostType">
                	<option value="按金额" <c:if test="${info.type=='按金额' }">selected<</c:if>>按金额</option>
                	<option value="按笔数" <c:if test="${info.type=='按笔数' }">selected</c:if>>按笔数</option>
                </select>
            </p>
            <p style="display: none;" id="countP">
                <label>笔数：</label>
                <input type="text" name="count" id="count" value="${info.count }" size="30"/>
            </p>
            <p>
                <label>币种：</label>
                <select name="currency">
                	<option <c:if test="${info.currency=='CNY' }">selected</c:if> value="CNY">CNY</option>
                	<option <c:if test="${info.currency=='USD' }">selected</c:if> value="USD">USD</option>
                </select>
            </p>
            <p>
                <label>金额：</label>
                <input type="text" name="amount" value="${info.amount }" size="30"  class="required doubles"/>
            </p>
            <p>
                <label>扣款时间：</label>
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