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
<title>白名单修改</title>
	<style type="text/css">
		#p_enableFlag{
		margin-top:25px;
	}
	</style>
</head>
<script type="text/javascript">
	$(function(){
		var rateType= "${exchangRateInfo.rateType }";
		if(rateType){
			$("#rateType").val(rateType);
		}
	});

</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/whiteListMagage/updateWhiteList" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="id" value="${whiteListInfo.id }">
       <input type="hidden" name="operationType" value="update">
        <div class="pageFormContent" layoutH="56">
        	
        	<p>
                <label>商户号：</label>
                <input name="merNo" id="merNo" type="text" size="30" class="required" value="${whiteListInfo.merNo}" readonly="readonly" />
            </p>
           
           	<p>
                <label>终端号：</label>
                <input name="terNo" id="terNo" type="text" size="30" class="required" value="${whiteListInfo.terNo}" />
            </p>
          
             <p>
             <label>规则类型：</label>
              <select name="blackType">
					<option value="email" ${whiteListInfo.blackType=='email'?'selected':'' }>邮箱</option>
					<option value="IP" ${whiteListInfo.blackType=='IP'?'selected':'' }>Ip地址</option>
					<option value="cardNo" ${whiteListInfo.blackType=='cardNo'?'selected':'' }>卡号</option>
					
				</select>
            </p>
            <p>
	         	<label>处理方式：</label>
	              <select name="type" class="required combox">
			           <option value="0" ${whiteListInfo.type=='0'?'selected':'' }>例外</option>
			           <option value="1" ${whiteListInfo.type=='1'?'selected':'' }>只接受</option>
			     </select>
	         </p>
            <p>
                <label>规则内容：</label>
                <input name="blackText" id="blackText" type="text" size="30"  value="${whiteListInfo.blackText}" style="width:22%;"/>
            </p>
          
            <p>
                <label>备注：</label>
               
           		<textarea name="remark"  style="width:22%;">${whiteListInfo.remark}</textarea>
            </p>
        	<p id="p_enableFlag">
             <label>状态：</label>
              <select name="enableFlag">
					<option value="1" ${whiteListInfo.enableFlag=='1'?'selected':'' }>开通</option>
					<option value="0" ${whiteListInfo.enableFlag=='0'?'selected':'' }>关闭</option>
				</select>
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