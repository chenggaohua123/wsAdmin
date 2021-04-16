<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易跟踪统计</title>
</head>
<script type="text/javascript">
function exportInfos(description){
	var param='?';
	$("#tradeRecodeCount input,select").each(function(){
		if($(this).attr("name")!=undefined){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	window.location.href="<%=path%>/countAnalysis/exportTransRecordInfo"+param+"description="+description;
}
</script>
<body>

<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/countTransRecord">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/countTransRecord" method="post">
	<div class="searchBar" id="tradeRecodeCount">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }">
			</li>
			<li>
				<label>网站：</label>
				<input type="text" name="merWebsite" value="${param.merWebsite }">
			</li>
			 <li  class="dateRange">
			  <label>进入系统时间</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			</li>
		</ul>
		<ul  class="searchContent">
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
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="140" style="text-align:center;" >
		<thead>
			<tr>
				  <th>描述</th>
				  <th>笔数 </th>
                  <th>占比</th>
                  <th>导出</th>
                  <th>异常原因内容构成</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.description }</td>
					<td>${info.count }</td>
					<td><fmt:formatNumber value="${info.decRate*100 }" maxFractionDigits="2" minFractionDigits="2"></fmt:formatNumber>%</td>
					<td><a href="javascript:exportInfos('${info.description }')">导出</a></td>
					<td>
					<a  href="<%=path %>/countAnalysis/showTransRecordPer?merNo=${param.merNo}&merWebsite=${param.merWebsite}&startDate=${form['startDate']}&endDate=${form['endDate']}&type=${param.type}&description=${info.description }"  target="dialog" width="650" height="300" rel="query11Conf11ig"  mask="true">异常构成</a>
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