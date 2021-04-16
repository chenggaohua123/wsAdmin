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
<title>修改商户汇率 定制</title>
</head>
<script type="text/javascript">
	function changeGroupNames(path){
		$.ajax({
			url:path+'/exchangRate/queryRateGroupNamesByType?type='+$("#type").val(),
			type:'post',
			dataType:'json',
			data:'text',
			success:function(data){
				 $("#groupName").empty();
				for(var i=0;i<data.length;i++){
					var option = $("<option>").text(data[i]).val(data[i]);
				 	$("#groupName").append(option);
				}
			}
		});	
	}
	
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/exchangRate/updateMerchantExchangRate" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <input type="hidden" name="id" value="${info.id }">
           <p>
                <label>商户号：</label>
                <input name="merNo" type="text" size="30" class="required" value="${info.merNo }" readonly="readonly"/>
            </p>
           <p>
                <label>终端号：</label>
                <input name="terNo" type="text" size="30" class="required" value="${info.terNo }" readonly="readonly"/>
            </p>
            <p>
             <label>类型：</label>
              <select name="type" onchange="changeGroupNames('<%=path %>')" id="type">
					<option value="bus" ${info.type=='bus'?'selected':'' }>交易汇率</option>
					<option value="settle" ${info.type=='settle'?'selected':'' }>结算汇率</option>
				</select>
            </p>
       		 <p>
                <label>组名：</label>
                <select name="groupName"  id="groupName">
                <c:forEach items="${list }" var="gName">
                <option value="${gName }" ${info.groupName==gName?'selected':'' }>${gName }</option>
                </c:forEach>
				</select>
            </p>
             <p>
             <label>是否有效：</label>
              <select name="enabled">
					<option value="1" ${info.enabled=='1'?'selected':'' }>有效</option>
					<option value="0" ${info.enabled=='0'?'selected':'' }>无效</option>
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