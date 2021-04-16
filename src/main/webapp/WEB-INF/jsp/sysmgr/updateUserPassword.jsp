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
<title>修改密码</title>
</head>
<body>
<script type="text/javascript">
	function updateSucceed(JSON){
		if(JSON.errorType=="1"){
			alertMsg.info("原始密码错误！");
		}else if(JSON.errorType=="2"){
			alertMsg.info("重复密码输入错误！");
		}else if(JSON.errorType=="3"){
			alertMsg.info("修改失败");
		}else{
			alert("修改成功，请重新登录");
			location.href="<%=path %>/logout";
		}
	}
	var f2=false,f3=false;
	var regPass=/^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)(?![a-zA-Z0-9]+$)(?![a-zA-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9\\W_!@#$%^&*`~()-+=]+$)(?![0-9A-Z\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$/;
	$("#pass").blur(function(){
		var pass=$("#pass").val();
		if(!pass.match(regPass)){
			f2=false;
			$("#passErr").html('<font color=red style="line-height: 21px;">&nbsp;格式不正确</font>');
		}else{
			f2=true;
			$("#passErr").html("<font color=green></font>");
		}
	}); 
	$("#comfirpassword").blur(function(){
		var pass=$("#comfirpassword").val();
		if(!pass.match(regPass)){
			f3=false;
			$("#comfirpasswordErr").html('<font color=red style="line-height: 21px;">&nbsp;格式不正确</font>');
		}else if(pass != $("#pass").val()){//两次输入密码不一致。
			$("#comfirpasswordErr").html('<font color=red style="line-height: 21px;">&nbsp;两次密码不同</font>');
			f3=false;
		}
		else{
			f3=true;
			$("#comfirpasswordErr").html("<font color=green></font>");
		}
	}); 
	
	$("#regpwd").click(function(){
		if(f2&&f3){
			$("#regForm").submit();
		}
	})
</script>
<div class="pageContent">
	<form method="post" id="regForm" action="<%=path %>/sysmgr/updateUserPassword" class="pageForm required-validate" onsubmit="return validateCallback(this, updateSucceed);">
        <div class="pageFormContent" layoutH="56">
            <p style="width:460px;">
                <label>原始密码：</label>
                <input name="forPassword" type="text" size="30" class="required" />
            </p>
            <p style="width:460px;">
                <label>新密码：</label>
                <input id="pass" name="passWord" type="password" size="30"  class="required"  />
                <span id="passErr"></span>
            </p>
            <p style="width:460px;">
                <label>确认密码：</label>
                <input id="comfirpassword" name="rePassWord"  type="password"  size="30" class="required"   />
                <span id="comfirpasswordErr"></span>
            </p>
            <p style="width:460px;"><font color="red">&nbsp;注1：密码长度8-16，同时包含大写字母、小写字母、数字、特殊字符</font></p>
            <p style="width:460px;"><font color="red">&nbsp;注2：密码90天更换一次</font></p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button id="regpwd" type="button">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>