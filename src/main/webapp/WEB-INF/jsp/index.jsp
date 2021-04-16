<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path %>/dwz/themes/silver/style.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />
<title>wantpay system</title>
</head>
<body scroll="no">
	<div id="layout">
			<div id="header">
				<div class="headerNav">
					<%-- <a class="logo" href="javascript:void(0)">Logo</a> --%>
					<ul class="nav">
						<li><a href="<%=path%>/logout">欢迎 ${SESSION_AUTHENTICATION.realName } 登陆系统。</a></li>
						<li><a target="dialog" title="修改密码" width="520" height="320" mask="true" href="<%=path %>/sysmgr/goupdatePassword">修改密码</a><input type="hidden" id="resetpwd" value="${resetpwd}"></li>
						<li><a href="<%=path%>/logout">注销</a></li>
					</ul>
					<!-- ul class="themeList" id="themeList">
						<li theme="silver"><div class="selected">silver</div></li>
						<li theme="default"><div>blue</div></li>
						<li theme="green"><div>green</div></li>
						<li theme="purple"><div>purple</div></li>
						<li theme="azure"><div>天蓝</div></li>
					</ul-->
				</div>
			</div>
			
			<div id="leftside">
				<div id="sidebar_s">
					<div class="collapse">
						<div class="toggleCollapse"><div></div></div>
					</div>
				</div>
				<div id="sidebar">
					<div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>
				
					<div class="accordion" fillSpace="sideBar">
						<div class="accordionContent">
							<ul class="tree treeFolder" oncheck="kkk">
							<li></li>
							<c:forEach items="${USER_MENU_LIST }" var="menu"><c:if test="${menu.parentNo == 0}"><li><a>${menu.menuName }</a><ul><c:forEach items="${USER_MENU_LIST }" var="menu1"><c:if test="${menu1.parentNo == menu.menuNo }"><c:if test="${menu1.menuType == 1 }"><li><a target="navTab" href="${menu1.actionName }" rel="${menu1.menuNo }">${menu1.menuName}</a></li></c:if></c:if></c:forEach></ul></li></c:if></c:forEach>
						</ul>
						</div>
					</div>
					
				</div>
			</div>
			<div id="container">
				<div id="navTab" class="tabsPage">
					<div class="tabsPageHeader">
						<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
							<ul class="navTab-tab">
								<li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">My Home</span></span></a></li>
							</ul>
						</div>
						<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
						<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
						<div class="tabsMore">more</div>
					</div>
					<ul class="tabsMoreList">
						<li><a href="javascript:void(0)">My Home</a></li>
					</ul>
					<div class="navTab-panel tabsPageContent layoutBox">
						<div>
							<div class="accountInfo">
								<p><span>Welcome, ${SESSION_AUTHENTICATION.realName }</span></p>
							</div>
							
							<div class="pageFormContent" layoutH="80">
								<p>
									<label>用户名:<span class="unit">${SESSION_AUTHENTICATION.userName }</span></label>
								</p>
								<p>
									<label>姓名:<span class="unit">${SESSION_AUTHENTICATION.realName }</span></label>
								</p>
	
								<p>
									<label>电话:<span class="unit">${SESSION_AUTHENTICATION.phoneNo }</span></label>
								</p>
								<p>
									<label>Email:<span class="unit">${SESSION_AUTHENTICATION.email }</span></label>
								</p>
							</div>
	
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="footer">Copyright © 2020 <a href="#">wantspay Inc</a>. All Rights Reserved. </div>
		
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
			<script src="<%=path %>/dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
			<script type="text/javascript">
				$(function(){
					DWZ.init("<%=path %>/dwz/dwz.frag.xml", {
						loginTitle:"Login",	// 弹出登录对话框
						loginUrl:"<%=path %>/loginDialog",
						forwardUrl:"<%=path %>/index",
						pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, 
						debug:true,	// 调试模式 【true|false】
						callback:function(){
							initEnv();
							$("#themeList").theme({themeBase:"<%=path %>/dwz/themes"});
						}
					});
				});
		</script>
	</body>
</html>