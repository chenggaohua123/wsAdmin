<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>wantspay Limited</title>
<link href="<%=path %>/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/dwz/fhtlogin/css/style.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery.easing.1.3.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/speedup.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.print.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.scrollCenter.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.util.number.js" type="text/javascript"></script>
<script src="<%=path %>/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	DWZ.init("<%=path %>/dwz/dwz.frag.xml", {
		loginTitle:"Login",	// 弹出登录对话框
		loginUrl:"<%=path %>/loginDialog",
		forwardUrl:"<%=path %>/index",
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, 
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"<%=path %>/dwz/themes"});
		}
	});
});

</script>
<script type="text/javascript">
	function keyUp()
	{
	    if (event.keyCode == 13)
	    {
	        event.returnValue=false;
	        event.cancel = true;
	        $("#loginButton").click();
	    }
	}
	function login(){
		if($("#userName").val() == ""){
			alertMsg.info("请输入用户名。");
			return false;
		}
		if($("#passWord").val() ==""){
			alertMsg.info("请输入密码。");
			return false;
		}
		if($("#vilidateCode").val() == ""){
			alertMsg.info("请输入动态码。");
			return false;
		}		
		$.ajax({
			   type: "POST",
			   url: "<%=path%>/dologin",
			   data: $("#loginForm").serialize(),
			   dataType:'json',
			   success: function(msg){
				   if(null != msg){
					   location.href="index";
				   }else{
					   alertMsg.info("登陆失败,请确认密码或者用户名是否正确。");					   
				   }
			   }
		});
		
	};
</script>
</head>
<body>
  <section class="container">
    <div class="login">
	    <div class="subtitle">
	    	<%-- <img src="<%=path %>/dwz/fhtlogin/img/fhtlogo.png"/> --%>
	    </div>
	    <form id="loginForm"  method="post" action="<%=path%>/dologin">
	        <table width="380px" >
	         <tbody>
	            <tr>
	                  <td class="td_title" width="40%"><label for="user">用户名:</label></td> 
	                  <td width="60%" style="text-align:left;"> <input class="texta" name="userName" type="text" maxlength=18  id="userName"  value="" /></td> 
	              </tr>
	              <tr>
	                  <td class="td_title"><label for="name">密  码:</label></td> 
	                  <td style="text-align:left;"> <input class="textb" name="passWord" type="passWord"  id="passWord" maxlength="18" value=""/></td> 
	              </tr>
	              <tr>
	                  <td class="td_title"><label for="password">动态密码:</label> </td>
	                  <td style="text-align:left;"> <input class="textc" name="vilidateCode" onkeyup="keyUp()" type="text" id="vilidateCode" maxlength="18" value=""/></td>
	               </tr>
	              <tr>
	              <td> </td>
	                  <td ><input type="button" class="button_login" id="loginButton" value="登 录" onclick="login()" />
	               </tr>   
	           <tbody>
	         </table>      
	    </form>
    </div>
</section>
    <div id="footer">
      <p>Copyright © 2019 <a href="#">wantspay Inc</a>. All Rights Reserved. </p>
    </div>
</body>
</html>