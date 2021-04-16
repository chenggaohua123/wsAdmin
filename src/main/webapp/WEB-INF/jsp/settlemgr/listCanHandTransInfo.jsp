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
<title>手工结算</title>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		$("#listCanHandTransInfo #selectCanHandAll").click(function(){
			 $('#listCanHandTransInfo input:checkbox').each(function () {
				 var flag=false;
				 if($("#listCanHandTransInfo #selectCanHandAll").attr("checked")){
					 flag=true;
				 }
				 $(this).attr("checked",flag);
			});
		});
	$("#addHandTransInfo").click(function (){
		if(!confirm("提交后次日8时结算，是否确认！")){
			return ;
		}
		var data=$("#canHandTransAdd").serialize();
		 $.ajax({
	            type:"POST",
	            data:data,
	            url:"<%=path %>/settlemgr/addHandTransInfo",
	            dataType:"json",
	            success: function(json){
	                if(json.statusCode==200){
	                	alert(json.message);
	                	$("#reSearch #search").click();
		            }else{
		            	alert(json.message);
		            }
	            }
	        });
	});
	});
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/queryCanHandTransInfo">
	<input type="hidden" name="pageNum" value="${list.nowPage }" />
	<input type="hidden" name="numPerPage" value="${list.pageSize}" />
</form>
<div class="pageHeader" id="reSearch">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/queryCanHandTransInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
			</li>
			</ul>
			<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo }"/>
			</li>
			</ul>
		<font color='red'>
			注：1.当选择提交之后，次日早上八点结算。<br>	
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.出现在此列表的订单为180之前未结算的订单。
		</font>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="search">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add"  id="addHandTransInfo"  width="1000" height="600" mask="true"  rel="settleType1" ><span>手工结算</span></a></li>
			<li>
			</li>
		</ul>
	</div>
	<div id="w_list_print">
	<form id="canHandTransAdd">
	<table class="list" width="100%" targetType="navTab" layoutH=160 style="text-align: center;" id="listCanHandTransInfo">	
		<thead>
			<tr>
				<th><input name="selectAll" id="selectCanHandAll" type="checkbox" > </th>
				<th>流水号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易金额</th>
				<th>结算金额</th>
				<th>交易时间</th>
				<th>退款状态</th>
				<th>拒付状态</th>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${list.data }" var="st">
				<tr  target="stId" rel="${st.tradeNo }"   >
					<td><input name="tradeNos" type="checkbox" value="${st.tradeNo }"> </td>
					<td>${st.tradeNo }</td>
					<td>${st.orderNo }</td>
					<td>${st.merNo }</td>
					<td>${st.terNo }</td>
					<td>${st.merBusCurrency } ${st.merTransAmount }</td>
					<td>${st.merSettleCurrency } ${st.merSettleAmount }</td>
					<td>${st.transDate }</td>
					<td>${st.transRefund==0?'未退款':'<font color=red>已退款</font>' }</td>
					<td>${st.transDishonor==0?'未拒付':'<font color=red>已拒付</font>'}</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	</div>
	<div class="panelBar" >
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${list.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${list.total }" numPerPage="${list.numPerPage }" pageNumShown="10" currentPage="${list.nowPage }"></div>
	</div>
</div>
</body>
</html>