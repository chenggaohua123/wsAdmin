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
<title>交易伪冒单</title>
</head>
<script type="text/javascript">
	function selectAllComplaint(){  
		if ($("#selectAllComplaint").attr("checked")) {  
	    	$("input[name='lsitComplaintInfo_ids']").each(function(){
		        $(this).attr("checked", true);  
	    	})
	    } else {  
	    	$("input[name='lsitComplaintInfo_ids']").each(function(){
		        $(this).attr("checked", false);  
	    	})
	    }  
	}  

	function toUpdateComplaintInfo(status){
		var str=""; 
		 $("input[name='lsitComplaintInfo_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		if(!str){
			alert("请选择数据");
		}else{
				var url="<%=path %>"+"/complaint/updateComplaintInfo?"+str+"status="+status;//根据状态不通修改不通状态
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:'text',
				success:function(data){
					alert(data.message);
					$("#fromId").submit();
				}
			});
		}
	}
	function downFakeFile(){
		window.location.href="<%=path%>/complaint/downloadFakeFile";
	}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/complaint/getFakeTransList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" id="fromId" action="<%=path %>/complaint/getFakeTransList" method="post">
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
			  <label>伪冒单通知时间:</label>
			  <input type="text" name="complaintDateStart"  id = "complaintDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.complaintDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="complaintDateEnd"  id = "complaintDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.complaintDateEnd}"/>		
			</li>
			<li>
				<label>创建人：</label>
				<input name="createdBy" value="${param.createdBy}">
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
			<li><a class="add" href="<%=path %>/complaint/goAddFakeInfo" target="dialog" width="550" height="380" mask="true" rel="add" ><span>添加伪冒单</span></a></li>
			<li><a class="edit" href="javascript:downFakeFile()" ><span>下载批量添加伪冒单模板</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/goBatchAddFakeInfo" target="dialog" width="550" height="380" mask="true" rel="add" ><span>批量添加伪冒单</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/exportFakeTransInfo" target="dwzExport" width="550" height="380" mask="true" rel="add"><span>导出列表数据</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="145" style="text-align:center;" >
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAll" id="selectAllComplaint" onclick="selectAllComplaint()"></th>
				<th>商户号</th>
				<th>流水号</th>
				<th>订单号</th>
				<th>网站</th>
				<th>交易金额</th>
				<th>伪冒单通知时间</th>
				<th>欺诈分数</th>
				<th>卡号</th>
				<th>IP</th>
				<th>邮箱</th>
				<th>拒付状态</th>
				<th>退款状态</th>
				<th>冻结状态</th>
				<th>创建时间</th>
				<th>处理人</th>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${page.data }" var="info">
				<tr target="sid_user" rel="${info.id }">
					<td>
						<input type="checkbox" name="lsitComplaintInfo_ids" value="${info.id}" id="ids">
					</td>
					<td>${info.merNo }</td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${info.tradeNo }"> ${info.tradeNo }</a></td>
					<td>${info.orderNo }</td>
					<td>${info.payWebSite }</td>
					<td>${info.merBusCurrency } ${info.merTransAmount }</td>
					<td>${info.complaintDate }</td>
					<td>${info.riskScore }</td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByType?cardNo=${info.sixAndFourCardNo}"> ${info.sixAndFourCardNo}</a></td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByType?IPAddress=${info.ipAddress}"> ${info.ipAddress}</a></td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByType?email=${info.email}"> ${info.email}</a></td>
					<td><c:if test="${info.transDishonor=='0'}">
						<font color="red">未拒付 </font>
					</c:if><c:if test="${info.transDishonor=='1'}">
						<font color="green">已拒付</font>
					</c:if></td>
					<td><c:if test="${info.transRefund=='0'}">
						<font color="red">未退款</font>
					</c:if><c:if test="${info.transRefund=='1'}">
						<font color="green">已退款</font>
					</c:if></td>
					
					<td><c:if test="${info.transFrozen=='0'}">
						<font color="red">未冻结</font>
					</c:if><c:if test="${info.transFrozen=='1'}">
						<font color="green">已冻结</font>
					</c:if></td>
					<td>${info.createdBy}</td>
					<td>${info.createdDate }</td>
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