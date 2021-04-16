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
<title>商户详细信息</title>
</head>
<body>
<div class="pageContent"  layoutH="0">
<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
	<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					   <li><span>商户信息</span></li>
					   <li onclick="document.getElementById('queryTerTransSetting').click()"><span>交易设置相关信息</span></li>
					</ul>
					<a style="display: none;" id="queryTerTransSetting" href="<%=path %>/merchantmgr/queryMerchantTransSettingBYmerNo?merNo=${merchant.merNo }" target="ajax" rel="jbsxBox3"></a>
				</div>
		</div>
		<div class="tabsContent pageFormContent" >
		<div>
	       	<dl>
	               <dt>商户号</dt>
				   <dd>${merchant.merNo }</dd>
			</dl>
			<dl>  	   
				   <dt>商户名称</dt>
				   <dd>${merchant.merchantName }</dd>
			</dl>
			<dl>	
	               <dt>商户联系人</dt>
				   <dd>${merchant.linkName }(${merchant.linkPhoneNo })</dd>
			</dl>
			<dl>	
	               <dt>商户电话</dt>
				   <dd>${merchant.phoneNo }</dd>
			</dl>
			<dl>	
	               <dt>商户邮箱</dt>
				   <dd>${merchant.email }</dd>
			</dl>
			<dl>	
	               <dt>QQ号</dt>
				   <dd>${merchant.qq }</dd>
			</dl>
			<dl>	
	               <dt>商户地址</dt>
				   <dd>${merchant.address }</dd>
			</dl>
			<dl>   
				   <dt>开户状态</dt>
				   <dd>${funcs:getStringColumnKey('MERCHANTSTATUS',merchant.enabled,merchant.enabled)}</dd>
	         </dl>
			 <dl>	
	               <dt>登录状态</dt>
				   <dd>${funcs:getStringColumnKey('MERCHANT_ACCOUNT_STATUS',merchant.accountStatus,merchant.accountStatus)}</dd>
			</dl>
			<dl>	
	               <dt>直连状态</dt>
				   <dd>${funcs:getStringColumnKey('MERCHANT_DIR_STATUS',merchant.dirStatus,merchant.dirStatus)}</dd>
			</dl>
			<dl>	
	               <dt>销售员</dt>
				   <dd>${merchant.sales}</dd>
			</dl>
			
			<dl>	
	               <dt>OA业务单号</dt>
				   <dd>${merchant.oaOrderNo}</dd>
			</dl>
			<dl>	   
				   <dt>注册时间</dt>
				   <dd>${funcs:formatDate(merchant.regDate,'yyyy-MM-dd HH:mm')} </dd>
	         </dl>
			 <dl>	
	               <dt>合同生效日期</dt>
				   <dd>${funcs:formatDate(merchant.activationDate,'yyyy-MM-dd HH:mm')} </dd>
			</dl>
			<dl>	   
				   <dt>合同失效时间</dt>
				   <dd>${funcs:formatDate(merchant.expireDate,'yyyy-MM-dd HH:mm')}</dd>
	         </dl>
			<dl>   
				   <dt>商户类别</dt>
				   <dd>${funcs:getStringColumnKey('MERCHANTTYPE',merchant.type,merchant.type)}</dd>
	         </dl>
	         <dl>   
				   <dt>商户等级</dt>
				   <dd>${funcs:getStringColumnKey('MERCHANT_LEV',merchant.merLev,merchant.merLev)}</dd>
	         </dl>
	         <dl>   
				   <dt>通道类型</dt>
				   <dd>${funcs:getStringColumnKey('MERCHNAT_CURRENCY_TYPE',merchant.merCurrencyType,merchant.merCurrencyType)}</dd>
	         </dl>
	         <dl>   
				   <dt>商户行业</dt>
				   <dd>${funcs:getStringColumnKey('INDUSTRYTYPE',merchant.industry,merchant.industry)}</dd>
	         </dl>
	         <dl>   
				   <dt>备注信息</dt>
				   <dd>${merchant.remark }</dd>
	         </dl>
	         <dl>   
				   <dt>是否MaxMind：</dt>
				   <dd>${merchantInfo.isMaxmind==0?'是':'否' }
               </dd>
	         </dl>
      </div>
      
		<div id="jbsxBox3">
		
		</div>
      		
      </div>
</div>
</div>
</body>
</html>