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
	<form method="post" action="<%=path %>/settlemgr/updateDeductionType" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>发送邮件模板：</label>
                <select id="emailModel" name="emailModel">
					<option value="1">虚拟商户持卡人回访模板</option>
					<option value="2">正品商户持卡人回访模板</option>
					<option value="3">拒付订单持卡人回访模板</option>
					<option value="4">日语订单持卡人回访模板</option>
					<option value="6">社交网站持卡人回访模板</option>
				</select>
            </p>
            <p>
                <label>发送邮箱：</label>
                <select id="sendEmail" name="sendEmail">
	                <option value="auto@bringallpay.net">auto邮箱</option>
	                <option value="auto1@bringallpay.net">auto1邮箱</option>
                	<option value="auto2@bringallpay.net">auto2邮箱</option>
					<option value="auto3@bringallpay.net">auto3邮箱</option>
					<option value="auto4@bringallpay.net">auto4邮箱</option>
					<option value="auto5@bringallpay.net">auto5邮箱</option>
				</select>
            </p>
   
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" onclick="goUpload()">下一步</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
<script type="text/javascript">
function goUpload(){
	$.pdialog.reload("<%=path %>/transmgr/toUploadTransCallbackInfo?emailModel="+$('#emailModel').val()+"&sendEmail="+$('#sendEmail').val(), {emailModel:$('#emailModel').val(), sendEmail:$('#sendEmail').val()}, 'addCallback');
}
</script>
</body>
</html>