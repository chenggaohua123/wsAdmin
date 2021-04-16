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
<title>添加邮箱信息</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/emailmgr/addEmailInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>流水号：</label>
                <input name="tradeNo" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>调查日期：</label>
                <input type="text" name="complaintDate"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date required" value="${param.transDateStart}"/>
            </p>
             <p>
                <label>处理截止日期：</label>
                <input type="text" name="deadline"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date required" value="${param.transDateStart}"/>
            </p>
             <p>
                <label>调查原因：</label>
                <input name="complaintType" type="text" size="30"  class="required"/><a class="btnLook"
								href="<%=path %>/complaint/complaintListBack" width="1100"
								height="350" rel="rateRelMerchant" mask="true" lookupGroup=""
								lookupPk="merNo">查找带回</a>
            </p>
             <p>
                <label>补充说明：</label>
                <textarea rows="5" cols="35" name="remark">
                </textarea>
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