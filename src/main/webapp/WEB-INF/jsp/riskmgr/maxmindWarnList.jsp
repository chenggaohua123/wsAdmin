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
<title>maxmind警告查询</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	  $("#btnx").click(function(){
			$("#pageHeaderx").slideToggle();
			/* var oldH=$("#tableList").attr("layoutH");
			alert(oldH);
			alert($("#btnx").is(":hidden"));
		  if($("#btnx").is(":hidden")){
			  $(".list").attr("layoutH",oldH+150);
		  }else{
			  $(".list").attr("layoutH",oldH-150);
		  }*/
	   });
	$("#btnx1").click(function(){
		  $("#pageHeader").slideToggle();
	  });
});

</script>
<form id="pagerForm" method="post" action="<%=path %>/riskmgr/getMaxmindWarnList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskmgr/getMaxmindWarnList" method="post">
	<div class="searchBar" >
			<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
		   <li>
			  <label>日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${form['transDateStart']}"/>
		      </li>
		   <li> <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss"  class="date" value="${form['transDateEnd']}"/>		
			</li>
		
			
		</ul>
		 <ul class="searchContent">
			<li>
				<label>maxmindID：</label>
				<input type="text" name="maxmindID" value="${param.maxmindID}"/>
			</li>
		 	
		 </ul>
		 
		 
		 
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="add" href="<%=path %>/riskmgr/exportMaxmindWarnList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="150" style="text-align: center;">
		<thead>
			<tr>
				<th>maxmindID</th>
				<th>流水号</th>
				<th>原因</th>
				<th>原因码</th>
				<th>支付状态</th>
				<th>返回结果 </th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.txnID }" >
					<td>${trans.maxmindID }</td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${trans.txnID }"> ${trans.txnID }</a></td>
					<td>${trans.reason}</td>
					<td>${trans.reason_code}</td>
					<td>
						<c:if test="${trans.respCode=='00' }"><font color="green">支付成功</font></c:if>
						<c:if test="${trans.respCode!='00' }"><font color="green">支付失败</font></c:if>
					</td>
					<td>${trans.respMsg }</td>
					<td>${trans.createDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" style="height:30px">
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>