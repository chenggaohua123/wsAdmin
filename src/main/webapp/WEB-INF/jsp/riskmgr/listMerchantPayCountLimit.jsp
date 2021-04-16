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
<title>商户重复支付限定</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/riskmgr/listMerchantPayCountLimit">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/riskmgr/listMerchantPayCountLimit" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>类型：</label>
				<select name="type" class="combox">
					<option ${param.type==''?'selected':'' } value="">所有</option>
					<option value="cardNo" ${param.type=='cardNo'?'selected':'' }>卡号</option>
					<option value="email" ${param.type=='email'?'selected':'' }>邮箱</option>
					<option value="IP" ${param.type=='IP'?'selected':'' }>IP</option>
				 </select>
			</li>
			<li>
				<label>状态：</label>
					<select name="status" class="combox">
					<option ${param.status==''?'selected':'' } value="">所有</option>
					<option value="0" ${param.status=='0'?'selected':'' }>待审核</option>
					<option value="1" ${param.status=='1'?'selected':'' }>审核通过</option>
					<option value="2" ${param.status=='2'?'selected':'' }>审核驳回</option>
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
		<li><a class="edit" href="<%=path %>/riskmgr/goUpdateMerchantPayCountLimit?id={rateId}" target="dialog"  rel="updatePayCountLimit" width="550" height="300" mask="true" warn="请选择要审核的记录"><span>审核</span></a></li>
		<li><a class="add" href="<%=path %>/riskmgr/goAddOrUpdateMerchantPayCountLimit" target="dialog" width="550" height="400" mask="true"  rel="settleType" ><span>添加</span></a></li>
		<li><a class="edit" href="<%=path %>/riskmgr/goAddOrUpdateMerchantPayCountLimit?id={rateId}" target="dialog" width="550" height="350" mask="true"  rel="settleType"  warn="请选择修改的数据"><span>修改</span></a></li>
		<li><a class="edit" href="<%=path %>/riskmgr/deleteMerchantPayCountLimit?id={rateId}" target="ajaxTodo" width="550" height="350" mask="true"  rel="settleType"  warn="请选择删除的数据"><span>删除</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="115" style="text-align: center;">
			<thead>
				<tr>
					<th>商户号</th>
					<th>终端号</th>
					<th>限制类型</th>
					<th>限制时间(H)</th>
					<th>限制成功次数</th>
					<th>状态</th>
					<!--  th>申请人</th-->
					<th>申请时间</th>
					<th>审核人</th>
					<th>审核时间</th>
					<th>审核意见</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr  target="rateId" rel="${info.id }"   >
						<td>${info.merNo }</td>
						<td>${info.terNo==0?'所有':info.terNo }</td>
						<td>${info.type }</td>
						<td>${info.limitTime }</td>
						<td>${info.limitCount }</td>
						<td>
						<c:if test="${info.status==0 }">待审核</c:if>
						<c:if test="${info.status==1 }">审核通过</c:if>
						<c:if test="${info.status==2 }">审核驳回</c:if>
						</td>
						<!--  td>${info.createBy }</td-->
						<td>${funcs:formatDate(info.createDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${info.lastModifyBy }</td>
						<td>${funcs:formatDate(info.lastMofifyDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${info.remark }</td>
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