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
<title>修改商户预授权配置</title>
</head>
<script type="text/javascript">
	function onchangeInfo(){
		var isProxy = $("#isProxy").val();
		if(0 == isProxy){
			$("#proxyHost").removeClass("required"); 
			$("#proxyPort").removeClass("required"); 
		}
		if(1 == isProxy){
			$("#proxyHost").addClass("required");
			$("#proxyPort").addClass("required");
		}
	}
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantAuthroizeConfigInfo/updateMerchantAuthroizeConfigInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>商户号：</label>
                <%-- <input name="merNo" type="text" value="${merchantAuthroizeConfigInfo.merNo }" size="30" class="required" disabled="disabled"/> --%>
                <input name="merNo" type="text" value="${merchantAuthroizeConfigInfo.merNo }" size="30" class="required" readonly="readonly"/>
                <input name="id" type="hidden" value="${merchantAuthroizeConfigInfo.id }">
            </p>
            <p> 
                <label>终端号：</label>
                <%-- <input name="terNo" type="text" value="${merchantAuthroizeConfigInfo.terNo}" size="30" class="required" disabled="disabled"/> --%>
                <input name="terNo" type="text" value="${merchantAuthroizeConfigInfo.terNo}" size="30" class="required" readonly="readonly"/>
            </p>
            <p>
                <label>天数：</label>
                <select name="day"  relValue="columnKey" selectedValue="${merchantAuthroizeConfigInfo.day }" relText="columnvalue" >
               		<option value="0">0</option>
                	<option value="1">1</option>
                	<option value="2">2</option>
                	<option value="3">3</option>
                	<option value="4">4</option>
                </select>
            </p>
              <p>
                <label>状态：</label>
               <select name="enable" selectedValue="${merchantAuthroizeConfigInfo.enable }">
                	<option value="0">无效</option>
                	<option value="1">有效</option>
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