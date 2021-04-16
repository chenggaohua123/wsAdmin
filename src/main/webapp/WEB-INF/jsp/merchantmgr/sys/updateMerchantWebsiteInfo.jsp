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
        <div class="pageFormContent" layoutH="50">
        	<input type="hidden" name="id" value="${info.id }">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" value="${info.merNo }" size="30"  class="required" readonly="readonly"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" type="text" value="${info.terNo }" size="30"  class="required" readonly="readonly"/>
            </p>
            <p>
                <label>网址：</label>
                <input name="merWebSite" type="text" size="30" value="${info.merWebSite }"   class="required"/>
            </p>
             
             <p>
                <label>网站语言：</label>
                <input name="merWebLanguage" type="text"  size="30" value="${info.merWebLanguage }"   class="required"/>
            </p>
              <p>
                <label>MCC：</label>
                
                                <input name="MCC" type="text" id="mccValue" size="30"  readonly="readonly" value='${info.MCC}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getMccInfo" width="1100" height="350"  rel="<%=path %>/merchantmgr/updateMerchantWebsiteInfo" mask="true" lookupGroup="" lookupPk="mccValue">查找带回</a>
            </p>
             <p>
                <label>品牌：</label>
                
                  <input name="brand" type="text" id="brand" size="30"  value='${info.brand}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getBrandInfo" width="1100" height="350"  rel="relUpdateWebSite" mask="true" lookupGroup="" lookupPk="brand">查找带回</a>
            
            </p>
             <p>
                <label>产品：</label>
                 <input name="product" type="text" id="product" size="30"  readonly="readonly" value='${info.product}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getProductInfo" width="1100" height="350"  rel="relUpdateWebSite" mask="true" lookupGroup="" lookupPk="product">查找带回</a>
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
</div>
</body>
</html>