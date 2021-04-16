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
<title>Insert title here</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/bankMgr/updateCurrencyInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>选择银行：</label>
                <input name="bankId" type="hidden" id="bankId" size="30" class="required" value="${currencyInfo.bankId }"/>
                <input name="bankName" type="text" id="bankName" size="30" class="required" value="${currencyInfo.bankName }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getBankListbrighBack" width="500" height="350"  rel="addCurrency" mask="true" lookupGroup="" lookupPk="bankId">查找带回</a>
            </p>
            <p>
                <label>通道名称：</label>
                <input name="currencyName" type="text" size="30" value="${currencyInfo.currencyName }" class="required"/>
                <input name="id" type="hidden" size="30" value="${currencyInfo.id }" class="required"/>
            </p>
   
             <p>
                <label>商户号：</label>
                <input name="merchantNo" type="text" size="30" value="${currencyInfo.merchantNo }" class="required"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" type="text" size="30" value="${currencyInfo.terNo }" class="required number"/>
            </p>
            <p>
                <label>英文账单名称：</label>
                <input name="acquirer" type="text" size="30" value="${currencyInfo.acquirer }" class="required"/>
            </p>
             <p>
                <label>日限额：</label>
                <input name="topAmount"  type="number"  min="0"  size="30" value="${currencyInfo.topAmount }" class="required"   placeholder="0是没有限额"/>
            </p>
               <p>
                <label>通道所属：</label>
                <input name="currencyBelongs" type="text" size="30" value="${currencyInfo.currencyBelongs }" />
            </p>
              <p>
                <label>备注：</label>
                <input name="remark" type="text" size="30" value="${currencyInfo.remark }"  />
            </p>
             <p>
                <label>是否有效：</label>
               	<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTCONFIG" relValue="columnKey"  relText="columnvalue" selectedValue="${currencyInfo.enabled }"   name="enabled">
			    </select>
            </p>
           	<p><font color="red">1.日限额：为通道每日交易限额，币种为银行交易币种，默认0.0是没有限额,限额必须大于0才有效</font></p>
           
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