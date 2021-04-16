<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="pageContent">
	<form method="post" action="<%=request.getContextPath()%>/merchantmgr/saveMerchantPage" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="56">
		<c:choose>
			<c:when test="${form.id>0}">
				<p>
					<label>商户号：</label>${form.merNo}
				</p>
				<p>
					<label>终端号：</label>${form.terNo}
				</p>
			</c:when>
			<c:otherwise>
				<p>
					<label>商户号：</label>
					<form:input path="form.merNo" maxlength="50" cssClass="required"/>
				</p>
				<p>
					<label>终端号：</label>
					<form:input path="form.terNo" maxlength="50" cssClass="required"/>
				</p>
			</c:otherwise>
		</c:choose>
		<p>
			<label>支付页面：</label>
			<form:hidden path="form.pageId"/>
			<form:input path="form.pageName" maxlength="160" lookupGroup="" lookupPk="smIdLookup" readonly="readonly"/>
			<a class="btnLook" href="<%=request.getContextPath()%>/bankMgr/selectPaymentPage" lookupGroup="" lookupPk="smIdLookup">选择</a>
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