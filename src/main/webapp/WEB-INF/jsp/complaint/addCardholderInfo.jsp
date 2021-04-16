<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">

$(document).ready(function (){
	var month=new Date().getMonth()+1;
	month=month<10?'0'+month:month;
	var day=new Date().getDate();
	day=day<10?'0'+day:day;
	var hh=new Date().getHours();
	var mm=new Date().getMinutes();
	var ss=new Date().getSeconds();
	hh=hh<10?'0'+hh:hh;
	mm=mm<10?'0'+mm:mm;
	ss=ss<10?'0'+ss:ss;
	$( "#d11" ).val( new Date().getFullYear()+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss+"");
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加持卡人投诉信息</title>
</head>
<body>
<div class="pageContent">
    <form method="post" action="<%=path %>/complaint/addComplaintInfo2" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        	<input name="complaintType" type="hidden" id="cId">
        	<input name="type" type="hidden" value="2">
            <p>
                <label>流水号：</label>
                <input name="tradeNo" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>投诉日期：</label>
                <input type="text" id="d11" name="complaintDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date required"/>
            </p>
             <p>
                <label>投诉问题：</label>
                <input name="complaintTypeValue" type="text" size="30" id="cValue" readonly="readonly" class="required"/><a class="btnLook"
								href="<%=path %>/complaint/complaintListBack?type=2" width="1100"
								height="350" rel="rateRelMerchant" mask="true" lookupGroup=""
								lookupPk="complaintTypeValue">查找带回</a>
            </p>
            <p>
                <label>投诉级别：</label>
                <select name="complaintLevel">
                	<option value="0">一般</option>
                	<option value="1">紧急</option>
                </select>
            </p>
            <p>
               <label>投诉处理状态：</label>
				<select name="status">
					<option value="0">未处理</option>
					<option value="1">已退款</option>
					<option value="2">已处理</option>
					<option value="3">商户自行协商</option>
				</select>
            </p>
             <p>
                <label>投诉处理截止日期：</label>
                <input type="text" name="deadline" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date required"/>
            </p>
             <p>
                <label>投诉补充说明：</label><textarea rows="5" cols="35" name="remark"></textarea>
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