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
<title>添加通道绑定记录</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/saveMerchantCurrencySpecialInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="id" value="${info.id }">
		<input type="hidden" name="merchantCurrencyId" value="${info.merchantCurrencyId }">
        <div class="pageFormContent" layoutH="56">
           <p>
                <label>单笔金额：</label>
                <input name="singleAmount" type="text" id="merNo" size="30" value="${info.singleAmount }"/>
            </p>
            <p>
                <label>是否自动：</label>
                <select class="combox" name="singleType" selectedValue="${info.singleType }">
                	<option value="0" ${info.singleType=='0'?'selected':'' }>不切换</option>
                	<option value="1" ${info.singleType=='1'?'selected':'' }>切换</option>
                </select>
            </p>
            
            <p>
                <label>日交易金额：</label>
                <input name="dayAmount" type="text" id="merNo" size="30" value="${info.dayAmount }"/>
            </p>
            <p>
                <label>是否自动：</label>
                <select class="combox" name="dayAmountType" selectedValue="${info.dayAmountType }">
                	<option value="0" ${info.dayAmountType=='0'?'selected':'' }>不切换</option>
                	<option value="1" ${info.dayAmountType=='1'?'selected':'' }>切换</option>
                </select>
            </p>
            
            <p>
                <label>日交易笔数：</label>
                <input name="dayCount" type="text" id="merNo" size="30" value="${info.dayCount }"/>
            </p>
            <p>
                <label>是否自动：</label>
                <select class="combox" name="dayCountType" selectedValue="${info.dayCountType }">
                	<option value="0" ${info.dayCountType=='0'?'selected':'' }>不切换</option>
                	<option value="1" ${info.dayCountType=='1'?'selected':'' }>切换</option>
                </select>
            </p>
            
            <p>
                <label>月交易金额：</label>
                <input name="monthAmount" type="text" id="merNo" size="30" value="${info.monthAmount }"/>
            </p>
            <p>
                <label>是否自动：</label>
                <select class="combox" name="monthAmountType" selectedValue="${info.monthAmountType }">
                	<option value="0" ${info.monthAmountType=='0'?'selected':'' }>不切换</option>
                	<option value="1" ${info.monthAmountType=='1'?'selected':'' }>切换</option>
                </select>
            </p>
            
            <p>
                <label>月交易笔数：</label>
                <input name="monthCount" type="text" id="merNo" size="30" value="${info.monthCount }"/>
            </p>
            <p>
                <label>是否自动：</label>
                <select class="combox" name="monthCountType" selectedValue="${info.monthCountType }">
                	<option value="0" ${info.monthCountType=='0'?'selected':'' }>不切换</option>
                	<option value="1" ${info.monthCountType=='1'?'selected':'' }>切换</option>
                </select>
            </p>
             <p>-----------------------------------规则互斥线------------------------------------------</p>
            <p></p>
             <p>
                <label>备选通道名称：</label>
                <a class="btnLook" href="<%=path %>/merchantmgr/getCurrencyListBrightBackMany?currencyIds=${info.currencyDayAmountIds }" width="740" height="700"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
                <input name="currencyDayAmountNames" type="text"  id="currency.currencyName" value="${info.currencyDayAmountNames }" readonly="readonly"/>
                <input name="currencyDayAmountIds" type="hidden" id="currency.currencyId" value="${info.currencyDayAmountIds }"/>
            </p>
            <p>
                <label>切换规则：</label>
                <select class="combox" name="currencyDayAmountType" selectedValue="${info.currencyDayAmountType }">
                	<option value="0" ${info.currencyDayAmountType=='0'?'selected':'' }>不切换</option>
                	<option value="1" ${info.currencyDayAmountType=='1'?'selected':'' }>顺序切换</option>
                	<option value="2" ${info.currencyDayAmountType=='2'?'selected':'' }>最少切换</option>
                </select>
            </p>
            
            <p><font color="red" style="font-size: 21px;">金额币种为${info.settleCurrency}(商户结算币种)，笔数为成功笔数<br>
            1、通道支持的币种需一致方可进行设置切换。<br>
			2、商户使用内嵌接口时，直连通道不能切换到3D通道。<br>
			3、商户使用内嵌接口时，直连3D通道不能切换到直连通道。<br>
			4、商户使用跳转我司支付页面接口时，不受3D和非3D通道切换限制。<br>
			5、内嵌接口直连通道根据通道交易限额进行切换配置时,依次判断可选通道id。英文逗号隔开，例如19,20,21<br>
			6、顺序切换规则：备用通道依次判断是否切换<br>
			7、最少切换规则：备用通道每日交易金额最少的通道，进行切换<br>
            </font></p>
            
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