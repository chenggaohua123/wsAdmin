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
<title>代理商列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/newsmgr/listNews">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/newsmgr/listNews" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>标题：</label>
				<input type="text" name="newsTitle" value="${param.newsTitle }"/>
			</li>
			<li>
				<label>类型：</label>
				<select name="newsType" class="combox">
				<option value="" ${param.newsType==''?'selected':'' }>所有</option>
				<option value="0" ${param.newsType=='0'?'selected':'' }>新闻</option>
				<option value="1" ${param.newsType=='1'?'selected':'' }>公告</option>
				</select>
			</li>
			<li>
				<label>发布状态：</label>
				<select name="enableFlag" class="combox">
				<option value="" ${param.enableFlag==''?'selected':'' }>所有</option>
				<option value="0" ${param.enableFlag=='0'?'selected':'' }>未发布</option>
				<option value="1" ${param.enableFlag=='1'?'selected':'' }>已发布</option>
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
		    <li><a class="add" href="<%=path %>/newsmgr/goAddNews" target="dialog" width="950" height="400" mask="true"  mask="true"  rel="agentMerchant" ><span>新增新闻公告</span></a></li>
			<li><a class="edit" href="<%=path %>/newsmgr/goUpdateNews?id={newsId}" target="dialog" width="850" height="400" mask="true"><span>修改新闻公告</span></a></li>
			 <li><a class="add" href="<%=path %>/newsmgr/showNews?id={newsId}" target="dialog" width="950" height="400" mask="true"  mask="true"  rel="agentMerchant" ><span>查看</span></a></li>
			  <li><a class="delete" href="<%=path %>/newsmgr/deleteNews?id={newsId}" target="ajaxTodo" width="950" height="400" mask="true"  mask="true"  rel="agentMerchant" ><span>删除</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
				<th>标题</th>
				<th>类型</th>
				<th>是否置顶</th>
				<th>状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>最后修改人</th>
				<th>最后修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="news">
				<tr target="newsId" rel="${news.id }"    align="center">
					<td>${news.newsTitle }</td>
					<td>${news.newsType==0?'新闻':'公告' }</td>
					<td>${news.isTop==1?'置顶':'不置顶'}</td>
					<td>${news.enableFlag==1?'已发布':'未发布'}</td>
					<td>${news.createBy }</td>
					<td>${funcs:formatDate(news.createDate,'yyyy-MM-dd HH:mm')}</td>
					<td>${news.lastModify }</td>
					<td>${funcs:formatDate(news.lastModifyDate,'yyyy-MM-dd HH:mm')}</td>
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