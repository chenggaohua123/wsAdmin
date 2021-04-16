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
<title>可疑订单明细</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/suspicious/querySuspiciousOrderList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/suspicious/querySuspiciousOrderList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 		<li>
		 		<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
		 	<li>
		 		<label>邮箱：</label>
				<input name="email" type="text" value="${param.email}"/>
		 	</li>
		</ul>
		<ul class="searchContent">
		 	<li>
		 		<label>IP：</label>
				<input name="IPAddress" type="text" value="${param.IPAddress}"/>
		 	</li>
		 	<li>
		 		<label>账单电话：</label>
				<input name="cardFullPhone" type="text" value="${param.cardFullPhone}"/>
		 	</li>
			<li>
		 		<label>收货地址：</label>
				<input name="grAddress" type="text" value="${param.grAddress}"/>
		 	</li>
		</ul>
		<ul class="searchContent">
		 	<li>
		 		<label>账单人：</label>
				<input name="cardFullName" type="text" value="${param.cardFullName}"/>
		 	</li>
		 	<li>
		 		<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
		 	</li>
		 	<li>
		 		<label>规则：</label>
				<input name="ruleName" type="hidden"  id="rule.name" value="${param.ruleName }" />
                <input name="ruleId" type="text" id="rule.id" value="${param.ruleId }"/>
                <a class="btnLook" href="<%=path %>/suspicious/goRuleLits" width="600" height="400" mask="true" lookupGroup="rule">查找带回</a>
		 	</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>卡号前六：</label>
				<input type="text" name="cardStart" value="${param.cardStart}"/>
			</li>
			<li>
				<label>卡号后四：</label>
				<input type="text" name="cardEnd" value="${param.cardEnd}"/>
			</li>
			<li>
				<label>执行类型：</label>
				<select name="susType" class="combox" size="30">
					<option value="" <c:if test="${empty param.susType || param.susType==''}">selected</c:if>>所有</option>
					<option value="2" <c:if test="${param.susType=='2'}">selected</c:if>>按商户统计</option>
					<option value="3" <c:if test="${param.susType=='3'}">selected</c:if>>按商户终端号统计</option>
				</select>
			</li>
		 </ul>
		<ul class="searchContent">
			<li >
			  <label>执行日期</label>
			  <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['createDateStart']}" />
				--
			</li>
			<li >
			  <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['createDateEnd']}" />
			</li>
			<li >
			 <label>核实</label>
			<select class="combox" name="status">
				<option value="0">未核实</option>
				<option value="1">已核实</option>
			</select>
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
			<li><a class="add" href="<%=path %>/suspicious/exportSuspicioustOrderInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>可疑订单明细导出</span></a></li>
		</ul>
	</div>
	<div class="pageHeader" id="pageHeaderx" style="display: none;">
	
	
	</div>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="220" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>交易时间</th>
				<th>商户交易金额</th>
				<th>拒付状态</th>
				<th>退款状态</th>
				<%--
				<th>触犯规则名称</th>
				 --%>
				<th>执行类型</th>
				<th>执行日期</th>
				<th>关联</th>
				<th>触犯规则详情</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }" >
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/suspicious/queryTransByTradeNo?tradeNo=${trans.tradeNo }"> ${trans.tradeNo }</a></td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.merBusCurrency }  ${trans.merTransAmount }</td>
					<td>
					<c:if test="${trans.isDishonor==0 }">
						<font color="black">未拒付</font>
					</c:if>
					<c:if test="${trans.isDishonor==1 }">
						<font color="green">待审核</font>
					</c:if>
					<c:if test="${trans.isDishonor==2 }">
						<font color="red">已拒付</font>
					</c:if>
					</td>
					<td>
					<c:if test="${trans.isRefund==0 }">
						<font color="black">未退款</font>
					</c:if>
					<c:if test="${trans.isRefund==1 }">
						<font color="green">未审核</font>
					</c:if>
					<c:if test="${trans.isRefund==2 }">
						<font color="red">已退款</font>
					</c:if>
					</td>
					<%--
					<td>
						${trans.ruleName }
					</td>
					 --%>
					<td>
						<c:if test="${trans.susType==1 }">全库统计</c:if>
						<c:if test="${trans.susType==2 }">按商户统计</c:if>
						<c:if test="${trans.susType==3 }">按商户终端统计</c:if>
					</td>
					<td>${funcs:formatDate(trans.createDate,'yyyy-MM-dd')} </td>
					<td>
						<a target="dialog" title="触犯同一规则交易信息关联" width="1000" height="520" mask="true" href="<%=path %>/suspicious/goRelTradeNo?ruleIds=${trans.ruleIds }&merNo=${trans.merNo }&terNo=${trans.terNo }&susType=${trans.susType}&createDate=${funcs:formatDate(trans.createDate,'yyyy-MM-dd')}&tradeNo=${trans.tradeNo }"> 关联</a>
					</td>
					<td>
						<a target="dialog" title="触犯规则详情" width="400" height="300" mask="true" href="<%=path %>/suspicious/goTriggerRulelist?susType=${trans.susType }&tradeNo=${trans.tradeNo }&createDate=${funcs:formatDate(trans.createDate,'yyyy-MM-dd')}"> 触犯规则详情</a>
					</td>
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