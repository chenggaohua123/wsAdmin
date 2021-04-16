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
<title>修改费率</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/ratemgr/updateRateInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="id" value="${rateInfo.id }">
				<div class="tabsContent pageFormContent" layoutH="56">
					<div>
				
						<p>
							<label>手续费费率：</label> <input name="merRate" type="text" size="30"
								value="${rateInfo.merRate }" class="required number" />
						</p>
						<%-- <p>
					        <label>通道名称：</label>
			                <input name="currencyName" type="text" readonly="readonly" id="currency.currencyName" size="30" class="required" value="${rateInfo.currencyName }"/>
			                <input name="currencyId" type="hidden" id="currency.currencyId" size="30" class="required" value="${rateInfo.currencyId }"/>
			                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="800" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
						 --%>
						 <p>
							<label>银行名称：</label>
							<select class="combox" selectedValue="${rateInfo.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
							</select>
						</p>
						<p>
							<label>保证金费率：</label> <input name="bondRate" type="text" size="30"
								value="${rateInfo.bondRate }" class="required number" />
						</p>
						<p>
							<label>单笔手续费：</label> <input name="singleFee" type="text" size="30"
								value="${rateInfo.singleFee }" class="required number" />
						</p>
						
						<p>
							<label>卡种：</label> <select class="combox" selectedValue="${rateInfo.cardType }"
								loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
								relValue="columnKey" relText="columnvalue" name="cardType">
							</select>
						</p>
						<p>
			               <label>国家：</label>
			                
			                                <input name="countrys" type="text" id="countryNameSimple" size="30"  value="${rateInfo.countrys }"/>
			                 <a class="btnLook" href="<%=path %>/riskmgr/queryCountryList" width="1100" height="350"  rel="countryNameSimple" mask="true" lookupGroup="" lookupPk="countryNameSimple">查找带回</a>
			            </p>
			            <p>
			               <label>是否欧元区：</label>
			               <select class="combox" name="isEUR">
			               <option value="0" ${rateInfo.isEUR==0?'selected':'' }>不是</option>
			               <option value="1" ${rateInfo.isEUR==1?'selected':'' }>是</option>
			               </select>
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