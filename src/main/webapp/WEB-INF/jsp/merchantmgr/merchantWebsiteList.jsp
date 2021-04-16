<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">

function downloadErrorInfosForWebsite(){
	window.location.href='<%=path %>/merchantmgr/downloadbatchWebsiteErrorInfo';
}
function web_selectAll(){
	if ($("#web_selectAll").attr("checked")) {  
    	$("input[name='merWeb_ids']").each(function(){
	        $(this).attr("checked", true);  
    	})
    } else {  
    	$("input[name='merWeb_ids']").each(function(){
	        $(this).attr("checked", false);  
    	})
    }  
};

function deleteWebsite(){
	if(confirm("是否删除！")){
		var str=""; 
		 $("input[name='merWeb_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		if(!str){
			alert("请选择删除数据");
		}else{
			var url="<%=path %>"+"/merchantmgr/deleteWebsite?"+str;
			$.ajax({
				url:url,
				type:'post',
				dataType:'json',
				data:'text',
				success:function(data){
					alert(data.message);
					$("#websiteFromId").submit();
				}
			});
		}
	}
	
};

function checkWebsite(){
	var str=""; 
	 $("input[name='merWeb_ids']:checked").each(function(){ 
		str+="ids="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选择审核数据");
	}else{
		var options = {mask:true,
                width:650, height:500,
                maxable: eval("true"),
                resizable:eval("true")
            }; 
		var url="<%=path %>"+"/merchantmgr/goBatchCheckMerchantWebsite?"+str+"type=0";
		$.pdialog.open(url, "merWebsits", "批量审核网站",options);
	}
};

function setWebsiteInfo(){
	var str=""; 
	 $("input[name='merWeb_ids']:checked").each(function(){ 
		str+="ids="+$(this).val()+"&"; 
	});
	if(!str){
		alert("请选择需要设置数据");
	}else{
		var options = {mask:true,
               width:650, height:500,
               maxable: eval("true"),
               resizable:eval("true")
           }; 
		var url="<%=path %>"+"/merchantmgr/goBatchCheckMerchantWebsite?"+str+"type=1";
		$.pdialog.open(url, "merWebsits", "批量设置网站数据",options);
	}
}
function getWebsiteList(){
        var name; //网页名称，可为空;
        var iWidth = "600"; //弹出窗口的宽度;
        var iHeight = "700"; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        var win = window.open("<%=path %>/merchantmgr/goQureyManyWebsite", "newWindow", 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth +
            ',innerWidth=' +
            iWidth + ',top=' + iTop + ',left=' + iLeft +
            ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
        
}
    function sele(NO) {
        //NO为返回值
        $("#websites").val(NO);
    }
function initialization(){
	$(".searchBar ul li input").val("");
	
}

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户网站管理</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/merchantmgr/getListMerchantWebsite">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" id="websiteFromId" action="<%=path %>/merchantmgr/getListMerchantWebsite" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo }"/>
			</li>
			<li>
			<label>状态：</label>
			<select name="status" class="combox">
				<option value="" ${param.status==''?'selected':'' }>--所有--</option>
				<option value="0" ${param.status=='0'?'selected':'' }>待审批</option>
				<option value="1" ${param.status=='1'?'selected':'' }>审批通过</option>
				<option value="2" ${param.status=='2'?'selected':'' }>审批驳回</option>
				<option value="3" ${param.status=='3'?'selected':'' }>不允许交易</option>
			</select>
			</li>
			<li>
				<label>MCC：</label>
				<input type="text" name="MCC" value="${param.MCC }"/>
			</li>
		</ul>
		<ul  class="searchContent">
		    <li>
				<label>网址：</label>
				<input type="text" name="merWebSite" value="${param.merWebSite }"/>
			</li>
			<li>
				<label>英文账单：</label>
				<input type="text" name="merAcquirer" value="${param.merAcquirer }"/>
			</li>
			 <li   class="dateRange">
			  <label>申请时间</label>
			  <input type="text" name="createDateStart"  id = "createDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="createDateEnd"  id = "createDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.createDateEnd}"/>		
			</li>
			<li   class="dateRange">
			  <label>审批时间</label>
			  <input type="text" name="appDateStart"  id = "appDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.appDateStart}"/>
			  <span class="limit">-</span>
			  <input type="text" name="appDateEnd"  id = "appDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.appDateEnd}"/>		
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>网站语言：</label>
				<input type="text" name="merWebLanguage" value="${param.merWebLanguage }"/>
			</li>
			<li>
				<label>网站程序：</label>
				<input type="text" name="merWebProgram" value="${param.merWebProgram }"/>
			</li>
			<li>
				<label>品牌：</label>
				<input type="text" name="brand" value="${param.brand}"/>
			</li>
			<li>
				<label>产品：</label>
				<input type="text" name="product" value="${param.product}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li style="width: 450px;">
			<label style="width:110px;">批量上传网站查询：</label>
			<input type="text" id="websites" name="websites" style="width:250px;" value="${param.websites}" />
            <a class="btnLook" href="javascript:getWebsiteList()" >查找带回</a>
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="initialization()">重置</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/merchantmgr/goAddMerchantWebsite" target="dialog" width="450" height="500" mask="true" rel="merWebsit"><span>添加交易网址</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/goUploadMerchantWebsite" target="dialog" width="850" height="400" mask="true" rel="merWebsits"><span>批量上传交易网址</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goUpdateMerchantWebsite?id={sid_role}" width="450" height="500" target="dialog" title="审核交易网站" warn="请选择一条记录" mask="true" rel="relUpdateMerchantWebsite"><span>审核</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="javascript:void(0)" onclick="checkWebsite()" rel="relUpdateWebSite"><span>批量审核</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="javascript:void(0)" onclick="setWebsiteInfo()"><span>批量设置网站参数</span></a></li>
			<li class="line">line</li>
			<li><a class="edit" href="<%=path %>/merchantmgr/goUpdateMerchantWebsiteInfo?id={sid_role}" rel="relUpdateWebSite" width="450" height="500" target="dialog" title="查看信息" warn="请选择一条记录" mask="true"><span>查看</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" href="javascript:void(0)" onclick="deleteWebsite()"><span>批量删除</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/queryWebsiteLogList" target="navTab" width="1600" height="800" mask="true" rel="1111"><span>操作历史记录</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="<%=path %>/merchantmgr/exportWebsiteInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
			
			<c:if test="${!(empty errorWebsitStr) }">
					<li class="line">line</li>
					<li><a class="add" href="javascript:downloadErrorInfosForWebsite()" width="1250" height="400" mask="true" ><span>下载上传错误信息</span></a></li>
			</c:if>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab" layoutH="190" style="text-align: center;">
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAll" id="web_selectAll" onclick="web_selectAll()"></th>
				<th>商户号</th>
				<th>终端号</th>
				<th>商户类型</th>
				<th>品牌限制</th>
				<th>网址</th>
				<th>webSiteId</th>
				<th>英文账单</th>
				<th>账单-country</th>
				<th>账单-city</th>
				<th>账单-company</th>
				<th>网站程序</th>
				<th>网站程序语言</th>
				<th>MCC</th>
				<th>状态</th>
				<th>申请时间</th>
				<th>审批意见</th>
				<th>审批时间</th>
				<th>审批人</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="merWeb">
				<tr target="sid_role" rel="${merWeb.id }"    align="center">
					<td>
						<input type="checkbox" name="merWeb_ids" value="${merWeb.id}" id="ids">
					</td>
					<td>${merWeb.merNo }</td>
					<td>${merWeb.terNo }</td>
					<td>${merWeb.merType}</td>
					<td>${merWeb.brandStatus}</td>
					<td>${merWeb.merWebSite }</td>
					<td>${merWeb.webSiteId }</td>
					<td>${merWeb.merAcquirer }</td>
					<td>${merWeb.merAcquirerCountry}</td>
					<td>${merWeb.merAcquirerCity }</td>
					<td>${merWeb.merAcquirerCompany }</td>
					<td>${merWeb.merWebProgram }</td>
					<td>${merWeb.merWebLanguage }</td>
					<td>${merWeb.MCC }</td>
					<td>${funcs:getStringColumnKey('MERCHANTWEBSITESTATUS',merWeb.status,'未知状态')}</td>
					<td>${merWeb.createDate }</td>
					<td>${merWeb.message }</td>
					<td>${merWeb.appDate }</td>
					<td>${merWeb.appBy }</td>
					<td>${merWeb.remark }</td>
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