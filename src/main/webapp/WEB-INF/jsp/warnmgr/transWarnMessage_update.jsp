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
	<input type="hidden" id="path" value="<%=path%>"/>
	<div class="panelBar">
		<ul class="toolBar">
		    <li><a class="add" href="javascript:addTable()" mask="true"><span>添加程序名</span></a></li>
		    <li><a class="add" href="javascript:addMessage()" mask="true"><span>添加内容</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	 <form method="post" action="warnmgr/updateTransWarnRuleInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	    <input type="hidden" id="messageId" name="messageId" value="${requestScope.info.id }"/>
	    <input type="hidden" id="systemId" name="systemId" value="${requestScope.info.systemId }"/>
	    <input type="hidden" id="warnId" name="warnId" value="${requestScope.info.warnId }"/>
	    <input type="hidden" id="message" value="${requestScope.info.message }"/>
	    <div id="content" class="pageFormContent" layoutH="80">
	     	
	     </div>
	     <div class="formBar">
	         <ul>
	             <li>
	             	<div class="buttonActive"><div class="buttonContent"><button id="subMessage" type="button">保存</button></div></div>
	             </li>
	             <li>
	                 <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
	             </li>
	         </ul>
	     </div>
	 </form>
	</div>
</div>
<script type="text/javascript">
	 	var path = $('#path').val();
	 	function resplitMessage(str){
	 		var message = '';
	 		var spliIndex1 = str.indexOf('{');
	 		var spliIndex2 = str.indexOf('}');
	 		if(spliIndex1>0){
	 			message = str.substring(0, spliIndex1)
	 			if(message!=undefined && message!=null && message!=''){
	 				$('#content').append('<p><label>内容：</label><input type="text"size="20" name="tableContent" class="required" value="'+message+'"><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			var flag = str.substring(spliIndex1, spliIndex2+1)
	 			if(flag=='{bankName}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}" selected="selected">银行名</option><option value="{activeTime}">时间值</option><option value="{time}">笔数值</option><option value="{respMsg}">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			if(flag=='{activeTime}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}" selected="selected">时间值</option><option value="{time}">笔数值</option><option value="{respMsg}">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			if(flag=='{time}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}">时间值</option><option value="{time}" selected="selected">笔数值</option><option value="{respMsg}">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			if(flag=='{respMsg}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}">时间值</option><option value="{time}" selected="selected">笔数值</option><option value="{respMsg}" selected="selected">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 		}
	 		if(spliIndex1==0){
	 			var flag = str.substring(spliIndex1, spliIndex2+1)
	 			if(flag=='{bankName}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}" selected="selected">银行名</option><option value="{activeTime}">时间值</option><option value="{time}">笔数值</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			if(flag=='{activeTime}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}" selected="selected">时间值</option><option value="{time}">笔数值</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			if(flag=='{time}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}">时间值</option><option value="{time}" selected="selected">笔数值</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			if(flag=='{respMsg}'){
	 				$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}">时间值</option><option value="{time}" selected="selected">笔数值</option><option value="{respMsg}" selected="selected">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 		}
	 		if(!(spliIndex2>0)){
	 			if(str!=undefined && str!=null && str!=''){
	 				$('#content').append('<p><label>内容：</label><input type="text"size="20" name="tableContent" class="required" value="'+str+'"><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
	 			}
	 			return str;
	 		}
	 		var resMessage = resplitMessage(str.substring(spliIndex2+1, str.length+1));
	 		message+=",";
	 		message+=resMessage;
	 		return message;
	 	}
		$(function(){
			var messageInfo = $('#message').val();
			if(messageInfo!=undefined && messageInfo!=null && messageInfo!=''){
				resplitMessage(messageInfo);
			}
			$('#subMessage').click(function(){
				var str = 'id='+$('#messageId').val()+'&warnId='+$('#warnId').val()+'&systemId='+$('#systemId').val()+'&message=';
				 $("#content input[name='tableContent'],select[name='tableName']").each(function(){
						str+=$(this).val();
				});
				if(str.length>300){
					 alert("信息太长!");
				}
				$.ajax({
					url: path+'/warnmgr/updateMessageModelInfo',
					type:'post',
					data:str,
					dataType:'json',
					success:function(data){
						alert(data.message);
					}
				});
			});
		});
		function addTable(){
			$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{bankName}">银行名</option><option value="{activeTime}">时间值</option><option value="{time}">笔数值</option><option value="{respMsg}">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
		}
		function addMessage(){
			$('#content').append('<p><label>内容：</label><input type="text" name="tableContent" name="message" size="20" class="required"><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
		}
		function deleteElement(o){
			var $el = $(o);
			$el.parent().remove();
		}
	</script>
</body>
</html>