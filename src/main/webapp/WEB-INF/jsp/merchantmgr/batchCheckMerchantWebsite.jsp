<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核商户网址</title>
</head>
<body>
<div class="pageContent" >
 <form method="post" action="<%=path %>/merchantmgr/batchCheckMerchantWebsite" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" layoutH="250" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>网址</th>
				<th>网站程序</th>
				<th>网站程序语言</th>
				<th>MCC</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="merWeb">
				<tr target="sid_role" rel="${merWeb.id }"    align="center">
					<td>${merWeb.merNo }</td>
					<td>${merWeb.terNo }</td>
					<td>${merWeb.merWebSite }</td>
					<td>${merWeb.merWebProgram }</td>
					<td>${merWeb.merWebLanguage }</td>
					<td>${merWeb.MCC }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
        <div class="pageFormContent" >
        	<input type="hidden" name="ids" value="${ids}">
        	<p>
        		 <label>MCC：</label>
                
                                <input name="MCC" type="text" id="mccValue" size="30"  readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getMccInfo" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="mccValue">查找带回</a>
        	</p>
        	 <p>
                <label>品牌：</label>
                
                                <input name="brand" type="text" id="brand" size="30"  readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getBrandInfo" width="1100" height="350"  rel="brand" mask="true" lookupGroup="" lookupPk="brand">查找带回</a>
            
            </p>
             <p>
                <label>产品：</label>
                 <input name="product" type="text" id="product" size="30"  readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getProductInfo" width="1100" height="350"  rel="product" mask="true" lookupGroup="" lookupPk="product">查找带回</a>
            </p>
            <p>
            <label>审批状态：</label>
			<select name="status">
				<%-- <option value="0" ${info.status=='0'?'selected':'' }>待审批</option> --%>
				<option value="1" ${info.status=='1'?'selected':'' }>审批通过</option>
				<option value="2" ${info.status=='2'?'selected':'' }>审批驳回</option>
				<option value="3" ${info.status=='3'?'selected':'' }>不允许交易</option>
			</select>
			</p>
            <p>
                <label>审核意见：</label>
                <input type="text" name="message" size="30">
            </p>
            <p>
                <label>备注：</label>
            	<input type="text" name="remark"  size="30">
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