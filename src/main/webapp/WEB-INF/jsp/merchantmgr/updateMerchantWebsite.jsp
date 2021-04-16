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
 <form method="post" action="<%=path %>/merchantmgr/updateMerchantWebsite" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
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
                <input name="merWebSite" type="text" size="30" value="${info.merWebSite }"   class="required" />
            </p>
            
             <p>
                <label>网站语言：</label>
                <input name="merWebLanguage" type="text"  size="30" value="${info.merWebLanguage }"   class="required" />
            </p>
             <p>
                <label>网站程序：</label>
                <input name="merWebProgram" type="text"  size="30" value="${info.merWebProgram }"   class="required" />
            </p>
            <p>
                <label>MCC：</label>
                
                                <input name="MCC" type="text" id="mccValue" size="30"  readonly="readonly" value='${info.MCC}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getMccInfo" width="1100" height="350"  rel="relUpdateMerchantWebsite" mask="true" lookupGroup="" lookupPk="mccValue" >查找带回</a>
            </p>
             <p>
                <label>品牌：</label>
                
                                <input name="brand" type="text" id="brand" size="30"  value='${info.brand}' readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getBrandInfo" width="1100" height="350"  rel="relUpdateMerchantWebsite" mask="true" lookupGroup="" lookupPk="brand">查找带回</a>
            
            </p>
             <p>
                <label>产品：</label>
          		  <input name="product" type="text" id="product" size="30"  readonly="readonly" value='${info.product}'/>
                 <a class="btnLook" href="<%=path %>/merchantmgr/getProductInfo" width="1100" height="350"  rel="relUpdateWebSite" mask="true" lookupGroup="" lookupPk="product">查找带回</a>
            </p>
             <!--p>
            <label>是否风控：</label>
			<select name="isRiskCode">
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
			</p-->
			 <p>
               	<label>webSiteId：</label>
            	  <input type="text" name="webSiteId" size="30"  value="${info.webSiteId }" >
            </p>
			 <p>
               	<label>英文账单：</label>
            	  <input type="text" name="merAcquirer" size="30"  value="${info.merAcquirer }" >
            </p>
             <p>
               	<label>英文账单-Country：</label>
            	  <input type="text" name="merAcquirerCountry" size="30"  value="${info.merAcquirerCountry }" >
            </p>
             <p>
               	<label>英文账单-City：</label>
            	  <input type="text" name="merAcquirerCity" size="30"  value="${info.merAcquirerCity }" >
            </p>
             <p>
               	<label>英文账单-Company：</label>
            	  <input type="text" name="merAcquirerCompany" size="30"  value="${info.merAcquirerCompany }" >
            </p>
            <p>
            <label>审批状态：</label>
			<select name="status" class="combox">
				<option value="1" ${info.status=='1'?'selected':'' }>审批通过</option>
				<option value="2" ${info.status=='2'?'selected':'' }>审批驳回</option>
				<option value="3" ${info.status=='3'?'selected':'' }>不允许交易</option>
			</select>
			</p>
             <p>
                <label>审核意见：</label>
                <input type="text" name="message" class="required" size="30">
            </p>
             <p>
                <label>备注：</label>
            	   <input type="text" name="remark" size="30">
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