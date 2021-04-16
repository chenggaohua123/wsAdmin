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
<title>多选择通道信息查找带回</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#currencyNameKeypress").keyup(function(){
			var value=$(this).val().trim();
			if(""!=value){
				var ele=$("."+value).offset();
				if(ele!=null){
					var firsty=$("#scrollDiv thead").first().offset().top;
					var y=$("."+value).offset().top-firsty;
					var et=$("#scrollDiv").parent();
					et.scrollTop(y);
					$("."+value).siblings().removeClass("selected");
					$("."+value).addClass("selected");
					
				}
			}else{
				var et=$("#scrollDiv").parent();
				et.scrollTop(0);
			}
			
			
			
		});
		$("#checkAll").checkboxCtrl($("#scrollDiv"));
		$("#checkAll").click(function(){
			$(this).checkboxCtrl($("#scrollDiv"));
			setTimeout(function(){
				$("#emptyCurrency").attr("checked","checked");
			},"20");
			var flag=$(this).is(":checked");
			if(flag){
				$("#selectIds").html("选中所有通道");
			}else{
				$("#selectIds").html("");
			}
		});
		
		$("#scrollDiv tbody input[name='currency']").click(function(){
			
			showCurrencyName();
		});
		
		showCurrencyName();
	});
	
	function showCurrencyName(){
		var context="";
		$("#scrollDiv tbody input[name='currency']").each(function(){
			var flag=$(this).is(":checked");
			
			if(flag){
				var dataValue=$(this).val();
				var json= eval("(" + dataValue + ")");
				var currencyName=json.currencyName;
				if(currencyName!=""){
					context=context+json.currencyName+",";
				}
			}
			
		});
		if(context.indexOf(",")>0){
			context=context.substring(0, context.lastIndexOf(","));
		}
		$("#selectIds").html(context);
	}
</script>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getCurrencyListBrightBackMany">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
	<input type="hidden" id="currencyIds" name="currencyIds" value="${currencyIds}">
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="<%=path %>/merchantmgr/getCurrencyListBrightBackMany?currencyIds=${currencyIds}" >
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>通道名称：</label>
				<input type="text" name="currencyName" id="currencyNameKeypress" value="${param.currencyName }"/>
			</li>
			<%-- <li>
			<label>状态：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTCONFIG"  relValue="columnKey" selectedValue="${param.enabled }" relText="columnvalue" name="enabled">
					<option value="">所有</option>
				</select>
			</li> --%>
		</ul>
		<div class="subBar">
		<span style="line-height: 25px;margin-left: 5px;">已选中的通道：<span id="selectIds" >
		</span></span>
			<ul><!-- <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li> -->
				<li><div class="button"><div class="buttonContent"><button type="button" multLookup="currency" >选择带回</button></div></div></li>
				<!-- <li style="margin-left: 20px;"><div class="buttonActive"><div class="buttonContent"><button type="button" value="保存" onclick="saveCurrencyOfSpare()">保存</button></div></div></li> -->
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
	<table class="list" width="98%" targetType="navTab" layoutH="115" style="text-align: center;" id="scrollDiv" >
		<thead>
			<tr>
				<th><input type="checkbox" id="checkAll"  group="currency"></th>
				<th>通道ID</th>
				<th>银行名称</th>
				<th>通道名称</th>
				<th>是否关闭</th>
				<th>通道所属</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<tr style="display:none;">
				<td colspan="7"><input type="checkbox" id="emptyCurrency" name="currency"  value="{currencyId:'',bankName:'',currencyName:''}" readonly="readonly" checked="checked" /></td>
				
			</tr>
			<c:forEach items="${page.data }" var="bank" varStatus="status">
				
				<tr target="bankId" rel="${bank.id }" class="${bank.currencyName }">
					<td>
					
					<input type="checkbox" name="currency" value="{currencyId:'${bank.id }',bankName:'${bank.bankName }',currencyName:'${bank.currencyName}'}"
						<c:forEach items="${idsList }" var="id">
							<c:if test="${bank.id==id}">checked="checked"</c:if>
						</c:forEach>
					>
					</td>
					<td>${bank.id }</td>
					<td>${bank.bankName }</td>
					<td>${bank.currencyName }</td>
					<td>${funcs:getStringColumnKey('MERCHANTCONFIG',bank.enabled,'未知状态')}</td>
					<td>${bank.currencyBelongs} </td>
					<td>${bank.remark }</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	</div>
	<%-- <div class="panelBar" >
		<div class="pages">
			
			<span>显示</span>
			<select name="numPerPage" class="combox"  onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" ${param.numPerPage==20?'selected':'' }>20</option>
				<option value="50" ${param.numPerPage==50?'selected':'' }>50</option>
				<option value="100" ${param.numPerPage==100?'selected':'' } >100</option>
				<option value="200" ${param.numPerPage==200?'selected':'' }>200</option>
			</select>
			<span>条，共${page.total }条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${page.total }" numPerPage="${page.numPerPage }" pageNumShown="10" currentPage="${page.nowPage }"></div>
	</div> --%>
</div>
</body>
</html>