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
	function exportDetail(id,merNo,terNo,cashType){
		window.location.href="<%=path%>/settlemgr/exportMerchantInAndOutDetail?id="+id+"&merNo="+merNo+"&terNo="+terNo+"&cashType="+cashType;
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>商户出入帐</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/listMerchantAccountInAndOut">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/listMerchantAccountInAndOut" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
             <li>
				<label>提现审核状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=ACCESSSTATUS" selectedValue="${param.status }"  relValue="columnKey" relText="columnvalue" name="status">
			      <option value="">所有</option>
			    </select>
			</li>
			 <li>
				<label>账户类型：</label>
				<select name="accountType" class="combox">
					<option value="" ${param.accountType==''?'selected':'' }>所有</option>
					<option value="0" ${param.accountType=='0'?'selected':'' }>交易账户</option>
					<option value="1" ${param.accountType=='1'?'selected':'' }>保证金账户</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>出账开始时间：</label>
				 <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateStart}"/>
			</li>
			<li>
				<label>出账结束时间：</label>
			   <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateEnd}"/>		
			</li>
			<li>
				<label>出入账类型：</label>
			   <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CASHTYPE" relValue="columnKey" selectedValue="${param.cashType }" relText="columnvalue" name="cashType">
					<option value="">所有</option>
				</select>
			</li>
		</ul>
		<ul  class="searchContent">
			<li >
            	<label>扣款类型</label>
            	<select name=deductionType  class="combox">
            	<option value="" ${param.deductionType==''?'selected':'' }>所有</option>
            		<c:forEach items="${dtList }" var="dt">
            			<option value="${dt.deductionType }"  ${param.deductionType==dt.deductionType ?'selected':''} >${dt.deductionType}</option>
            		</c:forEach>
            	</select>
            </li>
            <li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			 <li>
				<label>商户渠道：</label>
				 <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANT_CHANNEL" relValue="columnKey" selectedValue="${param.merchantChannel}"  relText="columnvalue"  name="merchantChannel">
             		<option value="" ${param.merchantChannel==''?'selected':'' }>所有</option>
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
	<ul  class="toolBar">
	<li><a class="add" href="<%=path %>/settlemgr/exportListMerchantAccountInAndOut" target="dwzExport" targetType="navTab"rel="addCurrenc1111y" width="550" height="300" mask="true"><span>导出</span></a></li>
	</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="165" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>账户类型</th>
				<th>出入帐类型</th>
				<th>扣款类型</th>
				<th>提现金额</th>
				<th>商户渠道</th>
				<th>状态</th>
				<th>出入帐时间</th>
				<th>出款说明</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="access">
				<tr target="accessID" rel="${access.id }">
					<td>${access.merNo }</td>
					<td>${access.terNo }</td>
					<td>${access.accountType==0?'交易账户':'保证金账户' }</td>
					<td>${funcs:getStringColumnKey('CASHTYPE',access.cashType,'未知状态')}</td>
					<td>${access.deductionType }</td>
					<td>${access.currency }&nbsp;
					<c:if test="${access.amount>0 }">
						<font color='red'>+${access.amount }</font>
					</c:if>
					<c:if test="${access.amount<0 }">
						<font color='green'>${access.amount }</font>
					</c:if>
					</td>
					<td>${funcs:getStringColumnKey('MERCHANT_CHANNEL',access.merchantChannel,'未知状态')}</td>
					<td>
					${funcs:getStringColumnKey('ACCESSSTATUS',access.status,access.status)}
					</td>
					<td>${funcs:formatDate(access.moneyDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${access.moneyRemark }</td>
					<td>
					<%-- 提现类型cashType=1交易提现 2保证金提现 --%>
					<c:if test="${access.cashType<0 ||((access.cashType==1 || access.cashType==2)&& access.status!=2 && access.status != 4) }">
						<a href="javascript:exportDetail('${access.id }','${access.merNo }','${access.terNo }','${access.cashType }')">导出明细</a>
					</c:if>
					</td>
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