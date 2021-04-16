<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="funcs" uri="funcs"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加费率</title>
</head>
<body>
	<div class="pageContent">
		<form method="post" action="<%=path %>/merchantmgr/copyMerchantTerInfo"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
				<div class="tabsContent pageFormContent" layoutH="50">
					<div>
						<p>
							<label>源商户号：</label> <input name="merNo" type="text" id="merNo"
								size="30" class="required number" readonly="readonly"/> <a class="btnLook"
								href="<%=path %>/ratemgr/getTerListBack" width="1100"
								height="350" rel="rateRelMerchant" mask="true" lookupGroup=""
								lookupPk="merNo" ref="asdasdsa">查找带回</a>
						</p>
						<p>
							<label>源终端号：</label> <input name="terNo" type="text" id="terNo"
								size="30" class="required number" readonly="readonly"/>
						</p>
						<p>
							<label>复制到</label> 
						</p>
						<p>
							<label>目标商户号：</label> <input name="newMerNo" type="text" 
								size="30" class="required number" readonly="readonly" value="${newTerInfo.merNo }"/> 
						</p>
						<p>
							<label>目标终端号：</label> <input name="newTerNo" type="text" 
								size="30" class="required number" readonly="readonly" value="${newTerInfo.terNo }"/>
						</p>
						<input type="hidden" name="id" value="${newTerInfo.id }">
					</div>
				</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>