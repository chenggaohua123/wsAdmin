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
<title>订单跟踪</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transmgr/getTransRecordList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transmgr/getTransRecordList" method="post">
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
		 		<label>商户订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
		 	</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li>
				<label>来源网址：</label>
				<input type="text" name="merURL" value="${param.merURL }"/>
			</li>
			<li  class="dateRange">
			  <label>进入系统时间</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateEnd']}"/>		
			</li>
		</ul> 
		<ul class="searchContent">
		<li>
				<label>描述：</label>
				<input type="text" name="description" value="${param.description }"/>
			</li>
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/transmgr/uploadTransRecordFile" target="dwzExport" targetType="navTab" rel="uploadRefundFile" width="550" height="300" mask="true"><span>订单跟踪导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>商户订单号</th>
				<th>进入系统时间</th>
				<th>来源IP</th>
				<th>描述</th>
				<th>异常码</th>
				<th>异常原因</th>
				<th>协议</th>
				<th>金额 </th>
				<th>网址信息</th>
				<!-- <th>来源网址</th>
				<th>提交地址</th>
				<th>返回网址</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }">
					<td>${trans.tradeNo }</td>
					<td>${trans.merNo }</td>
					<td>${trans.terNo }</td>
					<td>${trans.orderNo }</td>
					<td>${trans.enterTime }</td>
					<td><a href='http://www.ip138.com/ips138.asp?action=2&ip=${trans.clientIP }' target='_blank'>${trans.clientIP }</a></td>
					<td>${trans.description }</td>
					<td>${trans.respCode }</td>
					<td>${trans.respMsg }</td>
					<td>${trans.transPortProtocol }</td>
					<td>${trans.currencyCode }&nbsp;${trans.amount }</td>
					<td align="left">
						来源网址：${trans.merURL }<br>
						提交地址：${trans.submitURL }<br>
						返回网址：${trans.returnURL }<br>
						<c:if test="${trans.merNotifyStatus=='0' }">
							<font>
								<c:if test="${trans.merNotifyURL==null}">
									异步通知
								</c:if>
								<c:if test="${trans.merNotifyURL!=null}">
									<a  href="<%=path %>/rocketmq/asyncProducer?merNo=${trans.merNo}&terNo=${trans.terNo}&tradeNo=${trans.tradeNo }&orderNo=${trans.orderNo }&merNotifyURL=${trans.merNotifyURL}"  target="ajaxTodo"   title="确定要通知吗?">异步通知</a>
								</c:if>
							</font>：${trans.merNotifyURL }[${trans.merNotifyCount }]
						</c:if>
						<c:if test="${trans.merNotifyStatus=='1' }">
							<c:if test="${trans.merNotifyURL==null}">
									异步通知
								</c:if>
								<c:if test="${trans.merNotifyURL!=null}">
									<a  href="<%=path %>/rocketmq/asyncProducer?merNo=${trans.merNo}&terNo=${trans.terNo}&tradeNo=${trans.tradeNo }&orderNo=${trans.orderNo }&merNotifyURL=${trans.merNotifyURL}"  target="ajaxTodo"   title="确定要通知吗?"><font color="green">异步通知</font></a>
								</c:if>
							：${trans.merNotifyURL }[${trans.merNotifyCount }]
						</c:if>
						<c:if test="${trans.merNotifyStatus=='2' }">
								<c:if test="${trans.merNotifyURL==null}">
									异步通知
								</c:if>
								<c:if test="${trans.merNotifyURL!=null}">
									<a  href="<%=path %>/rocketmq/asyncProducer?merNo=${trans.merNo}&terNo=${trans.terNo}&tradeNo=${trans.tradeNo }&orderNo=${trans.orderNo }&merNotifyURL=${trans.merNotifyURL}"  target="ajaxTodo"   title="确定要通知吗?"><font color="red">异步通知</font></a>
								</c:if>
								：${trans.merNotifyURL }[${trans.merNotifyCount }]
						</c:if>
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