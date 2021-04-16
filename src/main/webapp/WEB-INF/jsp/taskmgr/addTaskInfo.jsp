<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="funcs" uri="funcs"%> 
 <%
	String path = request.getContextPath();
%>     

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>工作管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/taskmgr/addTaskInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>工作名称</label>
                <input name="jobName" type="text"  size="30" class="required"/>
            </p>
            <p> 
                <label>工作组</label>
                <input name="jobGroup" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>触发器名称</label>
                <input name="triggerName" type="text"  size="30" class="required"/>
            </p>
            <p>
                <label>触发器组名称</label>
                <input name="triggerGroupName" type="text"  size="30"class="required"/>
            </p>
             <p>
                <label>状态</label>
                <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=TASKSTAUTS" relValue="columnKey"  relText="columnvalue" name="status">
			    </select>
            </p>
            <p>
                <label>处理类型</label>
                <input name="processClass" type="text"  size="30"  class="required"/>
            </p>
            <p>
                <label>cron表达式</label>
                <input name="cronExpression" type="text"  size="30" class="required"/>
            </p>
            <p>
               <label>备注</label>
               <input name="remark" type="text"  size="30" />
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