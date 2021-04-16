<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>   

<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易预警管理</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
</head>
<body>
 <form id="pagerForm" method="post" action="<%=path %>/warnmgr/listTransWarnRuleInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
 </form>
<div class="pageHeader">
	<form rel="pagerForm" id="TransarnForm" onsubmit="return navTabSearch(this);" action="<%=path %>/warnmgr/listTransWarnRuleInfo" method="post">
	  <div class="searchBar">
		<ul class="searchContent">
		 <li>
			<label>银行</label>
			<select class="combox" selectedValue="${param.bankId }"
				loadurl="<%=path %>/warnmgr/queryBankInfoList"
				relValue="bankId" relText="bankName" name="bankId">
			</select>
		 </li>
		 <li>
			<label>监控条件</label>
			<select name="type" class="combox" size="30">
				<option value="" <c:if test="${param.type=='' || empty param.type }">selected</c:if> >全部</option>
			    <option value="0" <c:if test="${param.type=='0' }">selected</c:if> >交易返回信息</option>
			    <option value="1" <c:if test="${param.type=='1' }">selected</c:if> >续交易失败</option>
			    <option value="2" <c:if test="${param.type=='2' }">selected</c:if> >无新增交易</option>
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
		    <li><a class="add" href="<%=path %>/warnmgr/goAddTransWarnRule" target="dialog" width="550" height="400" mask="true" rel="addWarn" mask="true"  ><span>新增</span></a></li>
		    <li><a class="edit" href="<%=path %>/warnmgr/goUpdateTransWarnRule?id={id}" target="dialog" width="750" height="450" rel="update" mask="true"><span>修改</span></a></li>
		    <li><a class="delete" href="<%=path %>/warnmgr/delTransWarnRuleInfo?id={id}" target="ajaxTodo" title="请慎重删除,确定删除该参数吗?" mask="true"><span>删除</span></a></li>
		    <li><a class="delete" href="javascript:deleteTransWarnInfo()" id="deleteBrandLimitInfo"  width="1000" height="600" mask="true"  rel="dels" ><span>批量删除</span></a></li>
		    <li><a class="edit" href="<%=path %>/warnmgr/goTransWarnRelPhone?warnId={id}" target="dialog" width="800" height="500" mask="true"  rel="phonePageForm" ><span>添加电话</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	   <table class="list" width="100%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
						<th><input type="checkbox" name="transWarn_selectAll" id="transWarn_selectAll" onclick="transWarn_selectAll()"></th>
						<th>银行</th>
						<th>监控条件</th>
						<th>监控时效</th>
						<th>预警发送邮箱</th>
						<th>预警邮件发送间隔时间</th>
						<th>创建人</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="warnInfo">
						<tr  target="id" rel="${warnInfo.id }"    align="center">
							<td><input type="checkbox" name="transWarn_ids" value="${warnInfo.id }" id="transWarn_ids"></td>
							<td>${warnInfo.bankName }</td>
							<td>
								<c:if test="${warnInfo.type==0 }">交易返回信息</c:if>
								<c:if test="${warnInfo.type==1 }">连续交易失败</c:if>
								<c:if test="${warnInfo.type==2 }">无新增交易</c:if>
							</td>
							<td>${warnInfo.activeTime }</td>
							<td>${warnInfo.emails }</td>
							<td>${warnInfo.cycle }</td>
							<td>${warnInfo.createBy }</td>
							<td>${warnInfo.createDate }</td>
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
 <script type="text/javascript">
function transWarn_selectAll(){
	if ($("#transWarn_selectAll").attr("checked")) {  
    	$("input[name='transWarn_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='transWarn_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function deleteTransWarnInfo(){
	if(confirm("是否删除！")){
		var str=""; 
		 $("input[name='transWarn_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		if(!str){
			alert("请选择删除数据");
		}else{
			var url="<%=path %>"+"/warnmgr/delTransWarnRuleInfos?"+str;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:'text',
				success:function(data){
					alert(data.message);
					$("#TransarnForm").submit();
				}
			});
		}
	}
	
};
</script>
</body>
</html>