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
<title>交易变更</title>
</head>
<body>
<script type="text/javascript">
	function downloadErrorInfosForTransChange(){
		window.location.href='<%=path %>/transInfoLog/downloadbatchTransChangeErrorInfo';
	}
	$(document).ready(function (){
		$("#selectTransInfoLogAll").click(function(){
			$("input[name='tradeNos']").each(function(){
			      $(this).attr("checked", $("#selectTransInfoLogAll").attr("checked")=='checked'?true:false);  
		    });	
		});
	});
	function batchCheckTransChange(toBank){
		if(confirm("是否复核通过！")){
			var param="";
			$("input[name='tradeNos']").each(function(){
				if($(this).attr("checked")){
					param+="tradeNos="+$(this).val()+"&";
				}
			   });	
			if(param==''){
				alert("请选择要复核的内容");
			}else{
				param+="toBank="+toBank;
				var url="<%=path %>"+"/transInfoLog/batchCheckTransChangeInfo";
				$.ajax({
					url:url,
					type:'post',
					dataType:'json',
					data:param,
					success:function(data){
						alert(data.message);
						$("#transChangeInfoLog").submit();
					}
				});
			}
		}
	}
</script>
<form id="pagerForm" method="post" action="<%=path %>/transInfoLog/goDoTransInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="refundFromId" onsubmit="return navTabSearch(this);" action="<%=path %>/transInfoLog/goDoTransInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo }"/>
			</li>
			
			<li>
				<label>支付通道：</label>
				<input type="text" name="currencyName" value="${param.currencyName }"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li class="dateRange">
				<label>交易时间：</label>
				<input type="text" name="transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
			       <span class="limit">-</span>
				  <input type="text" name="transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>	
			</li>
			<li class="dateRange">
				<label>变更时间：</label>
				<input type="text" name="transDateLogStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateLogStart']}"/>
			       <span class="limit">-</span>
				  <input type="text" name="transDateLogEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateLogEnd']}"/>	
			</li>
			<li>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>退款状态：</label>
				<select name="isRefund">
					<option value="" ${param.isRefund==''?'selected':'' }>-- 全部 --</option>
					<option value="0" ${param.isRefund=='0'?'selected':'' }>未退款</option>
					<option value="1" ${param.isRefund=='1'?'selected':'' }>待退款</option>
					<option value="2" ${param.isRefund=='2'?'selected':'' }>已退款</option>
				</select>
			</li>
			<li>
				<label>拒付状态：</label>
				<select name="isDishonor" class="combox">
					<option value="" ${param.isDishonor==''?'selected':'' }>-- 全部 --</option>
					<option value="0" ${param.isDishonor=='0'?'selected':'' }>未拒付</option>
					<option value="1" ${param.isDishonor=='1'?'selected':'' }>待拒付</option>
					<option value="2" ${param.isDishonor=='2'?'selected':'' }>已拒付</option>
				</select>
			</li>
			<li>
				<label>冻结状态：</label>
				<select name="isFrozen" class="combox">
					<option value="" ${param.isFrozen==''?'selected':'' }>-- 全部 --</option>
					<option value="0" ${param.isFrozen=='0'?'selected':'' }>未冻结</option>
					<option value="1" ${param.isFrozen=='1'?'selected':'' }>待冻结</option>
					<option value="2" ${param.isFrozen=='2'?'selected':'' }>已冻结</option>
				</select>
			</li>
			<li>
				<label>解冻状态：</label>
				<select name="isThaw" class="combox">
					<option value="" ${param.isThaw==''?'selected':'' }>-- 全部 --</option>
					<option value="0" ${param.isThaw=='0'?'selected':'' }>未解冻</option>
					<option value="1" ${param.isThaw=='1'?'selected':'' }>待解冻</option>
					<option value="2" ${param.isThaw=='2'?'selected':'' }>已解冻</option>
				</select>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
		<span style="color:red;">提示：查询的订单必须是已经勾兑的订单</span>
	</div>
	</form>
