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
<title>银行拒付订单</title>

</head>
<script type="text/javascript">

	function getBank(){
		var currencyId=$("#currency").val();
		if(currencyId=="973"){
			$("#bank option[value='52']").attr("selected", false);
			$("#bank option[value='97']").attr("selected", true);
			$("#combox_bank a").attr("value","97");
			$("#combox_bank a").html("通联47通道-USD");
		}else {
			$("#bank option[value='52']").attr("selected", true);
			$("#bank option[value='97']").attr("selected", false);
			$("#combox_bank a").attr("value","52");
			$("#combox_bank a").html("通联2通道");
		}
		
	}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/bankOrder/getBankDishonourList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" >
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/bankOrder/getBankDishonourList" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li style="width: 250px;">
		 		<label>银行名称：</label>
				<select class="combox" name="bankId" id="bank">
					<option value="52" <c:if test="${form.bankId=='52'}">selected</c:if> >通联2通道</option>
					<option value="97" <c:if test="${form.bankId=='97'}">selected</c:if> >通联47通道-USD</option>
				</select>
		 	</li>
		 	<li style="width: 250px;">
		 		<label>通道名称：</label>
		 		<select class="combox" loadurl="<%=path %>/bankOrder/queryCurrencyIfoList?bankId=52/97/57&currencyId=2A/2B/2C/2BM/47A" relValue="id" selectedValue="${param.currencyId }" relText="currencyName" name="currencyId" id="currency" onchange="getBank()">
					<option value="">请选择</option>
				</select>
				
				
		 	</li>
		 	<li style="width: 250px;">
		 		<label>订单年份：</label>
				<select class="combox" name="year" >
					<option value="2008" <c:if test="${form.year=='2008'}">selected</c:if> >2008年</option>
					<option value="2009" <c:if test="${form.year=='2009'}">selected</c:if> >2009年</option>
					<option value="2010" <c:if test="${form.year=='2010'}">selected</c:if> >2010年</option>
					<option value="2011" <c:if test="${form.year=='2011'}">selected</c:if> >2011年</option>
					<option value="2012" <c:if test="${form.year=='2012'}">selected</c:if> >2012年</option>
					<option value="2013" <c:if test="${form.year=='2013'}">selected</c:if> >2013年</option>
					<option value="2014" <c:if test="${form.year=='2014'}">selected</c:if> >2014年</option>
					<option value="2015" <c:if test="${form.year=='2015'}">selected</c:if> >2015年</option>
					<option value="2016" <c:if test="${form.year=='2016'}">selected</c:if> >2016年</option>
					<option value="2017" <c:if test="${form.year=='2017'}">selected</c:if> >2017年</option>
					<option value="2018" <c:if test="${form.year=='2018'}">selected</c:if> >2018年</option>
					<option value="2019" <c:if test="${form.year=='2019'}">selected</c:if> >2019年</option>
					<option value="2020" <c:if test="${form.year=='2020'}">selected</c:if> >2020年</option>
					<option value="2021" <c:if test="${form.year=='2021'}">selected</c:if> >2021年</option>
					<option value="2022" <c:if test="${form.year=='2022'}">selected</c:if> >2022年</option>
				</select>
		 	</li>
		 	<li style="width: 250px;">
		 		<label>订单月份：</label>
		 		<select class="combox" name="month" >
					<option value="1" <c:if test="${form.month=='1'}">selected</c:if> >1月</option>
					<option value="2" <c:if test="${form.month=='2'}">selected</c:if> >2月</option>
					<option value="3" <c:if test="${form.month=='3'}">selected</c:if> >3月</option>
					<option value="4" <c:if test="${form.month=='4'}">selected</c:if> >4月</option>
					<option value="5" <c:if test="${form.month=='5'}">selected</c:if> >5月</option>
					<option value="6" <c:if test="${form.month=='6'}">selected</c:if> >6月</option>
					<option value="7" <c:if test="${form.month=='7'}">selected</c:if> >7月</option>
					<option value="8" <c:if test="${form.month=='8'}">selected</c:if> >8月</option>
					<option value="9" <c:if test="${form.month=='9'}">selected</c:if> >9月</option>
					<option value="10" <c:if test="${form.month=='10'}">selected</c:if> >10月</option>
					<option value="11" <c:if test="${form.month=='11'}">selected</c:if> >11月</option>
					<option value="12" <c:if test="${form.month=='12'}">selected</c:if> >12月</option>
				</select>
		 	</li>
		</ul>
		
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/bankOrder/uploadDishonourOrderFile" target="dwzExport" targetType="navTab" rel="uploadRefundFile" width="550" height="300" mask="true"><span>拒付订单导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
		<thead>
			<tr>
				<th>交易号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>拒付金额(CNY)</th>
				<th>拒付日期</th>
				<th>拒付状态</th>
				<th>拒付原因</th>
				<th>拒付添加日期</th>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${page.data }" var="bankOrder">
					<tr target="tradeNo" rel="${bankOrder.transactionId }">
						<td>${bankOrder.transactionId }</td>
						<td>${bankOrder.orderId }</td>
						<td>${bankOrder.merchantId }</td>
						<td>${bankOrder.amount }</td>
						<td>${bankOrder.dateRefused }</td>
						<td>
						<c:choose>
							<c:when test="${bankOrder.status=='Freeze' }">冻结</c:when>
							<c:when test="${bankOrder.status=='Refused' }">已拒付</c:when>
							<c:when test="${bankOrder.status=='Thawed' }">已解冻</c:when>
							<c:when test="${bankOrder.status=='Cancel' }">取消</c:when>
						</c:choose>
						</td>
						<td>${bankOrder.reason }</td>
						<td>${bankOrder.dateCreated }</td>
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