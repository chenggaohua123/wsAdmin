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
<title>投诉类型列表</title>
<script type="text/javascript">
$(function(){
	$.ajax({
		url : '<%=path %>/complaint/queryComplaintTypeInfo',
		type: 'post',
		data: {type: $('#typeHidden').val()},
		success : function(json){
			$('#complaintType').empty();
			if($('#typeHidden').val()=='0'){
				$('#complaintName').text('调查单');
			}else if($('#typeHidden').val()=='1'){
				$('#complaintName').text('拒付');
			}else if($('#typeHidden').val()=='2'){
				$('#complaintName').text('持卡人投诉');
			}else if($('#typeHidden').val()=='3'){
				$('#complaintName').text('伪冒单');
			}else{
				$('#complaintName').text('所有');
			}
			var selectValue = $('#complaintType').attr('selectedValue');
			if(selectValue!=undefined && selectValue!=null && selectValue!=''){
				selectValue = Number(selectValue);
			}
			$('#complaintType').append('<option value="">所有</option>');
			for(var i=0; i<json.length; i++){
				if(selectValue==json[i].id){
					$('#complaintType').append('<option selected="selected" value="'+json[i].id+'">'+json[i].cValue+'</option>');
				}else{
					$('#complaintType').append('<option value="'+json[i].id+'">'+json[i].cValue+'</option>');
				}
			}
		}
	});
});
function changeType(o){
	$.ajax({
		url : '<%=path %>/complaint/queryComplaintTypeInfo',
		type: 'post',
		data: {type: $(o).val()},
		success : function(json){
			$('#complaintType').empty();
			if($(o).val()=='0'){
				$('#complaintName').text('调查单');
			}else if($(o).val()=='1'){
				$('#complaintName').text('拒付');
			}else if($(o).val()=='2'){
				$('#complaintName').text('持卡人投诉');
			}else if($(o).val()=='3'){
				$('#complaintName').text('伪冒单');
			}else{
				$('#complaintName').text('所有');
			}
			var selectValue = $('#complaintType').attr('selectedValue');
			if(selectValue!=undefined && selectValue!=null && selectValue!=''){
				selectValue = Number(selectValue);
			}
			$('#complaintType').append('<option value="">所有</option>');
			for(var i=0; i<json.length; i++){
				if(selectValue==json[i].id){
					$('#complaintType').append('<option selected="selected" value="'+json[i].id+'">'+json[i].cValue+'</option>');
				}else{
					$('#complaintType').append('<option value="'+json[i].id+'">'+json[i].cValue+'</option>');
				}
			}
		}
	});
}
function complaintTypeChange(o){
	$('#complaintTypeValue').val($(o).val());
}
</script>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/complaint/queryCompWebsiteInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/complaint/queryCompWebsiteInfoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>网址：</label>
				<input type="text" name="payWebSite" value="${param.payWebSite}"/>
			</li>
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>年份：</label>
				<input type="text" name="year" value="${form.year}"/>
			</li>
			<li>
				<label>月份：</label>
				<input type="text" name="month" value="${form.month}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<%--
				ref="" refUrl="<%=path %>/complaint/queryComplaintTypeInfo?type={value}"
				 --%>
				<label>投诉类型：</label>
				<input type="hidden" id="typeHidden" value="${param.type }"/>
				<select name="type" class="combox" onchange="changeType(this)" >
					<option value="">所有</option>
					<option ${param.type=='0'?'selected':'' } value="0">调查单</option>
					<option ${param.type=='1'?'selected':'' } value="1">拒付</option>
					<option ${param.type=='2'?'selected':'' } value="2">持卡人投诉</option>
					<option ${param.type=='3'?'selected':'' } value="3">伪冒单</option>
				</select>
			</li>
			<li>
				<%--
				<label>调查类型：</label>
				<select class="combox" relValue="id" relText="cValue" name="complaintType" id="complaintType" style="width: 100px;">
					<option value="">所有</option>
				</select>
				 --%>
				<label id="complaintName">调查类型：</label>
				<input type="hidden" id="complaintTypeValue" name="complaintType" value="${form.complaintType }"/>
				<select id="complaintType" selectedValue="${form.complaintType }" onchange="complaintTypeChange(this)">
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
		 <li><a class="add" href="<%=path %>/complaint/exportCompWebsiteInfoList" target="dwzExport"><span>导出</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="165" style="text-align:center;" >
		<thead>
			<tr>
				<th>商户号</th>
				<th>终端号</th>
				<th>网址</th>
				<th>投诉数量</th>
				<th>网站状态</th>
				<th>投诉类型划分</th>
				<th>持卡人历史投诉</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr>
					<td>${info.merNo }</td>
					<td>${info.terNo }</td>
					<td>${info.payWebSite }</td>
					<td>${info.complCount }</td>
					<td>
						${funcs:getStringColumnKey('MERCHANTWEBSITESTATUS',info.websiteStatus,'未知状态')}
					</td>
					<td>
						<a href="<%=path %>/complaint/queryCompWebsiteCount?payWebSite=${info.payWebSite }&type=${param.type }&complaintType=${param.complaintType }&year=${param.year}&month=${param.month}" target="dialog" width="550" height="380" mask="true" rel="add" ><span>投诉类型划分</span></a>
					</td>
					<td>
						<a href="<%=path %>/complaint/queryCompHistoryInfo?complTradeNo=${info.complTradeNo }&type=${param.type }&complaintType=${param.complaintType }&year=${param.year}&month=${param.month}" target="dialog" width="550" height="380" mask="true" rel="add" ><span>持卡人历史投诉</span></a>
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