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
<title>银行汇率审核</title>
</head>
<script type="text/javascript">
	function lsitCheckBankSource_selectAll(){  
		if ($("#lsitCheckBankSource_selectAll").attr("checked")) {  
	    	$("input[name='lsitCheckBankSource_ids']").each(function(){
		        $(this).attr("checked", true);  
	    	})
	    } else {  
	    	$("input[name='lsitCheckBankSource_ids']").each(function(){
		        $(this).attr("checked", false);  
	    	})
	    }  
	}  
	function checkBankSourceRate(path,status){
		var str=""; 
		 $("input[name='lsitCheckBankSource_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		}); 
		var url=path+"/exchangRate/checkBankSourceRate?"+str+"status="+status;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			data:'text',
			success:function(data){
				if(data>0){
					alert("成功审核："+data+"笔数据");
					$("#fromId").submit();
				}else{
					alert("审核失败");
				}
			}
		});	
	}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/exchangRate/listCheckBankSourceRate">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" id="fromId" onsubmit="return navTabSearch(this);" action="<%=path %>/exchangRate/listCheckBankSourceRate" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>原始币种：</label>
				<input type="text" name="sourceCurrency" value="${param.sourceCurrency }"/>
			</li>
			<li>
				<label>目标币种：</label>
				<input type="text" name="targetCurrency" value="${param.targetCurrency }"/>
			</li>
			<li>
				<label>组名：</label>
				<input type="text" name="groupName" value="${param.groupName}"/>
			</li>
			
		</ul>
		<ul class="searchContent">
			<li>
				<label>汇率类型：</label>
				<select name="type">
					<option value="" ${param.type==''?'selected':'' }>所有</option>
					<option value="bus" ${param.type=='bus'?'selected':'' }>交易汇率</option>
					<option value="settle" ${param.type=='settle'?'selected':'' }>结算汇率</option>
				</select>
			</li>
			 <li  class="dateRange">
			  <label>生成时间</label>
			  <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateEnd}"/>		
			</li>
			 <li  class="dateRange">
			  <label>审核时间</label>
			  <input type="text" name="updateDateStart"  id = "updateDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.updateDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="updateDateEnd"  id = "updateDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.updateDateEnd}"/>		
			</li>
			
		</ul>	
			<ul class="searchContent">
				<li>
				<label>审核类型：</label>
				<select name="status">
					<option value="" ${param.status==''?'selected':'' }>所有</option>
					<option value="0" ${param.status=='0'?'selected':'' }>待审核</option>
					<option value="1" ${param.status=='1'?'selected':'' }>审核通过</option>
					<option value="2" ${param.status=='2'?'selected':'' }>审核不通过</option>
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
			<li><a class="edit" href="javascript:checkBankSourceRate('<%=path %>',1)"><span>审核通过</span></a></li>
			<li><a class="edit" href="javascript:checkBankSourceRate('<%=path %>',2)"><span>审核不通过</span></a></li>
			<li>
			<span style="color:red;font-size:12px;">组名为0表是默认汇率,所有商户都生效.</span>
			</li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="160" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" id="lsitCheckBankSource_selectAll" onclick="lsitCheckBankSource_selectAll()"></th>
				<th>组名</th>
				<th>汇率类型</th>
				<th>银行汇率类型</th>
				<th>原始币种</th>
				<th>目标币种</th>
				<th>汇率</th>
				<th>银行汇率</th>
				<th>创建 人</th>
				<th>创建时间</th>
				<th>审核 人</th>
				<th>审核时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody id="lsitCheckBankSource">
			<c:forEach items="${page.data }" var="exchangRate">
				<tr  target="rateId" rel="${exchangRate.id }"   >
					<td>
					<c:if test="${exchangRate.status==0 }">
						<input type="checkbox" name="lsitCheckBankSource_ids" value="${exchangRate.id }" id="ids">
					</c:if>
					</td>
					<td>${exchangRate.groupName }</td>
					<td>${exchangRate.type=='settle'?'结算汇率':'交易汇率'}</td>
					<td>${funcs:getStringColumnKey('bankRateType',exchangRate.bankRateType,'未知状态')}</td>
					<td>${exchangRate.sourceCurrency}</td>
					<td>${exchangRate.targetCurrency}</td>
					<td>${exchangRate.rate}</td>
					<td>${exchangRate.bankRate}</td>
					<td>${exchangRate.createBy}</td>
					<td>${exchangRate.createDate}</td>
					<td>${exchangRate.updateBy}</td>
					<td>${exchangRate.updateDate}</td>
					<td>
						<c:if test="${exchangRate.status==0 }">待审核</c:if>
						<c:if test="${exchangRate.status==1 }">审核通过</c:if>
						<c:if test="${exchangRate.status==2 }">审核不通过</c:if>
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