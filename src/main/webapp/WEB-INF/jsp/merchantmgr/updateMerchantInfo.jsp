<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="funcs" uri="funcs"%> 
 <%
	String path = request.getContextPath();
%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新用户</title>
<script type="text/javascript">
    function queryAjax(phoneNo,picType,div,a){
    	if(a.isClick!="ok"){
    		$.ajax({
                type: "POST",
                url: "<%=path %>/merchantmgr/queryPicImg?phoneNo="+phoneNo+"&picType="+picType,
                async: false, //设为false就是同步请求
                cache: false,
                success: function (msg) {
                	var path="data:image/"+msg.picInfo.picExtName+";base64,"+msg.picInfo.picBuffer;
                	$("#"+div+" img").attr('src',path);
                	a.isClick = "ok";
               }
            });
    	}
    }
</script>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/updateMerchant" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<input name="id" type="hidden" value="${merchantInfo.id}" />
		<div class="pageFormContent" layoutH="50" >
            <p>
                <label>商户名称：</label>
                <input name="merchantName" type="text" value="${merchantInfo.merchantName }" size="30" class="required"/>
                <input name="merNo" type="hidden" value="${merchantInfo.merNo }">
            </p>
            <p>
                <label>商户电话：</label>
                <input name="phoneNo" type="text" value="${merchantInfo.phoneNo }"size="30" class="required"/>
            </p>
            <p>
                <label>商户联系人：</label>
                <input name="linkName" type="text" value="${merchantInfo.linkName }"size="30" class="required"/>
            </p>
            <p>
                <label>商户联系人电话：</label>
                <input name="linkPhoneNo" type="text" value="${merchantInfo.linkPhoneNo }"size="30" class="required"/>
            </p>
              <p>
                <label>邮箱：</label>
                <input name="email"   type="text" value="${merchantInfo.email}"  size="30" class="required"/>
            </p>
            <p>
                <label>QQ：</label>
                <input name="qq"   type="text" value="${merchantInfo.qq}"  size="30" class="required"/>
            </p>
            <p>
                <label>商户地址：</label>
                <input name="address"   type="text" value="${merchantInfo.address}"  size="30" class="required"/>
            </p>
            <p>
                <label>开户状态：</label>
                <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTSTATUS" relValue="columnKey" selectedValue="${merchantInfo.enabled }" relText="columnvalue" name="enabled">
			    </select>
            </p>
            <p>
                <label>登录状态：</label>
                <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANT_ACCOUNT_STATUS" relValue="columnKey" selectedValue="${merchantInfo.accountStatus }" relText="columnvalue" name="accountStatus">
			    </select>
            </p>
            <p>
                <label>直连状态：</label>
                <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANT_DIR_STATUS" relValue="columnKey" selectedValue="${merchantInfo.dirStatus }" relText="columnvalue" name="dirStatus">
			    </select>
            </p>
            <p>
                <label>销售人：</label>
                <input name="sales" type="text" value="${merchantInfo.sales}" size="30" class="required"/>
            </p>
            <p>
                <label>OA单号：</label>
                <input name="oaOrderNo" type="text" value="${merchantInfo.oaOrderNo}" size="30" class="required"/>
            </p>
             <p>
               <label>商户类别</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${merchantInfo.type }" relText="columnvalue" name="type">
			  </select>
            </p>
            <p>
                <label>开通日期：</label>
                <input name="activationDate"   type="text" value="${funcs:formatDate(merchantInfo.activationDate,'yyyy-MM-dd')}" dateFmt="yyyy-MM-dd" size="30" class="date"/>
            </p>
            <p>
                <label>失效日期：</label>
                <input name="expireDate"   type="text" value="${funcs:formatDate(merchantInfo.expireDate,'yyyy-MM-dd')}" dateFmt="yyyy-MM-dd" size="30" class="date"/>
            </p>
            
            <p>
              <label>行业</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=INDUSTRYTYPE" relValue="columnKey" selectedValue="${merchantInfo.industry }" relText="columnvalue" name="industry">
			  </select>
            </p>
            
            <p>
              <label>通道类型</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHNAT_CURRENCY_TYPE" relValue="columnKey" selectedValue="${merchantInfo.merCurrencyType }" relText="columnvalue" name="merCurrencyType">
			  </select>
            </p>
            <p>
                <label>实收历史费用：</label>
                <input name="topRate"   type="text" value="${merchantInfo.topRate}"  size="30" class=""/>
            </p>
             <p>
                <label>备注信息：</label>
                <input name="remark"   type="text" value="${merchantInfo.remark}"  size="30" class=""/>
            </p>
            <p>
                <label>商户地址：</label>
                <input name="merchantAddress"   type="text" value="${merchantInfo.merchantAddress}"  size="30" class=""/>
            </p>
            <p>
                <label>SWIFT：</label>
                <input name="swift"   type="text" value="${merchantInfo.swift}"  size="30" class=""/>
            </p>
            <p>
                <label>银行地址：</label>
                <input name="bankAddress"   type="text" value="${merchantInfo.bankAddress}"  size="30" class=""/>
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