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
<title>添加商户网址</title>
</head>
<body>
<div class="pageContent" >
 <form method="post" action="<%=path %>/merchantmgr/addMerchantWebsite" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required number" readonly="readonly"/>
            </p>
            <p>
                <label>网址：</label>
                <input name="merWebSite" type="text" size="30"  class="required"/>
            </p>
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
                <label>网站程序：</label>
                <input name="merWebProgram" type="text" size="30"  class="required"/>
            </p>
             <p>
                <label>网站程序语言：</label>
                <input name="merWebLanguage" type="text"  size="30"  class="required"/>
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