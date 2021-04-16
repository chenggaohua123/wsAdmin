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
<title>批量修改通道绑定信息</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/batchUpdateCurrencyToMerchant" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack?type=exceptionType" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required number" />
            </p>
            <p>
                <label>原卡种：</label>
           		 <select class="combox"
					loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
					relValue="columnKey" relText="columnvalue" name="cardType">
				</select>
            </p>
            <p>
                <label>原通道名称：</label>
                <input name="oriCurrencyName" type="text" readonly="readonly" id="ori.currencyName" size="30" class="required"/>
                <input name="oriCurrencyId" type="hidden" id="ori.currencyId" size="30" class="required"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="ori" >查找带回</a>
            </p>
            <p>
                <label>新通道名称：</label>
                <input name="currencyName" type="text" readonly="readonly" id="currencyName" size="30" class="required"/>
                <input name="currencyId" type="hidden" id="currencyId" size="30" class="required"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="" lookupPk="bankId">查找带回</a>
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