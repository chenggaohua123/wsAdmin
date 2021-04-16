<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商户交易预警信息</title>
</head>
<body>
<div class="pageContent">
	 <form method="post" action="warnmgr/addTransWarnRuleInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div class="pageFormContent" layoutH="56">
	         <p>
	            <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
	         </p>
	         <p>
	             <label>监控条件：</label>
	             <select id="type" name="type" class="required combox">
			           <option value="0">交易返回信息</option>
			           <option value="1">连续交易失败</option>
			           <option value="2">无新增交易</option>
			           <option value="3">风险订单</option>
			     </select>
	         </p>
	         <p>
	             <label>监控时效：</label>
	             <input type="text" id="activeTime" name="activeTime" size="30" class="required number textInput"/>
	             <font style="color: red">单位为分钟</font>
	         </p>
	         <%--
	         <p>
	             <label>预警发送邮箱：</label>
	             <input type="text" id="emails" name="emails" size="30" class="required"/>
	         </p>
	          --%>
	         <p>
	             <label>预警发送间隔时间：</label>
	             <input type="text" id="cycle" name="cycle" size="30" class="required number textInput"/>
	             <font style="color: red">单位为分钟</font>
	         </p>
	         <p id="timesContent" style="display: block;">
	             <label id="timeName">笔数：</label>
	             <input type="text" id="time" name="time" size="30" class="required number textInput"/>
	             <font style="color: red">单位为笔</font>
	         </p>
	         <p id="respMsgContent" style="display: block;">
	             <label>失败原因：</label>
	             <input type="text" id="respMsg" name="respMsg" size="30" class="required"/>
	         </p>
	     </div>
	     <div class="formBar">
	         <ul>
	             <li>
	             	<div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
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
			$('#type').change(function(){
				var type = $('#type option:selected').val();
				if(type==0){
					$('#time').val(null);
					$('#respMsg').val(null);
					$('#respMsg').addClass('required');
					$('#time').addClass('required number textInput');
					$('#timesContent').css('display','block');
					$('#respMsgContent').css('display','block');
					$('#timeName').text('笔数: ');
				}
				if(type==1){
					$('#time').val(null);
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
				if(type==3){
					$('#time').val(null);
					$('#respMsg').val(null);
					$('#timesContent').css('display','block');
					$('#respMsgContent').css('display','none');
					$('#respMsg').removeClass('required');
					$('#time').addClass('required number');
				}
			});
		});
	</script>
</body>
</html>