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
<title>添加商户汇率 定制</title>
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
					if(data[i]==0){
						continue;
					}
					var option = $("<option>").text(data[i]).val(data[i]);
				 	$("#groupName").append(option);
				}
			}
		});	
	}
	
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/exchangRate/addMerchantExchangRate" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required number" readonly="readonly"/>
            </p>
            <p>
             <label>类型：</label>
	              <select name="type" onchange="changeGroupNames('<%=path %>')" id="type">
					<option value="bus">交易汇率</option>
					<option value="settle">结算汇率</option>
				  </select>
            </p>
       		 <p>
                <label>组名：</label>
                <select name="groupName"  id="groupName">
                <c:forEach items="${bus }" var="groupName">
                <option value="${groupName }">${groupName }</option>
                </c:forEach>
				</select>
            </p>
             <p>
             <label>是否有效：</label>
              <select name="enabled">
					<option value="1">有效</option>
					<option value="0">无效</option>
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