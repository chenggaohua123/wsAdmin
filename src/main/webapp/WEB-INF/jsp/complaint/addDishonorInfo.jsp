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
<title>添加调查单信息</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/complaint/addComplaintInfo1" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input name="complaintType" type="hidden" id="cId">
        	<input name="status" type="hidden" value="0">
        	<input name="type" type="hidden" value="1">
            <p>
                <label>流水号：</label>
                <input name="tradeNo" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>拒付金额：</label>
                <input name="amount" type="text" size="30" class="required number" value="0"/>
            </p>
            <p>
                <label>拒付通知日期：</label>
                <input type="text" name="complaintDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date required"/>
            </p>
             <p>
                <label>拒付原因：</label>
                <input name="complaintTypeValue" type="text" size="30" id="cValue" readonly="readonly" class="required"/><a class="btnLook"
								href="<%=path %>/complaint/complaintListBack?type=1" width="1100"
								height="350" rel="rateRelMerchant" mask="true" lookupGroup=""
								lookupPk="complaintTypeValue">查找带回</a>
            </p>
             <p>
                <label>处理截止日期：</label>
                <input type="text" name="deadline" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date required"/>
            </p>
             <p>
                <label>CPD日期：</label>
                <input type="text" name="CPDDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date required"/>
            </p>
             <p>
                <label>商户是否可见：</label>
                <select name="isMerchantSee" class="combox">
                	<option value="0">可见</option>
                	<option value="1">不可见</option>
                </select>
            </p>
               <p>
                <label>特殊拒付：</label>
                <select name="isSp" class="combox">
                	<option value="0">否</option>
                	<option value="1">是</option>
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