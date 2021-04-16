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
 <form id="pagerForm" method="post" action="<%=path %>/warnmgr/listMerchantWarnRuleInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
 </form>
<div class="pageHeader">
	<form rel="pagerForm" id="TransarnForm" onsubmit="return navTabSearch(this);" action="<%=path %>/warnmgr/listMerchantWarnRuleInfo" method="post">
	  <div class="searchBar">
		<ul class="searchContent">
		 <li>
			<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 </li>
		 <li>
			<label>监控条件</label>
			<select name="type" class="combox" size="30">
				<option value="" <c:if test="${param.type=='' || empty param.type }">selected</c:if> >全部</option>
			    <option value="0" <c:if test="${param.type=='0' }">selected</c:if> >交易返回信息</option>
			    <option value="1" <c:if test="${param.type=='1' }">selected</c:if> >续交易失败</option>
			    <option value="2" <c:if test="${param.type=='2' }">selected</c:if> >无新增交易</option>
			    <option value="3" <c:if test="${param.type=='3' }">selected</c:if> >风险交易</option>
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
		    <li><a class="add" href="<%=path %>/warnmgr/goAddMerchantTransWarnRule" target="dialog" width="550" height="400" mask="true" rel="addWarn" mask="true"  ><span>新增</span></a></li>
		    <li><a class="edit" href="<%=path %>/warnmgr/goUpdateMerchantTransWarnRule?id={id}" target="dialog" width="550" height="400" rel="update" mask="true"><span>修改</span></a></li>
		    <%--
		    <li><a class="delete" href="<%=path %>/warnmgr/delTransWarnRuleInfo?id={id}" target="ajaxTodo" title="请慎重删除,确定删除该参数吗?" mask="true"><span>删除</span></a></li>
		     --%>
		    <li><a class="delete" href="javascript:deleteTransWarnInfo()" id="deleteBrandLimitInfo"  width="1000" height="600" mask="true"  rel="dels" ><span>批量删除</span></a></li>
		    <li><a class="edit" href="<%=path %>/warnmgr/goTransWarnRelPhone?warnId={id}" target="dialog" width="800" height="500" mask="true"  rel="phonePageForm" ><span>关联联系人</span></a></li>
		    <li><a class="add" href="<%=path %>/warnmgr/goAddMerchantMessageModel?warnId={id}" target="dialog" width="550" height="400" mask="true"  rel="addMessage" ><span>添加短信内容</span></a></li>
		    <li><a class="edit" href="<%=path %>/warnmgr/goUpdateMerchantMessageModelInfo?warnId={id}" target="dialog" width="550" height="400" mask="true"  rel="updateMessage" ><span>修改短信内容</span></a></li>
		    <%--
		    <li><a class="delete" href="<%=path %>/warnmgr/delMessageModelInfo?warnId={id}" target="ajaxTodo" title="请慎重删除,确定删除短信吗?" mask="true"><span>删除短信内容</span></a></li>
		     --%>
		</ul>
	</div>
	<div id="w_list_print">
	   <table class="list" width="100%" targetType="navTab" layoutH="115">
		<thead>
			<tr>
						<th><input type="checkbox" name="merchantWarn_selectAll" id="merchantWarn_selectAll" onclick="transWarn_selectAll()"></th>
						<th>商户号</th>
						<th>监控条件</th>
						<th>监控时效</th>
						<%--
						<th>预警发送邮箱</th>
						 --%>
						<th>预警邮件发送间隔时间</th>
						<th>出现笔数</th>
						<th>失败原因内容</th>
						<th>创建人</th>
						<th>创建时间</th>
						<th>修改人</th>
						<th>修改时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="warnInfo">
						<tr  target="id" rel="${warnInfo.id }"    align="center">
							<td><input type="checkbox" name="merchantWarn_ids" value="${warnInfo.id }" id="merchantWarn_ids"></td>
							<td>${warnInfo.merNo }</td>
							<td>
								<c:if test="${warnInfo.type==0 }">交易返回信息</c:if>
								<c:if test="${warnInfo.type==1 }">连续交易失败</c:if>
								<c:if test="${warnInfo.type==2 }">无新增交易</c:if>
								<c:if test="${warnInfo.type==3 }">风险交易</c:if>
							</td>
							<td>${warnInfo.activeTime } 分钟</td>
							<%--
							<td>${warnInfo.emails }</td>
							 --%>
							<td>${warnInfo.cycle } 分钟</td>
							<td>${warnInfo.time } 笔</td>
							<td>${warnInfo.respMsg }</td>
							<td>${warnInfo.createBy }</td>
							<td>${warnInfo.createDate }</td>
							<td>${warnInfo.lastModifyBy }</td>
							<td>${warnInfo.lastModifyDate }</td>
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
	if ($("#merchantWarn_selectAll").attr("checked")) {  
    	$("input[name='merchantWarn_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='merchantWarn_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function deleteTransWarnInfo(){
	if(confirm("是否删除！")){
		var str=""; 
		 $("input[name='merchantWarn_ids']:checked").each(function(){ 
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