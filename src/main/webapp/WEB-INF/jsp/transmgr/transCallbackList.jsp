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
<title>交易回访查询</title>
</head>
<script type="text/javascript">
</script>
<form id="pagerForm" method="post" action="<%=path %>/transmgr/queryTransCallback">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/transmgr/queryTransCallback" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 	<li>
		 		<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
		 	</li>
		 	
		 	<li>
		 		<label>持卡人邮箱：</label>
				<input name="email" type="text" value="${param.email}"/>
		 	</li>
		</ul>
		
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			
		   <li >
			  <label>上传日期</label>
			  <input type="text" name="uploadDateStart"  id = "uploadDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['uploadDateStart']}" />
			</li>
			
			<li>
		       <span class="limit">-</span>
			  <input type="text" name="uploadDateEnd"  id = "uploadDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['uploadDateEnd']}"/>		
			
			</li>
		
		</ul>
		
		 <ul class="searchContent">
		 	<li>
		 		<label>邮件模板：</label>
				<select name="emailModel" class="combox">
					<option value=""  ${param.emailModel==''?'selected':'' }>所有</option>
					<option value="1" ${param.emailModel=='1'?'selected':'' }>虚拟商户持卡人回访模板</option>
					<option value="2" ${param.emailModel=='2'?'selected':'' }>正品商户持卡人回访模板</option>
					<option value="3" ${param.emailModel=='3'?'selected':'' }>拒付订单持卡人回访模板</option>
					<option value="4" ${param.emailModel=='4'?'selected':'' }>日语订单持卡人回访模板</option>
					<option value="6" ${param.emailModel=='6'?'selected':'' }>社交网站持卡人回访模板</option>
				</select>
		 	</li>
		 	
		 	<li>
		 		<label>发送邮箱：</label>
				<select name="sendEmail" class="combox">
					<option value="" ${param.sendEmail==''?'selected':'' }>所有</option>
					<option value="auto" ${param.sendEmail=='auto'?'selected':'' }>auto邮箱</option>
					<option value="auto1" ${param.sendEmail=='auto1'?'selected':'' }>auto1邮箱</option>
					<option value="auto2" ${param.sendEmail=='auto2'?'selected':'' }>auto2邮箱</option>
					<option value="auto3" ${param.sendEmail=='auto3'?'selected':'' }>auto3邮箱</option>
					<option value="auto4" ${param.sendEmail=='auto4'?'selected':'' }>auto4邮箱</option>
					<option value="auto5" ${param.sendEmail=='auto5'?'selected':'' }>auto5邮箱</option>
				</select>
		 	</li>
		 	
		 	<li>
		 		<label>发送状态：</label>
				<select name="sendStatus" class="combox">
					<option value="" ${param.sendStatus==''?'selected':'' }>所有</option>
					<option value="0" ${param.sendStatus=='0'?'selected':'' }>未发送</option>
					<option value="1" ${param.sendStatus=='1'?'selected':'' }>发送失败</option>
					<option value="2" ${param.sendStatus=='2'?'selected':'' }>发送成功</option>
				</select>
		 	</li>
		 	
		 </ul>
		 
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/transmgr/toSelectSendInfo" target="dialog" targetType="navTab"rel="addCallback" width="1000" height="600" mask="true"><span>批量上传回访信息</span></a></li>
			<li><a class="add" href="<%=path %>/transmgr/exportTransCallbackInfoList" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
	<table class="list" id="tableList"   width="100%" targetType="navTab" layoutH="170" style="text-align: center;">
		<thead>
			<tr>
				<th>商户号</th>
				<th>流水号</th>
				<th>订单号</th>
				<th>交易网址</th>
				<th>交易时间</th>
				<th>持卡人邮箱</th>
				<th>邮件类型</th>
				<th>发送邮箱</th>
				<th>上传时间</th>
				<th>上传人</th>
				<th>发送状态</th>
				<th>邮件发送时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.id }" >
					<td>${trans.merNo }</td>
					<td>${trans.tradeNo}</td>
					<td>${trans.orderNo }</td>
					<td>${trans.payWebSite }</td>
					<td >
						${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} 
					</td>
					<td>
						${trans.email }
					</td>
					<td>
						${trans.emailModel=='1'?'虚拟商户持卡人回访模板':(trans.emailModel=='2'?'正品商户持卡人回访模板':(trans.emailModel=='3'?'拒付订单持卡人回访模板':(trans.emailModel=='4'?'日语订单持卡人回访模板':(trans.emailModel=='5'?'221888订单持卡人回访模板':(trans.emailModel=='6'?'社交网站持卡人回访模板':''))))) }
					</td>
					<td>${trans.sendEmail }</td>
					<td>
						${funcs:formatDate(trans.uploadDate,'yyyy-MM-dd HH:mm:ss') }
					</td>
					<td>${trans.uploadBy }</td>
					<td>${trans.sendStatus=='0'?'未发送':(trans.sendStatus=='1'?'发送失败':(trans.sendStatus=='2'?'发送成功':'未发送'))}</td>
					<td>
						${funcs:formatDate(trans.sendTime,'yyyy-MM-dd HH:mm:ss') }
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" style="height:30px">
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