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
<title>拒付信息列表</title>
</head>
<script type="text/javascript">
function selectAllCardholder(){  
    if ($("#selectAllCardholder").attr("checked")) {  
    	$("input[name='lsitCardholderInfo_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='lsitCardholderInfo_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
} 


function toUpdateCardholderInfo(status){
	var str=""; 
	 $("input[name='lsitCardholderInfo_ids']:checked").each(function(){ 
		str+="ids="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选处理核数据");
	}else{
			var url="<%=path %>"+"/complaint/updateCardholderInfo?"+str+"status="+status;//根据状态不通修改不通状态
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:'text',
			success:function(data){
				alert(data.message);
				$("#fromCardholderId").submit();
			}
		});
	}
}
	function downCompFile(){
		window.location.href="<%=path%>/complaint/downloadCompFile";
	}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/complaint/listCardholderInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="fromCardholderId" onsubmit="return navTabSearch(this);" action="<%=path %>/complaint/listCardholderInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
			</li>
			<li>
				<label>网站：</label>
				<input type="text" name="payWebSite" value="${param.payWebSite}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li  class="dateRange">
			  <label>交易日期:</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
			</li>
			<li  class="dateRange">
			  <label>投诉日期:</label>
			  <input type="text" name="complaintDateStart"  id = "complaintDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.complaintDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="complaintDateEnd"  id = "complaintDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.complaintDateEnd}"/>		
			</li>
			<li>
				<label>投诉处理状态：</label>
				<select name="status" class="combox">
					<option value="" <c:if test="${param.status==''}">selected</c:if>>--所有--</option>
					<option value="0" <c:if test="${param.status=='0'}">selected</c:if>>未处理</option>
					<option value="1" <c:if test="${param.status=='1'}">selected</c:if>>已退款</option>
					<option value="2" <c:if test="${param.status=='2'}">selected</c:if>>已处理</option>
					<option value="3" <c:if test="${param.status=='3'}">selected</c:if>>商户自行协商</option>
				</select>
			</li>
			<li>
				<label>投诉问题：</label>
				<input name="complaintTypeValue" value="${param.complaintTypeValue}">
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>投诉级别：</label>
				<select name="complaintLevel" class="combox">
					<option value="" <c:if test="${param.complaintLevel==''}">selected</c:if>>--所有--</option>
					<option value="0" <c:if test="${param.complaintLevel=='0'}">selected</c:if>>一般</option>
					<option value="1" <c:if test="${param.complaintLevel=='1'}">selected</c:if>>紧急</option>
				</select>
			</li>
			<li>
				<label>支付通道：</label>
				<input name="currencyName" value="${param.currencyName}">
			</li>
			<li>
				<label>创建人：</label>
				<input name="createdBy" value="${param.createdBy}">
			</li>
			<li>
				<label>处理人：</label>
				<input name="lastUpdateBy" value="${param.lastUpdateBy}">
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
			<li><a class="add" href="<%=path %>/complaint/goAddCardholderInfo" target="dialog" width="550" height="380" mask="true" rel="add" ><span>添加投诉单</span></a></li>
			<li><a class="edit" href="javascript:downCompFile()" ><span>下载批量添加投诉单模板</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/goBatchAddCardholderInfo" target="dialog" width="550" height="380" mask="true" rel="add" ><span>批量添加投诉单</span></a></li>
			<li><a class="edit" href="javascript:toUpdateCardholderInfo(2);"><span>批量操作已处理</span></a></li>
			<li><a class="edit" href="javascript:toUpdateCardholderInfo(3);"><span>批量操作商户自行协商</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/exportCardholderTransInfo" target="dwzExport" width="550" height="380" mask="true" rel="add"><span>导出列表数据</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="165" style="text-align:center;" >
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAllCardholder" id="selectAllCardholder" onclick="selectAllCardholder()"></th>
				<th>商户号</th>
				<th>流水号</th>
				<th>订单号</th>
				<th>网站</th>
				<th>交易金额</th>
				<th>投诉日期</th>
				<th>投诉问题</th>
				<th>投诉级别</th>
				<th>投诉处理状态</th>
				<th>投诉补充说明</th>
				<th>投诉处理截止日期</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>处理人</th>
				<th>处理时间</th>
				<th>商户处理结果</th>
				<th>处理过程记录</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td>
						<input type="checkbox" name="lsitCardholderInfo_ids" value="${info.id}" id="ids">
					</td>
					<td>${info.merNo }</td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${info.tradeNo }"> ${info.tradeNo }</a></td>
					<td>${info.orderNo }</td>
					<td>${info.payWebSite }</td>
					<td>${info.merBusCurrency } ${info.merTransAmount }</td>
					<td>${info.complaintDate }</td>
					<td>${info.complaintTypeValue }</td>
					<td>${funcs:getStringColumnKey('complaint_Level',info.complaintLevel,'未知状态')}</td>
					<td>${funcs:getStringColumnKey('COMPLAINT_STATUS_2',info.status,'未知状态')}</td>
					<td>${info.remark }</td>
					<td>${info.deadline }</td>
					<td>${info.createdBy}</td>
					<td>${info.createdDate }</td>
					<td>${info.lastUpdateBy }</td>
					<td>${info.lastUpdateDate }</td>
					<td><a target="dialog" title="商户处理结果" width="400" height="520" mask="true" href="<%=path %>/complaint/goShowHandleResult?complaintId=${info.id }&systemId=2">商户处理结果</a></td>
					<td><a target="dialog" rel="showHandleResultPage" title="处理过程记录" width="400" height="520" mask="true" href="<%=path %>/complaint/goShowHandleResult?complaintId=${info.id }&systemId=1">处理过程记录</a></td>
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