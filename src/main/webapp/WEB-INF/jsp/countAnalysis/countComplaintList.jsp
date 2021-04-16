<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="funcs" uri="funcs"%> 
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投诉订单分析</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/countComplaintList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/countComplaintList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>年：</label>
				<select name="countYear" class="combox">
					<option value="" ${form['countYear']==''?'selected':'' }>所有</option>
					<option value="2015" ${form['countYear']=='2015'?'selected':'' }>2015年</option>
					<option value="2016" ${form['countYear']=='2016'?'selected':'' }>2016年</option>
					<option value="2017" ${form['countYear']=='2017'?'selected':'' }>2017年</option>
					<option value="2018" ${form['countYear']=='2018'?'selected':'' }>2018年</option>
					<option value="2019" ${form['countYear']=='2019'?'selected':'' }>2019年</option>
					<option value="2020" ${form['countYear']=='2020'?'selected':'' }>2020年</option>
					<option value="2021" ${form['countYear']=='2021'?'selected':'' }>2021年</option>
					<option value="2022" ${form['countYear']=='2022'?'selected':'' }>2022年</option>
					<option value="2023" ${form['countYear']=='2023'?'selected':'' }>2023年</option>
					<option value="2024" ${form['countYear']=='2024'?'selected':'' }>2024年</option>
				</select>
			</li>
			<li>
				<label>月：</label>
				<select name="countMonth" class="combox">
					<option value="" ${form['countMonth']==''?'selected':'' }>所有</option>
					<option value="1" <c:if test="${form['countMonth']=='1'}">selected</c:if>>1</option>
					<option value="2" <c:if test="${form['countMonth']=='2'}">selected</c:if>>2</option>
					<option value="3" <c:if test="${form['countMonth']=='3'}">selected</c:if>>3</option>
					<option value="4" <c:if test="${form['countMonth']=='4'}">selected</c:if>>4</option>
					<option value="5" <c:if test="${form['countMonth']=='5'}">selected</c:if>>5</option>
					<option value="6" <c:if test="${form['countMonth']=='6'}">selected</c:if>>6</option>
					<option value="7" <c:if test="${form['countMonth']=='7'}">selected</c:if>>7</option>
					<option value="8" <c:if test="${form['countMonth']=='8'}">selected</c:if>>8</option>
					<option value="9" <c:if test="${form['countMonth']=='9'}">selected</c:if>>9</option>
					<option value="10" <c:if test="${form['countMonth']=='10'}">selected</c:if>>10</option>
					<option value="11" <c:if test="${form['countMonth']=='11'}">selected</c:if>>11</option>
					<option value="12" <c:if test="${form['countMonth']=='12'}">selected</c:if>>12</option>
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
		<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
			<thead>
				<tr>
					<th>统计方式</th>
					<th>交易成功笔数</th>
					<th>投诉笔数</th>
					<th>投诉率</th>
					<th>投诉转拒付笔数</th>
					<th>投诉转拒付率</th>
					<th>查看占比</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr  >
						<td>${info.time==0?'所有':info.time }</td>
						<td>${info.successCount }</td>
						<td>${info.comCount }</td>
						<td>
						<c:choose>
							<c:when test="${info.successCount==0 }">
							0.00%
							</c:when>
							<c:otherwise>
							<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.comCount/info.successCount*100}"/>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>${info.disCount}</td>
						
						<td>
						<c:choose>
							<c:when test="${info.comCount==0 }">
							0.00%
							</c:when>
							<c:otherwise>
							<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disCount/info.comCount*100}"/>%
							</c:otherwise>
						</c:choose>
						</td>
						<td>
						<a  href="<%=path %>/countAnalysis/showComPer?merNo=${param.merNo}&time=${info.time}"  target="dialog" width="650" height="300" rel="query11Conf1111ig"  mask="true">投诉构成</a> | 
						<a  href="<%=path %>/countAnalysis/downloadCompCountInfo?merNo=${param.merNo}&time=${info.time}" target="dwzExport" targetType="navTab"rel="addCurre11111ncy" width="550" height="300" mask="true">导出明细</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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