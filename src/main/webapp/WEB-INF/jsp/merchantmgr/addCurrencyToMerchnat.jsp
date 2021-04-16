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
	<form method="post" action="<%=path %>/merchantmgr/addCurrencyToMerchnat" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" id="terNo" type="text" size="30" class="required number" readonly="readonly"/>
            </p>
            <p>
                <label>卡种：</label>
              <select class="combox"
								loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
								relValue="columnKey" relText="columnvalue" name="cardType">
			</select>
            </p>
            <p>
                <label>通道名称：</label>
                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" size="30" class="required"/>
                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" class="required"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
            </p>
            <p>
                <label>备用通道：</label>
                <input name="currencyName2" type="text" readonly="readonly" id="currency2.currencyName" size="30" />
                <input name="currencyId2" type="hidden" id="currency2.currencyId" size="30" />
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency2" lookupPk="bankId">查找带回</a>
            </p>
            <p>
                <label>欧元通道：</label>
                <input name="currencyName3" type="text" readonly="readonly" id="currency3.currencyName" size="30" />
                <input name="currencyId3" type="hidden" id="currency3.currencyId" size="30" />
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency3" lookupPk="bankId">查找带回</a>
            </p>
             <p>
                <label>是否可用：</label>
               	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CURRENCYSTATUS" relValue="columnKey"  relText="columnvalue" name="enabled">
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