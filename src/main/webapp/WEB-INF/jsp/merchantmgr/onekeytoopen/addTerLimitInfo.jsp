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
<title>添加终端配额</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/addTerLimit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p> 
                <label>终端号：</label>
                <input name="terNo" type="text" id="terNo" size="30" class="required" readonly="readonly"/>
            </p>
            <p>
                <label>单笔限额：</label>
                <input name="singleTransAmountLimit" type="text"  size="30"  class="required"/>
            </p>
            <p>
                <label>日限额：</label>
                <input name="dayTransAmountLimit" type="text"  size="30" class="required"/>
            </p>
             <p>
                <label>月限额：</label>
                <input name="monthTransAmountLimit" type="text"  size="30" class="required"/>
            </p>
            
            <!-- <p>
                <label>单卡交易次数：</label>
                <input name="transCount" type="text"  size="30" value="5" class="required"/>
            </p>
             <p>
                <label>单卡交易次数时间段：</label>
                <input name="transDate" type="text" value="24" size="30" class="required"/>
            </p> -->
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