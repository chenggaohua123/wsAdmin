<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退款信息</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/refund/listBankRefundInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="refundFromId" onsubmit="return navTabSearch(this);" action="<%=path %>/refund/listBankRefundInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li  class="dateRange">
			  <label>申请时间</label>
			  <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['dateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['dateEnd']}"/>		
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
			<li><a class="add" href="<%=path %>/refund/goAddBankRefundInfo" target="dialog" targetType="navTab" rel="uploa2dRefundFile" width="550" height="300" mask="true"><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/refund/goUpdateBankRefundInfo?id={refundId}" target="dialog" targetType="navTab" rel="uploa2dRefundFile" width="550" height="300" mask="true"><span>再次退款</span></a></li>
			<li><a class="add" href="<%=path %>/refund/exportBankRefundInfo" target="dwzExport" targetType="navTab"rel="addC22urrency" width="550" height="300" mask="true"><span>导出</span></a></li>
			<li><a class="add" href="<%=path %>/refund/uploadBankBatchRefundFile" target="dialog" targetType="navTab"rel="addC22ur11rency11" width="1000" height="600" mask="true"><span>批量退款</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>银行交易金额</th>
				<th>退款金额</th>
				<th>退款时间</th>
				<th>交易时间</th>
				<th>退款状态</th>
				<th>退款信息</th>
				<th>申请退款人</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="refund">
				<tr  target="refundId" rel="${refund.id }">
					<td>${refund.tradeNo }</td>
					<td>${refund.bankCurrency } ${refund.bankAmount }</td>
					<td>${refund.refundCurrency } ${refund.refundAmount }</td>
					<td>${refund.refundDate }</td>
					<td>${refund.transDate }</td>
					<td>${refund.refundStatus=='0000'?'退款成功':'退款失败' }</td>
					<td>${refund.refundMsg }</td>
					<td>${refund.createBy }</td>
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
</html>