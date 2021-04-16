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
<script type="text/javascript" src="<%=path%>/js/table.js"></script>
<title>修改工作记录</title>
</head>

<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/taskmgr/updateCardInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
				<p>
					<label>发卡行名称</label>
					<input type="text" id="bankName" name="bankName" value="${binInfo.bankName}" size="30" class="required" />
					<input type="hidden" name="id" value="${binInfo.id}">
				</p>
				<p>
					<label>卡名称</label>
					<input type="text" id="cardName" name="cardName" value="${binInfo.cardName}" size="30"   class="required"   />
				</p>
				<p>
					<label>卡bin</label>
					<input type="text" id="cardBin" name="cardBin" value="${binInfo.cardBin}"  size="30" class="required"   />
				</p>
				<p>
					<label>卡类型</label>
					<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDBINTYPE" relValue="columnKey" selectedValue="${binInfo.cardType }" relText="columnvalue" name="cardType">
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