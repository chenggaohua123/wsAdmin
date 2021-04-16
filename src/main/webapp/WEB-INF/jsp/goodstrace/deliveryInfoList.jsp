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
<title>妥投信息</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/delivery/queryDeliveryInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/delivery/queryDeliveryInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li><label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo }"/>
			</li>
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			
		</ul>
		<ul class="searchContent">
			
			<li  class="dateRange">
			  <label>上传时间</label>
			  <input type="text" name="uploadStartDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.uploadStartDate}"/>
		       <span class="limit">-</span>
			  <input type="text" name="uploadsEndDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.uploadsEndDate}"/>		
			</li>
			<li  class="dateRange">
			  <label>交易时间</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart']}"/>
			  <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateEnd']}"/>		
			</li>
			<li>
				<label>货运查询状态：</label>
				
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=LOGISTICS_STATUS" relValue="columnKey" selectedValue="${param.operationStatus}" relText="columnvalue" name="operationStatus">
					<option value="">所有</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>网站：</label>
				<input type="text" name="merwebsite" value="${param.merwebsite }"/>
			<li>
				<label>发货状态：</label>
				<select name="status" class="combox">
					<option value="" ${param.status==''?'selected':'' }>所有</option>
	
					<option value="0" ${param.status=='0'?'selected':'' }>未发货</option>
					<option value="1" ${param.status=='1'?'selected':'' }>已发货</option>
					
				</select>
			</li>
			<li>
				<label>货运单号：</label>
				<input type="text" name="trackNo" value="${param.trackNo }"/>
			</li>
			
		</ul>
		<ul class="searchContent">
			<li>
				<label>退款状态：</label>
				<select name="refundStatus" class="combox">
				<option value="" ${param.refundStatus==''?'selected':''}>所有</option>
				<option value="0" ${param.refundStatus=='0'?'selected':''}>未退款</option>
				<option value="1" ${param.refundStatus=='1'?'selected':''}>已退款</option>
				</select>
			<li>
				<label>拒付状态：</label>
					<select name="disStatus" class="combox">
				<option value="" ${param.disStatus==''?'selected':''}>所有</option>
				<option value="0" ${param.disStatus=='0'?'selected':''}>未拒付</option>
				<option value="1" ${param.disStatus=='1'?'selected':''}>已拒付</option>
				</select>
			</li>
			<li>
				<label>冻结状态：</label>
				<select name="frozenStatus" class="combox">
				<option value="" ${param.frozenStatus==''?'selected':''}>所有</option>
				<option value="0" ${param.frozenStatus=='0'?'selected':''}>未冻结</option>
				<option value="1" ${param.frozenStatus=='1'?'selected':''}>已冻结</option>
				</select>
			</li>
			
		</ul>
		<ul class="searchContent">
			<li>
				<label>划款状态：</label>
				<select name="isSettle" class="combox">
				<option value="" ${param.isSettle==''?'selected':''}>所有</option>
				<option value="0" ${param.isSettle=='0'?'selected':''}>未划款</option>
				<option value="1" ${param.isSettle=='1'?'selected':''}>已划款</option>
				</select>
			<li>
				<label>结算条件：</label>
					<select name="settleType" class="combox">
				<option value="" ${param.settleType==''?'selected':''}>所有</option>
				<option value="0" ${param.settleType=='0'?'selected':''}>免审单结算</option>
				<option value="1" ${param.settleType=='1'?'selected':''}>上传单号结算</option>
				<option value="2" ${param.settleType=='2'?'selected':''}>妥投结算</option>
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
			<li><a class="add" href="<%=path %>/delivery/exportDelivery" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出交易运单信息</span></a></li>
			<li><a  class="edit" style="color:green" href="<%=path %>/delivery/updateDeliveryInfo?id={id}&operationStatus=1" target="ajaxTodo" title="确定要修改货运状态吗?" mask="true"><span>已妥投</span></a></li>
			<li><a  class="edit" style="color:red" href="<%=path %>/delivery/updateDeliveryInfo?id={id}&operationStatus=0" target="ajaxTodo" title="确定要修改货运状态吗?" mask="true"><span>未妥投</span></a></li>
			<!-- 
			<li><a  class="edit" style="color:purple" href="<%=path %>/delivery/updateDeliveryInfo?id={id}&status=1" target="ajaxTodo" title="确定要修改发货状态吗?" mask="true"><span>已发货</span></a></li>
			<li><a  class="edit" style="color:#8B0A50" href="<%=path %>/delivery/updateDeliveryInfo?id={id}&status=0" target="ajaxTodo" title="确定要修改发货状态吗?" mask="true"><span>未发货</span></a></li>
			 -->
			<li><a class="edit" href="<%=path %>/delivery/goUpdateStatusAndRemark?id={id}" target="dialog" width="500" height="250" mask="true"><span>修改运单信息</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="210" style="text-align: center;">
		<thead>
					  
			<tr>
				<th>商户号</th>
				<th>交易流水号</th>
				<th>订单号</th>
				<th>交易日期</th>
				<th>网站</th>
				<th>发货状态</th>
				<th>货运公司</th>
				<th>货运单号</th>
				<th>上传时间</th>
				<th>货运查询状态</th>
				<th>操作人</th>
				<th>操作时间</th>
				<th>备注说明</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data}" var="deliveryInfo">
				<tr target="id" rel="${deliveryInfo.id }">
					<td>${deliveryInfo.merNo}</td>
					<td>${deliveryInfo.tradeNo}</td>
					<td>${deliveryInfo.orderNo}</td>
					<td>${funcs:formatDate(deliveryInfo.tradetime,'yyyy-MM-dd HH:mm:ss')}	</td>
					
					<td>${deliveryInfo.merwebsite}</td>
					<td>
					<c:if test="${deliveryInfo.status==1}"><a style="color:purple">已发货</a></c:if>
					<c:if test="${deliveryInfo.status==0}"><a style="color:#8B0A50">未发货</a></c:if>
					</td>
					<td>${deliveryInfo.iogistics}</td>
					<td>${deliveryInfo.trackNo}</td>
					<td>${deliveryInfo.uploadTime}</td>
					
					
					<td>
						${funcs:getStringColumnKey('LOGISTICS_STATUS',deliveryInfo.operationStatus,deliveryInfo.operationStatus)}
					</td>
					<td>${deliveryInfo.modifyPerson}</td>
					<td>${deliveryInfo.lastDateTime}</td>
					<td>${deliveryInfo.remark}</td>
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