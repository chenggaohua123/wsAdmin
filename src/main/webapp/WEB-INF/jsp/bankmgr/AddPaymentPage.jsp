<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="pageContent">
	<form method="post" action="<%=request.getContextPath()%>/bankMgr/savePaymentPage" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<p>
			<label>页面名称：</label>
			<form:input path="form.pageName" maxlength="50" cssClass="required"/>
		</p>
		<p>
			<label>页面地址：</label>
			<form:input path="form.pageUrl" maxlength="100" cssClass="required"/>
		</p>
		<p>
			<label>状态：</label>
			<form:select path="form.status">
				<form:option value="1">正常</form:option>
				<form:option value="2">关闭</form:option>
			</form:select>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
	<form:hidden path="form.id"/>
	</form>
</div>