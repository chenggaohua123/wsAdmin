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
<title>添加邮件附带信息</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/emailmgr/addEmailSubInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>tel：</label>
                <input name="tel" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>fax：</label>
                <input name="fax" type="text" size="30"  class="required"/>
            </p>
             <p>
                <label>email：</label>
                <input name="email" type="text" size="30"  class="required"/>
            </p>
             <p>
                <label>replyEmail：</label>
                <input name="replyEmail" type="text" size="30"  class="required"/>
            </p>
             <p>
                <label>helpWebsite：</label>
                <input name="helpWebsite" type="text" size="30"  class="required"/>
            </p>
             <p>
                <label>website：</label>
                <input name="website" type="text" size="30"  class="required"/>
            </p>
            <p>
                <label>有效性：</label>
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