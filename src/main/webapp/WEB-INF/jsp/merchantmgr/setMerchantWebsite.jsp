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
<title>设置网站参数</title>
</head>
<body>
<div class="pageContent" >
 <form method="post" action="<%=path %>/merchantmgr/setMerchantWebsite" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" layoutH="146" style="text-align: center;">
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
				<tr target="sid_role" rel="${merWeb.id }" align="center">
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
                
                                <input name="MCC" type="text" id="mccValue" size="30"  readonly="readonly" value='${info.MCC}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getMccInfo" width="1100" height="350"  rel="relUpdateMerchantWebsite" mask="true" lookupGroup="" lookupPk="mccValue" >查找带回</a>
            </p>
             <p>
                <label>品牌：</label>
                
                                <input name="brand" type="text" id="brand" size="30"  readonly="readonly" value='${info.brand}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getBrandInfo" width="1100" height="350"  rel="relUpdateMerchantWebsite" mask="true" lookupGroup="" lookupPk="brand">查找带回</a>
            
            </p>
             <p>
                <label>产品：</label>
                 <input name="product" type="text" id="bpname" size="30"  readonly="readonly" value='${info.product}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getProductInfo" width="1100" height="350"  rel="relUpdateMerchantWebsite" mask="true" lookupGroup="" lookupPk="bpname">查找带回</a>
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