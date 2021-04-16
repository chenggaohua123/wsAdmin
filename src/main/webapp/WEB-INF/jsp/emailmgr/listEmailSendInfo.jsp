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
<title>邮件发送列表</title>
</head>
<body>
<form id="pagerForm" method="post" action="<%=path %>/emailmgr/queryEmailSendInfoList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/emailmgr/queryEmailSendInfoList" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
			<li  class="dateRange">
			  <label>发送时间</label>
			  <input type="text" name="sendStart"  id = "sendStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['sendStart'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="sendEnd"  id = "sendEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['sendEnd']}"/>		
			</li>
			<li  class="dateRange">
			  <label>交易时间</label>
			  <input type="text" name="transStart"  id = "transStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transStart'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="transEnd"  id = "transEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['transEnd']}"/>		
			</li>
		</ul>
		
		<div style="color: red;">
		注:
			<ol>
				<li>1.未发送:表示邮件没有发送</li>
				<li>2.已发送:表示邮件已经发送</li>
				<li>3.未激活:表示没有要发送对应类型邮件</li>
				<li>4.不发送:商户设置邮件为不发送状态</li>
			</ol>
		</div>
		
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
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="175" style="text-align:center;" >
		<thead>
			<tr>
				<th style="border-bottom: 0px;"></th>
				<th colspan="5">发送邮件给持卡人</th>
				<th colspan="4">发送邮件给商户</th>
			</tr>
			<tr>
				<th>交易流水号</th>
				<th>支付成功</th>
				<th>支付失败</th>
				<th>运单上传成功</th>
				<th>退款成功</th>
				<th>支付待处理</th>
				<th>支付成功</th>
				<th>添加拒付订单</th>
				<th>新建投诉订单</th>
				<th>调查单</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="id" rel="${info.tradeNo }">
					<td>${info.tradeNo }</td>
					<td title="${ info.successToCardEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.successToCardIds }&sendTypeId=${info.successToCardType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.successToCard==0?'red':(info.successToCard<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.successToCard==0}">
								未发送
							</c:when>
							<c:when test="${info.successToCard==1}">
								已发送
							</c:when>
							<c:when test="${info.successToCard==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a> 
						<c:if test="${info.successToCard>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=1">详情</a>
						</c:if>
					</td>
					<td title="${ info.faildTocardEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.faildTocardIds }&sendTypeId=${info.faildTocardType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.faildTocard==0?'red':(info.faildTocard<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.faildTocard==0}">
								未发送
							</c:when>
							<c:when test="${info.faildTocard==1}">
								已发送
							</c:when>
							<c:when test="${info.faildTocard==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.faildTocard>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=3">详情</a>
						</c:if>
					</td>
					<td  title="${ info.divToCardEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.divToCardIds }&sendTypeId=${info.divToCardType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.divToCard==0?'red':(info.divToCard<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.divToCard==0}">
								未发送
							</c:when>
							<c:when test="${info.divToCard==1}">
								已发送
							</c:when>
							<c:when test="${info.divToCard==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.divToCard>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=4">详情</a>
						</c:if>
					</td>
					<td  title="${ info.refundToCardEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.refundToCardIds }&sendTypeId=${info.refundToCardType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.refundToCard==0?'red':(info.refundToCard<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.refundToCard==0}">
								未发送
							</c:when>
							<c:when test="${info.refundToCard==1}">
								已发送
							</c:when>
							<c:when test="${info.refundToCard==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.refundToCard>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=5">详情</a>
						</c:if>
					</td>
					<td title="${ info.payPendToCardEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.payPendToCardIds }&sendTypeId=${info.payPendToCardType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.payPendToCard==0?'red':(info.payPendToCard<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.payPendToCard==0}">
								未发送
							</c:when>
							<c:when test="${info.payPendToCard==1}">
								已发送
							</c:when>
							<c:when test="${info.payPendToCard==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.payPendToCard>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=11">详情</a>
						</c:if>
					</td>
					<td title="${ info.successToMerEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.successToMerIds }&sendTypeId=${info.successToMerType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.successToMer==0?'red':(info.successToMer<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.successToMer==0}">
								未发送
							</c:when>
							<c:when test="${info.successToMer==1}">
								已发送
							</c:when>
							<c:when test="${info.successToMer==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.successToMer>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=2">详情</a>
						</c:if>
					</td>
					<td title="${ info.refuseToMerEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.refuseToMerIds }&sendTypeId=${info.refuseToMerType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.refuseToMer==0?'red':(info.refuseToMer<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.refuseToMer==0}">
								未发送
							</c:when>
							<c:when test="${info.refuseToMer==1}">
								已发送
							</c:when>
							<c:when test="${info.refuseToMer==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.refuseToMer>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=6">详情</a>
						</c:if>
					</td>
					<td title="${ info.addComToMerEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.addComToMerIds }&sendTypeId=${info.addComToMerType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.addComToMer==0?'red':(info.addComToMer<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.addComToMer==0}">
								未发送
							</c:when>
							<c:when test="${info.addComToMer==1}">
								已发送
							</c:when>
							<c:when test="${info.addComToMer==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.addComToMer>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=7">详情</a>
						</c:if>
					</td>
					<td title="${ info.surveyToMerchantEmail }">
						<a class="edit" href="<%=path %>/emailmgr/updateEmailSendStatus?ids=${info.surveyToMerchantIds }&sendTypeId=${info.surveyToMerchantType}&tradeNo=${info.tradeNo }" target="ajaxTodo" title="确定需要重新发送吗?" rel="edit">
						<span>
						<font color="${info.surveyToMerchant==0?'red':(info.surveyToMerchant<0?'blue':'green') }">
						<c:choose>
							<c:when test="${info.surveyToMerchant==0}">
								未发送
							</c:when>
							<c:when test="${info.surveyToMerchant==1}">
								已发送
							</c:when>
							<c:when test="${info.surveyToMerchant==2}">
								不发送
							</c:when>
							<c:otherwise>未激活</c:otherwise>
						</c:choose>
						</font>
						</span>
						</a>
						<c:if test="${info.surveyToMerchant>=0 }">
						&nbsp;|&nbsp;
						<a target="_black" href="<%=path %>/emailmgr/showEmailDesc?tradeNo=${info.tradeNo}&type=12">详情</a>
						</c:if>
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