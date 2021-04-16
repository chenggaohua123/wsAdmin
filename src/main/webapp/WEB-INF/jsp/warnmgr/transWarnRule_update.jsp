<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改交易预警信息</title>
</head>
<body>
<div class="pageContent">
	 <form method="post" action="warnmgr/updateTransWarnRuleInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	    <div class="pageFormContent" layoutH="56">
	    	<p>
	             <label>银行：</label>
	             <input type="hidden" id="bankId" name="bankId" value="${requestScope.warn.bankId }">
	             <input id="bankName" name="bankName" type="text" size="30" class="required" readonly="readonly" size="30" value="${requestScope.warn.bankName }"/>
	             <a class="btnLook" href="<%=path %>/warnmgr/queryBankList" target="dialog" lookupPk="bankName"  mask="true" lookupGroup=""  width="650" height="400">查找带回</a>
	         </p>
	         <p>
	             <label>监控条件：</label>
	             <select id="type" name="type" class="required combox">
			           <option value="0" <c:if test="${requestScope.warn.type=='0' }">selected</c:if> >交易返回信息</option>
			           <option value="1" <c:if test="${requestScope.warn.type=='1' }">selected</c:if> >连续交易失败</option>
			            <option value="2" <c:if test="${requestScope.warn.type=='2' }">selected</c:if> >无新增交易</option>
			     </select>
	         </p>
	         <p>
	             <label>监控时效：</label>
	             <input type="hidden" name="id" value="${requestScope.warn.id }"/>
	             <input type="text" id="activeTime" name="activeTime" value="${requestScope.warn.activeTime }" class="required number textInput" size="30" class="required"/>
	             <font style="color: red">单位为分钟</font>
	         </p>
	         <%--
	         <p>
	             <label>预警发送邮箱：</label>
	             <input type="text" id="emails" name="emails" value="${requestScope.warn.emails }" class="required" size="30" class="required"/>
	         </p>
	          --%>
	         <p>
	             <label>预警发送间隔时间：</label>
	             <input type="text" id="cycle" name="cycle" value="${requestScope.warn.cycle }" class="required number textInput" size="30" class="required"/>
	         	 <font style="color: red">单位为分钟</font>
	         </p>
	         <%--
	         <c:if test="${requestScope.warn.type==0 }">
	         </c:if>
	          --%>
	         <p id="timesContent">
	             <label>笔数：</label>
	            <input type="text" id="time" name="time" size="30" class="required number textInput" value="${requestScope.warn.time }"/>
	            <font style="color: red">单位为笔</font>
	         </p>
	         <p id="respMsgContent">
	             <label>失败原因：</label>
	            <input type="text" id="respMsg" name="respMsg" size="30" class="required" value="${requestScope.warn.respMsg }"/>
	         </p>
	         <%--
	         <c:if test="${requestScope.warn.type==1 }">
	         	<p id="timesContent">
	             	<label>失败笔数：</label>
	            	<input type="text" id="time" name="time" size="30" class="required number textInput" value="${requestScope.warn.time }"/>
	            	<font style="color: red">单位为笔</font>
	         	</p>
	         </c:if>
	          --%>
	     </div>
	     
	     <div class="formBar">
	         <ul>
	             <li>
	             	<div class="buttonActive"><div class="buttonConte nt"><button type="submit">保存</button></div></div>
	             </li>
	             <li>
	                 <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
	             </li>
	         </ul>
	     </div>
	 </form>
	</div>
<script type="text/javascript">
		$(function(){
			var time0 = null;
			var respMsg0 = null;
			var flag = $('#type option:selected').val();
			if(flag=="0"){
				time0 = $('#time').val();
				respMsg0 = $('#respMsg').val();
				$('#respMsg').addClass('required');
				$('#time').addClass('required number textInput');
				$('#timesContent').css('display','block');
				$('#respMsgContent').css('display','block');
				$('#timeName').text('笔数: ');
			}
			if(flag=="1"){
				time0 = $('#time').val();
				respMsg0 = $('#respMsg').val();
				$('#time').addClass('required number textInput');
				$('#timesContent').css('display','block');
				$('#respMsgContent').css('display','none');
				$('#respMsg').removeClass('required');
				$('#timeName').text('失败笔数: ');
			}
			if(flag=="2"){
				time0 = $('#time').val();
				respMsg0 = $('#respMsg').val();
				$('#timesContent').css('display','none');
				$('#respMsgContent').css('display','none');
				$('#respMsg').removeClass('required');
				$('#time').removeClass('required number');
			}
			$('#type').change(function(){
				var type = $('#type option:selected').val();
				if(type==0){
					$('#time').val(time0);
					$('#respMsg').val(respMsg0);
					$('#respMsg').addClass('required');
					$('#time').addClass('required number textInput');
					$('#timesContent').css('display','block');
					$('#respMsgContent').css('display','block');
					$('#timeName').text('笔数: ');
				}
				if(type==1){
					$('#time').val(time0);
					$('#respMsg').val(null);
					$('#time').addClass('required number textInput');
					$('#timesContent').css('display','block');
					$('#respMsgContent').css('display','none');
					$('#respMsg').removeClass('required');
					$('#timeName').text('失败笔数: ');
				}
				if(type==2){
					$('#time').val(null);
					$('#respMsg').val(null);
					$('#timesContent').css('display','none');
					$('#respMsgContent').css('display','none');
					$('#respMsg').removeClass('required');
					$('#time').removeClass('required number');
				}
			});
		});
</script>
</body>
</html>