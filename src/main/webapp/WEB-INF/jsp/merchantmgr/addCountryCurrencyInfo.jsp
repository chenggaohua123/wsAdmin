<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户绑定用户</title>
</head>
<body>
<div class="pageContent" layoutH="0" style="width: 100%;">
 <form method="post" action="<%=path %>/merchantmgr/saveCountryCurrencyInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="60">
        	<p>
        		<label>商户类型：</label>
        		 <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${info.merType }" relText="columnvalue" name="merType" id="merType">
					<option value="-1">所有</option>
				</select>
        	</p>
            <p>
                <label>商户号：</label>
                <input name="id" id="id"  readonly="readonly" type="hidden" size="30" value="${info.id }"/>
                <input name="merNo" id="merNo"  readonly="readonly" type="text" size="30" class="required" value="${info.merNo }"/>
	            <a class="btnLook" href="<%=path %>/merchantmgr/queryMerchantMerAndTerNoList?type=1&merType={merType}" target="dialog" lookupGroup="" lookupPk="merNo"  mask="true"  width="750" height="400" warn="请选择商户类型">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" id="terNo"  readonly="readonly" type="text" size="30" class="required" value="${info.terNo }"/>
	            <a class="btnLook" href="<%=path %>/merchantmgr/queryMerchantMerAndTerNoList?type=2&merNo={merNo}" target="dialog" lookupGroup="" lookupPk="terNo"  mask="true"  width="750" height="400" warn="请选择商户号">查找带回</a>
            </p>
            <p>
                <label>卡种：</label>
            	<select name="cardType">
            		<option value="0" <c:if test="${info.cardType=='0'}">selected</c:if>>所有</option>
            		<option value="visa" <c:if test="${info.cardType=='visa'}">selected</c:if>>VISA</option>
					<option value="master" <c:if test="${info.cardType=='master'}">selected</c:if>>Master</option>
					<option value="jcb" <c:if test="${info.cardType=='jcb'}">selected</c:if>>JCB</option>
            	</select>
            </p>
            <p>
               <label>品牌(默认为全选)：</label>
               <input name="brand" type="text" id="brand" size="30" value="${info.brand }"/>
               <a class="btnLook" href="<%=path %>/merchantmgr/getBrandInfo" width="1100" height="350"  rel="brand" mask="true" lookupGroup="" lookupPk="brand">查找带回</a>
            </p>
            <p>
                <label>国家(默认为全选)：</label>
                <input name="countryCode" type="text" id="countryNameSimple" size="30" value="${info.countryCode }"/>
                <a class="btnLook" href="<%=path %>/riskmgr/queryCountryList" width="1100" height="350"  rel="countryNameSimple" mask="true" lookupGroup="" lookupPk="countryNameSimple">查找带回</a>
            </p>
            <p>
                <label>通道：</label>
                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" value="${info.currencyName }" size="30" class="required"/>
                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" value="${info.currencyId }" class="required"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
            </p>
            <p>
                <label>是否生效：</label>
                <select name="enabled">
                	<option ${info.enabled =='1'?'selected':'' } value="1">生效</option>
                	<option ${info.enabled =='0'?'selected':'' } value="0">不生效</option>
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