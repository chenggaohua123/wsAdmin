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
<script type="text/javascript" src="<%=path%>/js/table.js"></script>
<title>修改工作记录</title>
</head>

<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/taskmgr/updateTaskInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
				<p>
					<label>工作名称</label>
					<input type="text" id="jobName" name="jobName" value="${taskInfo.jobName}" size="30" readonly="readonly" class="required" />
					<input type="hidden" name="id" value="${taskInfo.id}">
				</p>
				<p>
					<label>工作组</label>
					<input type="text" id="jobGroup" name="jobGroup" value="${taskInfo.jobGroup}" size="30"  readonly="readonly" class="required"   />
				</p>
				<p>
					<label>触发器名字</label>
					<input type="text" id="triggerName" name="triggerName" value="${taskInfo.triggerName}"  size="30" class="required"   />
				</p>
				<p>
					<label>触发器组名字</label>
					<input type="text" id="triggerGroupName"name="triggerGroupName" value="${taskInfo.triggerGroupName}" size="30" class="required"/>
				</p>
				<p>
					<label>处理类型</label>
					<input type="text" id="processClass" name="processClass" value="${taskInfo.processClass}" size="30" class="required" />
				</p>
				<p>
					<label>状态码</label>
					<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=TASKSTAUTS" relValue="columnKey" selectedValue="${merchantInfo.status }" relText="columnvalue" name="status">
			        </select>
				</p>
				<p>
					<label>备注</label>
					<input type="text" id="remark" name="remark" value="${taskInfo.remark}"  size="30" class="required" />
				</p>
				<p>
					<label>cron表达式</label>
					<input type="text" id="cronExpression" name="cronExpression" value="${taskInfo.cronExpression}" size="30" class="required" />
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