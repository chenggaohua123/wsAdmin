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
<title>修改运单状态</title>
</head>

<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/delivery/UpdateStatusAndRemark" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
				<p>
			    <label>货运查询状态：</label>
			    <input type="hidden" name="id" value="${deliveryInfo.id}">
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=LOGISTICS_STATUS" relValue="columnKey" selectedValue="${param.operationStatus}" relText="columnvalue" name="operationStatus">
					
				</select>
			
				</p><br/>
				<p>
					<label>备注</label>
					<input type="text" id="remark" name="remark" value="${deliveryInfo.remark}" size="30"   class="required"   />
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