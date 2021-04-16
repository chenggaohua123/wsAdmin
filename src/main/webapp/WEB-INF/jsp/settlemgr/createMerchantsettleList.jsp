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
<title>生成清算记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/viewMerchantSettleList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<script type="text/javascript">
	function viewMerchantSettleList(o){
		if($("#settleDate").val() == ""){
			alertMsg.error('请先选择清算日期，然后在提交！')
			return false;
		}
		return navTabSearch(o);
			
	}
	
	function changeSubmitAction(){
		$("#settleForm").attr("action","<%=path %>/settlemgr/createMerchantSettleList");
		validateCallback($("#settleForm"),'','确定要生效？');
	}
</script>
<div class="pageHeader">
	<form rel="pagerForm" id="settleForm" onsubmit="return  viewMerchantSettleList(this)"  action="<%=path %>/settlemgr/viewMerchantSettleList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			 <li>
				<label>清算截止日期：</label>
				<input type="text" name="transDate"  id = "settleDate" readonly="readonly" dateFmt="yyyy-MM-dd HH" class="date required" value="${param.transDate}"/>
			</li>
		</ul>
			<div class="subBar">
			<ul>
				<c:if test="${param.transDate != null }">
					<li><div class="buttonActive"><div class="buttonContent"><button onclick="changeSubmitAction()"  type="button">生成清算记录</button></div></div></li>
				</c:if>
				<c:if test="${param.transDate == null }">
					<li><div class="buttonActive"><div class="buttonContent"><button  type="submit">预览清算记录</button></div></div></li>
				</c:if>
			</ul>
		</div>
	</div>
	
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/settlemgr/exportViewMerchantSettleList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出清算记录</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>代理商编号</th>
				<th>父级代理商编号</th>
				<th>交易笔数</th>
				<th>交易金额</th>
				<th>交易手续费</th>
				<th>代理商手续费</th>
				<th>父级商手续费</th>
				<th>结算金额</th>
				<th>结算日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="settle">
				<tr target="settleId" rel="${settle.id }">
					<td>${settle.merNo }</td>
					<td>${settle.terNo }</td>
					<td>${settle.agentNo }</td>
					<td>${settle.parentAgentNo }</td>
					<td>${settle.transCount }</td>
					<td>${settle.transAmount }</td>
					<td>${settle.forAmount }</td>
					<td>${settle.agentForAmount }</td>
					<td>${settle.parentAgentForAmount }</td>
					<td>${settle.settleAmount }</td>
					<td>${param.transDate }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
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
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
</body>
</html>