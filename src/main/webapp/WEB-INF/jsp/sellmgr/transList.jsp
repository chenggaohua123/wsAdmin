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
<title>交易查询</title>
</head>
<body>
<style type="text/css">
span{
 font-weight:bold;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	  $("#btnx1").click(function(){
			$("#pageHeaderx1").slideToggle();
			/* var oldH=$("#tableList").attr("layoutH");
			alert(oldH);
			alert($("#btnx").is(":hidden"));
		  if($("#btnx").is(":hidden")){
			  $(".list").attr("layoutH",oldH+150);
		  }else{
			  $(".list").attr("layoutH",oldH-150);
		  }*/
	   });
	$("#btnx11").click(function(){
		  $("#pageHeader1").slideToggle();
	  });
});

</script>
<form id="pagerForm" method="post" action="<%=path %>/sellmgr/listTransInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="pageHeader1">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sellmgr/listTransInfo" method="post">
	<div class="searchBar" >
		<ul class="searchContent">
		 	<li>
		 		<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
		 	</li>
		 		<li>
		 		<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
		 	</li>
		 	<li>
		 		<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
		 	</li>
		 	
		 	
		 	
		 	
		</ul>
			<ul class="searchContent">
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo }"/>
			</li>
		   <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart']}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['transDateEnd']}"/>		
			</li>
			 <li>
		 		<label>交易网址：</label>
				<input type="text" name="payWebSite" value="${param.payWebSite}"/>
		 	</li>
		
			
		</ul>
		<ul class="searchContent">
		 	<li>
		 		<label>交易状态：</label>
				<select class="combox"  name="respCode" >
				    <option value="">请选择</option>
					<option value="00" <c:if test="${param.respCode=='00'}">selected</c:if>>成功</option>
					<option value="01" <c:if test="${param.respCode=='01'}">selected</c:if>>失败</option>
				</select>
		 	</li>
			
			<li>
			    <label>勾兑状态：</label>
				<select name="ischecked"  class="combox">
				   <option value="">请选择</option>
				   <option value="0" <c:if test="${param.ischecked=='0'}">selected</c:if>>未勾兑</option>
				   <option value="1" <c:if test="${param.ischecked=='1'}">selected</c:if>>已勾兑</option>
				</select>
			</li>
			<li>
			    <label>入账状态：</label>
				<select name="access"  class="combox">
				   <option value="">请选择</option>
				   <option value="0" <c:if test="${param.access=='0'}">selected</c:if>>未入账</option>
				   <option value="1" <c:if test="${param.access=='1'}">selected</c:if>>已入账</option>
				</select>
			</li>
			
		</ul> 
	
		<ul class="searchContent">
		<li>
		 		<label>卡种：</label>
				<select class="combox"
								loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=CARDTYPE"
								relValue="columnKey" relText="columnvalue" name="cardType" selectedValue="${param.cardType }">
								<option value="">所有</option>
							</select>
		 	</li>
		
			
		 	
		 	
		 </ul>
		 <ul class="searchContent">
	
		 	<li>
		 		<label>邮箱：</label>
				<input name="email" type="text" value="${param.email}"/>
		 	</li>
		 	<li>
		 		<label>IP：</label>
				<input name="ipAddress" type="text" value="${param.ipAddress}"/>
		 	</li>
		 	<li>
		 		<label>电话：</label>
				<input name="grphoneNumber" type="text" value="${param.grphoneNumber}"/>
		 	</li>
			
		 	
		 	
		 </ul>
		 <ul class="searchContent">
			<li>
				<label>卡号前六：</label>
				<input type="text" name="cardStart" value="${param.cardStart}"/>
			</li>
			<li>
				<label>卡号后四：</label>
				<input type="text" name="cardEnd" value="${param.cardEnd}"/>
			</li>	
			<li>
				<label>姓名：</label>
				<input type="text" name="grPerName" value="${param.grPerName}"/>
			</li>
		
		 	
		 </ul>
		 
		 <ul class="searchContent">
		
		 	
		 	
		 	<li>
		 		<label>退款状态：</label>
				<select name="transRefund" class="combox">
					<option value="" ${param.transRefund==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transRefund=='0'}">selected</c:if>>未退款</option>
					<option value="1" <c:if test="${param.transRefund=='1'}">selected</c:if>>已退款</option>
					
				</select>
		 	</li>
		 	
		 	<li>
		 		<label>拒付状态：</label>
				<select name="transDishonor" class="combox">
					<option value="" ${param.transDishonor==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transDishonor=='0'}">selected</c:if>>未拒付</option>
					<option value="1" <c:if test="${param.transDishonor=='1'}">selected</c:if>>已拒付</option>
					
				</select>
		 	</li>
		 	
		 	<li>
		 		<label>冻结状态：</label>
				<select name="transFrozen" class="combox">
					<option value="" ${param.transFrozen==''?'selected':'' }>所有</option>
					<option value="0" <c:if test="${param.transFrozen=='0'}">selected</c:if>>未冻结</option>
					<option value="1" <c:if test="${param.transFrozen=='1'}">selected</c:if>>已冻结</option>
					
				</select>
		 	</li>
		 </ul>
		  <ul class="searchContent">
		
		 	
		 	
		 	<li>
		 		<label>风险单过滤：</label>
				<select name="riskFilter" class="combox">
					<option value='' <c:if test="${param.riskFilter==''}">selected</c:if>>过滤</option>
					<option value="1" <c:if test="${param.riskFilter=='1'}">selected</c:if>>不过滤</option>
					
				</select>
		 	</li>
		 	<li>
				<label>账单国家：</label>
				<input type="text" name="cardCountry" value="${param.cardCountry}"/>
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
<div class="pageContent" >
	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="add" id="btnx11" href="#"   width="550" height="300" mask="true"><span>隐藏/显示查询条件</span></a></li>
			<li><a class="add" id="btnx1" href="#"   width="550" height="300" mask="true"><span>隐藏/显示统计汇总</span></a></li>
		</ul>
	</div>
	<div class="pageHeader" id="pageHeaderx1" style="display: none;">
	<div class="searchBar" >
		<ul class="searchContent" >
			<li>
			<span>交易总笔数: ${transCount.countTrans}</span> 
			</li>
			<li>
			<span>交易成功笔数: ${transCount.successTrans}</span>
			</li>
			
						
		</ul>
	</div>
	
	
		<div class="searchBar" >
				<ul class="searchContent" >
				<li style="width: 1000px"><span>标签总金额: 	
					<c:forEach items="${transCount.labelMoney}" var="list">
					${list.currency}:${list.countMoney}<font color="red"> | </font>
					</c:forEach>
				</span> 
			</ul>
		</div>
		<div class="searchBar" >
				<ul class="searchContent" >
				<li style="width: 1000px"><span>标签成功金额: 	
					<c:forEach items="${transCount.labelMoney}" var="list">
					${list.currency}:${list.countSuccessMoney}<font color="red"> | </font>
					</c:forEach>
				</span> 
			</ul>
		</div>
		<div class="searchBar" >
		<ul class="searchContent" >
				<li style="width: 1000px">
				<span>结算成功金额: 
				
				<c:forEach items="${transCount.settleMoney}" var="list">
					${list.currency}:${list.countSuccessMoney}<font color="red"> | </font>
				</c:forEach>
				</span> 
			</li>
		</ul>
	</div>
	</div>
	<div id="w_list_print">
	<table class="list"  width="100%" targetType="navTab" layoutH="50" style="text-align: center;">
		<thead>
			<tr>
				<th>流水号</th>
				<th>订单号</th>
				<th>商户号</th>
				<th>商户交易金额</th>
				<th>商户结算金额</th>
				<th>交易状态</th>
				<th>返回原因</th>
				<th>交易时间</th>
				<th>卡种</th>
				<th>卡类型</th>
				<th>卡号</th>
				<th>IP</th>
				<th>邮箱</th>
				<th>是否勾兑</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="trans">
				<tr target="tradeNo" rel="${trans.tradeNo }" >
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/sellmgr/queryTransByTradeNo?tradeNo=${trans.tradeNo }"> ${trans.tradeNo }</a></td>
					<td>${trans.orderNo}</td>
					<td>${trans.merNo }</td>
					<td>${trans.merBusCurrency }  ${trans.merTransAmount }</td>
					<td>${trans.merSettleCurrency } ${trans.merSettleAmount }</td>
					<td >
					<c:if test="${trans.respCode=='00'}">
						<font color="green">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if><c:if test="${trans.respCode!='00'}">
						<font color="red">${funcs:getStringColumnKey('RESP_INFO',trans.respCode,trans.respCode)}</font>
					</c:if>
					</td>
					<td>${trans.respMsg }</td>
					<td>${funcs:formatDate(trans.transDate,'yyyy-MM-dd HH:mm:ss')} </td>
					<td>${trans.cardType}</td>
					<td>${trans.cbInfo.card_type}</td>
					<td>${trans.sixAndFourCardNo}</td>
					<td> ${trans.ipAddress}</td>
					<td> ${trans.email}</td>
					<td>
					<c:if test="${trans.ischecked==0 }">
						<font color="black">未勾兑</font>
					</c:if>
					<c:if test="${trans.ischecked==1 }">
						<font color="green">已勾兑</font>
					</c:if>
					<c:if test="${trans.ischecked==2 }">
						<font color="red">勾兑异常</font>
					</c:if>
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