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
<div class="pageFormContent" >
         <dl>
               <dt>商户号</dt>
			   <dd>${agentInfo.agentNo }</dd>
		</dl>
		  <dl>	   
			   <dt>父级代理号</dt>
			   <dd>${agentInfo.parentAgentNo }</dd>
		  </dl>
		  <dl> 
               <dt>商户名称</dt>
			   <dd>${agentInfo.agentName }</dd>
		</dl>
		  <dl>	   
			   <dt>手机号码</dt>
			   <dd>${agentInfo.phoneNo}</dd>
          </dl>
		  <dl>
               <dt>状态</dt>
			   <dd>${funcs:getStringColumnKey('AGENT_STATUS',agentInfo.enabled,'未知状态')}</dd>
		</dl>
		  <dl>	   
			   <dt>注册时间</dt>
			   <dd>${funcs:formatDate(agentInfo.regDate,'yyyy-MM-dd HH:mm')}</dd>
          </dl>
		  <dl>
               <dt>开通时间</dt>
			   <dd>${funcs:formatDate(agentInfo.activationDate,'yyyy-MM-dd HH:mm')}</dd>
		</dl>
		  <dl>	   
			   <dt>失效时间</dt>
			   <dd>${funcs:formatDate(agentInfo.expDate,'yyyy-MM-dd HH:mm')}</dd>
          </dl>
		  <dl>
               <dt>账户名</dt>
			   <dd>${agentInfo.accountName }</dd>
		</dl>
		  <dl>	   
			   <dt>账户省份</dt>
			   <dd>${agentInfo.accountState }</dd>
          </dl>
		  <dl>
               <dt>账户城市</dt>
			   <dd>${agentInfo.accountCity }</dd>
		</dl>
		  <dl>	  
			   <dt>账户国家</dt>
			   <dd>${agentInfo.accountContryCode }</dd>
          </dl>
		  <dl>
               <dt>开户行</dt>
			   <dd>${agentInfo.accountAddress }</dd>
		</dl>
		  <dl>	  
			   <dt>创建人</dt>
			   <dd>${agentInfo.createBy }</dd>
         </dl>
		  <dl>
               <dt>银行账号</dt>
			   <dd>${agentInfo.accountNo }</dd>
          </dl>
       </div>
	</div>
</body>
</html>