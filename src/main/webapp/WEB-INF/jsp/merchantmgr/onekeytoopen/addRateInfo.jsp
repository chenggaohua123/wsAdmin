<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加费率</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/ratemgr/addRateInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
				<div class="tabsContent pageFormContent" layoutH="50">
					<div>
						<p>
							<label>商户号：</label> <input name="merNo" type="text" id="merNo"
								size="30" class="required number" readonly="readonly"/> <a class="btnLook"
								href="<%=path %>/ratemgr/getTerListBack" width="1100"
								height="350" rel="rateRelMerchant" mask="true" lookupGroup=""
								lookupPk="merNo">查找带回</a>
						</p>
						<p>
							<label>终端号：</label> <input name="terNo" type="text" id="terNo"
								size="30" class="required number" readonly="readonly"/>
						</p>
						
						<%-- <p>
					        <label>通道名称：</label>
			                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" size="30" class="required"/>
			                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" class="required"/>
			                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
						</p> --%>
						<p>
							<label>银行名称：</label>
							<select class="combox" selectedValue="0"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
							</select>
						</p>
						<p>
							<label>卡种：</label> <select class="combox"
								loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
								relValue="columnKey" relText="columnvalue" name="cardType">
								<option value="">所有</option>
							</select>
						</p>
						<p>
							<label>手续费费率：</label> <input name="merRate" type="text" size="30"
								class="required number" />
						</p>
						<p>
							<label>保证金费率：</label> <input name="bondRate" type="text" size="30" class="required number" />
						</p>
						<p>
							<label>单笔手续费：</label> <input name="singleFee" type="text"
								size="30" class="required number" />
						</p>
						<p>
						<font color='red'>注:单笔手续费币种默认以结算币种为准</font>
					</div>
				</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>