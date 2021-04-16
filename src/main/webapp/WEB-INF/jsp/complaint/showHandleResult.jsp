<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	$(function(){
		var systemId = $("#systemId").val();
		if(!systemId){
			return;
		}
		if(1 == systemId){
			$("#addRemark").show();
			$("#showSubmit").show();
			$("#showClose").hide();
		}else{
			$("#addRemark").hide();
			$("#showSubmit").hide();
			$("#showClose").show();
		}
		
		$("#submitInfo").click(function(){
			var param = {};
			param.complaintId = $('#complaintId').val();
			param.result = $('#result').val();
			var url="<%=path %>"+"/complaint/addComplaintResultInfo";
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:param,
				success:function(data){
						alert(data.message);
						$.pdialog.close("showHandleResultPage");
					/* if(200 == data.statusCode){
						alert(">>>>>>><<<<<<<<" + JSON.stringify(data.message));
						var info = data.message;
						var str="";
						for(var i=0;i<info.length;i++){
							str+="<ul><li>&nbsp;&nbsp;"+info[i].createBy+"&nbsp;&nbsp;&nbsp;";
							str+="备注于："+info[i].createDate+"<br>-----------------------------------------------------------------------------------------------------<br>";
							str+=""+info[i].result+"<br>-----------------------------------------------------------------------------------------------------<br></li></ul>";
						}
						$("#showData").html(str);
						$("#result").val("");
					} */
	            }
			});
		})
	});
	
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉单处理结果</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="javaScript:viod(0);" class="pageForm required-validate">
        <input type="hidden" id="systemId" value="${systemId }">
        <input type="hidden" id="complaintId" name="complaintId" value="${complaintId }">
        <div id="showData" class="pageFormContent" layoutH='<c:if test="${systemId == '1'}">150</c:if><c:if test="${systemId == '2'}">56</c:if>'" style="width:100%;">
        	<c:forEach items="${resultList }" var="info">
	        	<ul>
	                <li>&nbsp;&nbsp;${info.createBy}&nbsp;&nbsp;&nbsp;备注于：${info.createDate}</li>
	                <li>-----------------------------------------------------------------------------------------------------</li>
	                <li>${info.result}<br>-----------------------------------------------------------------------------------------------------<br></li>
	            </ul>
			</c:forEach>
        </div>
        <div id="addRemark">
        	<p>
                <label>备注：</label>
                <textarea rows="5" cols="25" id="result" name="result"></textarea>
            </p>
        </div>
        <div class="formBar" id="showSubmit">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="button" id="submitInfo">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
        
        <div class="formBar" id="showClose">
            <ul>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>