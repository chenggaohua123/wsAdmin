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
<title>退款信息</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/refund/listRefundInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="refundFromId" onsubmit="return navTabSearch(this);" action="<%=path %>/refund/listRefundInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>状态：</label>
				<select name="status" class="combox">
					<option value="" ${param.status==''?'selected':'' }>所有</option>
					<option value="0" ${param.status=='0'?'selected':'' }>待审核</option>
					<option value="2" ${param.status=='2'?'selected':'' }>退款成功</option>
					<option value="1" ${param.status=='1'?'selected':'' }>退款失败</option>
				</select>
			</li>
			<li  class="dateRange">
			  <label>申请时间</label>
			  <input type="text" name="applyDateStart"  id = "applyDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.applyDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="applyDateEnd"  id = "applyDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.applyDateEnd}"/>		
			</li>
			<li  class="dateRange">
			  <label>退款时间</label>
			  <input type="text" name="refundDateStart"  id = "refundDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.refundDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="refundDateEnd"  id = "refundDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.refundDateEnd}"/>		
			</li>
			
		</ul>
		<ul class="searchContent">
			<li>
				<label>勾兑状态：</label>
				<select name="isChecked" class="combox">
					<option value="" ${param.isChecked==''?'selected':'' }>所有</option>
					<option value="0" ${param.isChecked=='0'?'selected':'' }>未勾兑</option>
					<option value="1" ${param.isChecked=='1'?'selected':'' }>已勾兑</option>
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
			<!--li><a class="edit" href="<%=path %>/refund/goDoRefundPage?id={refundId}" target="dialog" width="550" height="450" mask="true" warn="请选择一个退款记录"><span>审核</span></a></li-->
			<li><a class="edit" href="javascript:void(0)" onclick="checkRefundInfo(2)"><span>批量审核退款成功</span></a></li>
			<!-- li><a class="edit" href="javascript:void(0)" onclick="checkRefundInfo(4)"><span>批量审核退款失败</span></a></li-->
			<li><a class="edit" href="javascript:void(0)" onclick="checkRefundInfo(1)"><span>批量审核退款驳回</span></a></li>
			<li><a class="add" href="<%=path %>/refund/goAddRefundInfo" target="dialog" title="添加退款信息" mask="true"><span>新增单笔退款</span></a></li>
			<li><a class="add" href="<%=path %>/refund/goAddRefundFile" target="dialog" title="上传退款信息" mask="true"><span>批量上传退款</span></a></li>
			<li><a class="add" href="<%=path %>/refund/uploadRefundFile" target="dwzExport" targetType="navTab" rel="uploadRefundFile" width="550" height="300" mask="true"><span>退款单导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectRefundAll" id="selectRefundAll" onclick="selectRefundAll()"></th>
				<th>流水号</th>
				<th>商户号</th>
				<th>终端号</th>
				<th>订单号</th>
				<th>交易金额</th>
				<th>退款金额</th>
				<th>退款原因</th>
				<th>申请退款人</th>
				<th>申请退款时间</th>
				<th>退款处理人</th>
				<th>退款处理时间</th>
				<th>状态</th>
				<th>勾兑</th>
				<th>通知状态</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="refund">
				<tr  target="refundId" rel="${refund.id }">
					<td>
						<c:if test="${refund.status=='0'}">
							<input type="checkbox" name="refund_ids" value="${refund.id}" id="ids">
						</c:if>
					</td>
					<td>${refund.tradeNo }</td>
					<td>${refund.merNo }</td>
					<td>${refund.terNo }</td>
					<td>${refund.orderNo }</td>
					<td>${refund.busCurrency } ${refund.busAmount }</td>
					<td>${refund.refundCurrency } ${refund.refundAmount }</td>
					<td>${refund.refundReason }</td>
					<td>${refund.applyBy }</td>
					<td>${refund.applyDate }</td>
					<td>${refund.auditor }</td>
					<td>${refund.refundDate }</td>
					<td>${funcs:getStringColumnKey('CHECK_STATUS',refund.status,'未知状态')}</td>
					<td>${refund.isChecked=='1'?'<font color=red>已勾兑</font>':'未勾兑'}</td>
					<td>
						<c:if test="${refund.refundNotifyStatus=='0' }">
							<font>未通知</font>
						</c:if>
						<c:if test="${refund.refundNotifyStatus=='1' }">
							<font color="green">通知成功</font>
						</c:if>
						<c:if test="${refund.refundNotifyStatus=='2' }">
							<font color="red"><a onclick="doRefundNotify(${refund.id})">通知失败,再次通知</a></font>
						</c:if>
					</td>
					<td>${refund.remark }</td>
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
<script type="text/javascript">
function selectRefundAll(){  
    if ($("#selectRefundAll").attr("checked")) {  
    	$("input[name='refund_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='refund_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
}  

function checkRefundInfo(status){
	var str=""; 
	 $("input[name='refund_ids']:checked").each(function(){ 
		str+="ids="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选择审核数据");
	}else{
		var url="<%=path %>"+"/refund/checkRefundInfo?"+str+"status="+status;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:'text',
			success:function(data){
				alert(data.message);
				$("#refundFromId").submit();
			}
		});
	}
}

function doRefundNotify(id){
	if(confirm("是否通知！")){
		if(!id){
			alert("请选择通知的数据");
		}else{
			var url="<%=path %>"+"/refund/doRefundNotify";
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:{id:id},
				success:function(data){
					alert(data.message);
					$("#refundFromId").submit();
				}
			});
		}
	}
}
</script>
</html>