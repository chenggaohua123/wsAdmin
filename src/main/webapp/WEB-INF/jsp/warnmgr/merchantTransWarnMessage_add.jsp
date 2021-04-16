<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加交易预警信息</title>
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
	   <input type="hidden" id="warnId" name="type" value="${requestScope.warnId }"/>
	   <input type="hidden" id="systemId" name="systemId" value="1"/>
	   <form method="post" action="warnmgr/addMessageModelInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	     <div id="content" class="pageFormContent" layoutH="80">
	         <p>
	             <label>程序名：</label>
	             <select name="tableName">
	             	<option value="{merNo}">商户号</option>
	             	<option value="{activeTime}">时间值</option>
	             	<option value="{time}">笔数值</option>
	             	<option value="{respMsg}">失败原因</option>
	             </select>
	             <a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="<%=path%>/dwz/uploadify/img/del.png"></a>
	         </p>
	         <p>
	             <label>内容：</label>
	             <input type="text" size="20" name="tableContent" class="required" value="">
	             <a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="<%=path%>/dwz/uploadify/img/del.png"></a>
	         </p>
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
		$(function(){
			$('#subMessage').click(function(){
				var str = 'warnId='+$('#warnId').val()+'&systemId='+$('#systemId').val()+'&message=';
				 $("#content input[name='tableContent'],select[name='tableName']").each(function(){
					str+=$(this).val();
				});
				if(str.length>300){
					 alert("信息太长!");
				}
				$.ajax({
					url: path+'/warnmgr/addMessageModelInfo',
					data : str,
					type:'post',
					dataType:'json',
					success:function(data){
						alert(data.message);
					}
				});
			});
		});
		function addTable(){
			$('#content').append('<p><label>程序名：</label><select name="tableName"><option value="{merNo}">商户号</option><option value="{activeTime}">时间值</option><option value="{time}">笔数值</option><option value="{respMsg}">失败原因</option></select><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
		}
		function addMessage(){
			$('#content').append('<p><label>内容：</label><input type="text" name="tableContent" size="20" class="required"><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="'+path+'/dwz/uploadify/img/del.png"></a></p>');
		}
		function deleteElement(o){
			var $el = $(o);
			$el.parent().remove();
		}
	</script>
</body>
</html>