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
<title>风险订单查询</title>
</head>
<body>
<script type="text/javascript">
function brandLimit_selectAll(){
	if ($("#brandLimit_selectAll").attr("checked")) {  
    	$("input[name='brandLimit_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='brandLimit_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function deleteBrandLimitInfo(){
	if(confirm("是否删除！")){
		var str=""; 
		 $("input[name='brandLimit_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		 alert(str);
		if(!str){
			alert("请选择删除数据");
		}else{
			var url="<%=path %>"+"/riskmgr/deleteBrandLimitInfo?"+str;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:'text',
				success:function(data){
					alert(data.message);
					$("#branklimitform").submit();
				}
			});
		}
	}
	
};
</script>
<form id="pagerForm" method="post" action="<%=path %>/riskmgr/listBrandLimitInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="branklimitform" onsubmit="return navTabSearch(this);" action="<%=path %>/riskmgr/listBrandLimitInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>品牌：</label>
				<input type="text" name="brand" value="${param.brand}"/>
			</li>
			<li>
				<label>国家：</label>
				<input type="text" name="countrys" value="${param.countrys}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>卡种：</label>
				<input type="text" name="cardType" value="${param.cardType }"/>
			</li>
			 <li  class="dateRange">
			  <label>创建时间</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateEnd']}"/>		
			</li>
			<li>
				<label>银行：</label>
				 <select class="combox" selectedValue="${param.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>限制条件：</label>
				<select class="combox" name="enabled">
					<option value="" ${param.enabeld==''?'selected':'' }>所有</option>
					<option value="0" ${param.enabeld=='0'?'selected':'' }>不限制</option>
					<option value="1" ${param.enabeld=='1'?'selected':'' }>限制</option>
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
		<!--li>< a class="add" href="<%=path %>/riskmgr/exportRiskTransInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出明细</span></a></li-->
		<li><a class="add" href="<%=path %>/riskmgr/goAddBrandLimitInfo" target="dialog" width="550" height="400" mask="true"  rel="settleType" ><span>添加</span></a></li>
			<li><a class="edit" href="<%=path %>/riskmgr/goUpdateBrandLimitInfo?id={rateId}" target="dialog" width="550" height="400" mask="true"  rel="settleType"  warn="请选择修改的数据"><span>修改</span></a></li>
			<li><a class="delete" href="javascript:deleteBrandLimitInfo()" id="deleteBrandLimitInfo"  width="1000" height="600" mask="true"  rel="settleType1" ><span>批量删除</span></a></li>
		</ul>
	</div>
		<table class="list" width="100%" targetType="navTab" layoutH="189" style="text-align: center;">
			<thead>
				<tr>
					<th><input type="checkbox" name="brandLimit_selectAll" id="brandLimit_selectAll" onclick="brandLimit_selectAll()"></th>
					<th>商户号</th>
					<th>终端号</th>
					<th>银行</th>
					<th>品牌</th>
					<th>国家</th>
					<th>卡种</th>
					<th>限制条件</th>
					<th>创建时间</th>
					<th>创建人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.data}" var="info">
					<tr  target="rateId" rel="${info.id }"   >
						<td><input type="checkbox" name="brandLimit_ids" value="${info.id }" id="brandLimit_ids"></td>
						<td>${info.merNo==0?'所有':info.merNo}</td>
						<td>${info.terNo==0?'所有':info.terNo}</td>
						<td>${empty info.bankName?'所有':info.bankName }</td>
						<td>${info.brand }</td>
						<td>${info.countrys }</td>
						<td>${info.cardType=='0'?'所有':info.cardType }</td>
						<td>${info.enabled=='1'?'<font color=red>限制</font>':'不限制'}</td>
						<td>${funcs:formatDate(info.createDate,'yyyy-MM-dd HH:mm')} </td>
						<td>${info.createBy }</td>
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