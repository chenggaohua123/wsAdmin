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
<title>添加通道绑定记录</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/saveAgentMerchantInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<p>
                <label>代理商号：</label>
                <input name="agentNo" type="text" id="agentNo" size="30" class="required number" value="${agentNo }" readonly="readonly"/>
            </p>
        	<p>
                <label>商户号：</label>
                <input name="merchantNo" type="text" id="merchantNo" size="30" class="required number" readonly="readonly"/>
                <a class="btnLook" href="<%=path %>/merchantmgr/goAgentMerchantInfo" width="1100" height="350" rel="dispachMerchantInfo" mask="true" lookupGroup="" lookupPk="merchantNo">查找带回</a>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" id="terNo" type="text" size="30" class="required number"/>
                <%-- <a class="btnLook" id="Meachanttera" href="#" width="1100" height="350" rel="terList" mask="true" lookupGroup="" lookupPk="terNo" onclick="agentter()" onclick="ajaxTodo1()">查找带回</a>
                <a class="btnLook" id="Meachanttera" href="#" onclick="ajaxTodo1()">查找带回</a> --%>
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
    <input type="hidden" id="path" value="<%=path %>"/>
	</div>
	<script type="text/javascript">
		function agentter(){
			var path = document.getElementById("path").value;
			var merNo = document.getElementById("merchantNo").value;
			alert(merNo);
			if(merNo!=null && merNo!=undefined){
				var a = document.getElementById("Meachanttera");  
				//a.href = path + "/merchantmgr/getAgentMerchantTerInfoList?merNo="+merNo;
				var href = path + "/merchantmgr/getAgentMerchantTerInfoList?merNo="+merNo;     
				//取消<a>标签原先的onclick事件,使<a>标签点击后通过href跳转(因为无法用js跳转)^-^     
				a.setAttribute("onclick",'');     
				//激发标签点击事件OVER        
				//a.click("return false");
				event.preventDefault();
				//window.location = href;
			}else{
				alert("xxxxxxxxxx");
			}
		}
	</script>
</body>
</html>