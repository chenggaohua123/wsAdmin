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
<title>终端下发</title>
</head>
<body>
 <div class="pageContent">
  <form method="post" action="<%=path %>/merchantmgr/addTerSnRelAgent" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>terSN号：</label>
                <input name="SNNo" type="text" size="30" id="tersn"  class="required"/>
				<a class="btnLook" href="<%=path %>/merchantmgr/parentRelMasteKeyBack"  width="1100" height="600" rel="parentRelAgent"  mask="true"  lookupPk="tersn"   lookupGroup="">查找带回</a>
            </p>
             <p>
                <label>顶级代理商号：</label>
                <input name="parentAgentNo" type="text" id="agentNo" size="30"  class="required"/>
                <a class="btnLook" href="<%=path %>/merchantmgr/parentRelAgentBack" width="1100" height="350"  rel="parentRelAgent" mask="true" lookupGroup="" lookupPk="agentNo">查找带回</a>
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