</div>
			
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="edit" href="<%=path %>/transInfoLog/goDoTransInfoCheckPage?tradeNo={tradeNo}&transType=check" target="dialog" width="550" height="400" mask="true" warn="请选择一条交易记录"><span>复核</span></a></li>
				<li><a class="edit" href="<%=path %>/transInfoLog/goDoTransInfoPage?tradeNo={tradeNo}&transType=dishonor" target="dialog" width="550" height="400" mask="true" warn="请选择一条交易记录"><span>拒付</span></a></li>
				<li><a class="edit" href="<%=path %>/transInfoLog/goToFrozenInfo?tradeNo={tradeNo}&transType=frozen" target="dialog" width="550" height="400" mask="true" warn="请选择一条交易记录"><span>冻结</span></a></li>
				<li><a class="edit" href="<%=path %>/transInfoLog/goDoTransInfoPage?tradeNo={tradeNo}&transType=thaw" target="dialog" width="550" height="400" mask="true" warn="请选择一条交易记录"><span>解冻</span></a></li>
				<li><a class="edit" href="<%=path %>/transInfoLog/goDoRefundPage?tradeNo={tradeNo}&transType=refund" target="dialog" width="550" height="400" mask="true" warn="请选择一条交易记录"><span>退款</span></a></li>
				<li><a class="edit" href="<%=path %>/transInfoLog/quertTransInfoLog?tradeNo={tradeNo}" target="dialog" width="1250" height="400" mask="true" warn="请选择一条交易记录"><span>查看历史记录</span></a></li>
				<li><a class="add" href="<%=path %>/transInfoLog/goUploadbatchTransChange" target="dialog" width="1250" height="600" mask="true" ><span>批量变更</span></a></li>
				<li><a class="add" href="javascript:batchCheckTransChange(0)"><span>批量复核通过(提交银行)</span></a></li>
				<li><a class="add" href="javascript:batchCheckTransChange(1)"><span>批量复核通过(不提交银行)</span></a></li>
				<c:if test="${!(empty batchTransChangeErrorInfo) }">
					<li><a class="add" href="javascript:downloadErrorInfosForTransChange()" width="1250" height="400" mask="true" ><span>下载上传错误信息</span></a></li>
				</c:if>
			</ul>
		</div>
		<div id="w_list_print">
			<table class="list" width="100%" targetType="navTab" layoutH="176" style="text-align: center;">
				<thead>
					<tr>
						<th><input type="checkBox" id="selectTransInfoLogAll" ></th>
						<th>商户号</th>
						<th>流水号</th>
						<th>订单号</th>
						<th>交易时间</th>
						<th>交易金额</th>
						<th>支付通道</th>
						<th>退款状态</th>
						<th>退款金额</th>
						<th>拒付状态</th>
						<th>拒付金额</th>
						<th>冻结状态</th>
						<th>冻结金额</th>
						<th>解冻状态</th>
						<th>解冻金额</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data}" var="order">
						<tr target="tradeNo" rel="${order.tradeNo }" align="center">
							<td>
							<c:if test="${order.isRefund=='1' ||order.isDishonor=='1'||order.isFrozen=='1'||order.isThaw=='1' }">
							<input type="checkbox" name="tradeNos" value="${order.tradeNo}">
							</c:if>
							
							</td>
							<td>${order.merNo}</td>
							<td>${order.tradeNo}</td>
							<td>${order.orderNo}</td>
							<td>${order.transDate }</td>
							<td>${order.merBusCurrency }  ${order.merTransAmount }</td>
							<td>${order.currencyName }</td>
							<td>${funcs:getStringColumnKey('TRANS_TYPE',order.isRefund,'未知状态')}</td>
							<td>${order.refundAmount }</td>
							<td>${funcs:getStringColumnKey('TRANS_TYPE',order.isDishonor,'未知状态')}</td>
							<td>${order.dishonorAmount }</td>
							<td>${funcs:getStringColumnKey('TRANS_TYPE',order.isFrozen,'未知状态')}</td>
							<td>${order.frozenAmount }</td>
							<td>${funcs:getStringColumnKey('TRANS_TYPE',order.isThaw,'未知状态')}</td>
							<td>${order.thawAmount }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' }>100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div>
</div>
	</body>
</html>