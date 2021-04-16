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
<title>勾兑上传记录</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/transchangemgr/listCheckTrans">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transchangemgr/listCheckTrans" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li>
				<label>参考号：</label>
				<input type="text" name="relNo" value="${param.relNo }"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>状态：</label>
				<select name="status">
					<option value="" ${param.status==''?'selected':'' }>所有</option>
					<option value="0" ${param.status=='0'?'selected':'' }>未勾兑</option>
					<option value="1" ${param.status=='1'?'selected':'' }>已勾兑</option>
					<option value="2" ${param.status=='2'?'selected':'' }>勾兑异常</option>
				</select>
			</li>
			<li  class="dateRange">
			  <label>上传时间</label>
			  <input type="text" name="dateStart"  id = "dateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="dateEnd"  id = "dateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.dateEnd}"/>		
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
			<li><a class="edit" href="<%=path %>/transchangemgr/goUpdateTransChecked?id={transCheckID}" target="dialog" width="550" height="400" mask="true" warn="请选择一条记录"><span>审核</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="140" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>参考号</th>
				<th>标签金额</th>
				<th>上传时间</th>
				<th>上传人</th>
				<th>最后操作人</th>
				<th>最后操作时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="transCheck">
				<tr  target="transCheckID" rel="${transCheck.id }"   >
					<td>${transCheck.tradeNo }</td>
					<td>${transCheck.relNo }</td>
					<td>${transCheck.currency }&nbsp;${transCheck.amount }</td>
					<td>${transCheck.updateTime }</td>
					<td>${transCheck.updateBy }</td>
					<td>${transCheck.lastModifyBy }</td>
					<td>${transCheck.lastModifyTime }</td>
					<td>
					<c:if test="${transCheck.status==0 }">
						<font color="black">未勾兑</font>
					</c:if>
					<c:if test="${transCheck.status==1 }">
						<font color="green">已勾兑</font>
					</c:if>
					<c:if test="${transCheck.status==2 }">
						<font color="red">勾兑异常</font>
					</c:if>
					<c:if test="${transCheck.status==3 }">
						<font color="red">无效</font>
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