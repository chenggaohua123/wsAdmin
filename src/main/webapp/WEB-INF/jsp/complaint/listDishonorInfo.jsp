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
<title>拒付信息列表</title>
</head>
<script type="text/javascript">
	function selectAllDishonor(){  
		if ($("#selectAllDishonor").attr("checked")) {  
	    	$("input[name='lsitDishonorInfo_ids']").each(function(){
		        $(this).attr("checked", true);  
	    	});
	    } else {  
	    	$("input[name='lsitDishonorInfo_ids']").each(function(){
		        $(this).attr("checked", false);  
	    	});
	    }  
	}  

	function toUpdateComplaintInfo(status){
		var str=""; 
		 $("input[name='lsitDishonorInfo_ids']:checked").each(function(){ 
			str+="ids="+$(this).val()+"&"; 
		});
		if(!str){
			alert("请选处理核数据");
		}else{
			var content="";
			if("2"==status){
				content="确定要修改为申诉成功?";
			}else if("3"==status){
				content="确定要修改为申诉失败?";
			}else if("4"==status){
				content="确定要修改为拒付?";
			}
			if(confirm(content)){
				var url="<%=path %>"+"/complaint/updateDishonorInfo?"+str+"status="+status;//根据状态不通修改不通状态
				$.ajax({
					url:url,
					type:'post',
					dataType:'json',
					data:'text',
					success:function(data){
						alert(data.message);
						$("#fromInfo").submit();
					}
				});
			}
			
		}
	}
	function downDisFile(){
		window.location.href="<%=path%>/complaint/downloadDisFile";
	}

</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/complaint/listDishonorInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" id="fromInfo" action="<%=path %>/complaint/listDishonorInfo" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>流水号：</label>
				<input type="text" name="tradeNo" value="${param.tradeNo}"/>
			</li>
			<li>
				<label>订单号：</label>
				<input type="text" name="orderNo" value="${param.orderNo}"/>
			</li>
		</ul>
		<ul class="searchContent">
			<li  class="dateRange">
			  <label>交易日期:</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.transDateEnd}"/>		
			</li>
			<li  class="dateRange">
			  <label>拒付通知时间:</label>
			  <input type="text" name="complaintDateStart"  id = "complaintDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.complaintDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="complaintDateEnd"  id = "complaintDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.complaintDateEnd}"/>		
			</li>
			<li>
				<label>拒付处理状态：</label>
				<select name="status" class="combox">
					<option value="" <c:if test="${param.status==''}">selected</c:if>>--所有--</option>
					<option value="0" <c:if test="${param.status=='0'}">selected</c:if>>拒付通知</option>
					<option value="1" <c:if test="${param.status=='1'}">selected</c:if>>申诉中</option>
					<option value="2" <c:if test="${param.status=='2'}">selected</c:if>>申诉成功</option>
					<option value="3" <c:if test="${param.status=='3'}">selected</c:if>>申诉失败</option>
					<option value="4" <c:if test="${param.status=='4'}">selected</c:if>>已拒付</option>
				</select>
			</li>
		</ul>
		<ul class="searchContent">
			<li>
		 		<label>银行：</label>
				 <select class="combox" selectedValue="${param.bankId }"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
		 	</li>
		 	<li>
		 		<label>通道：</label>
		 		<input name="currencyName" type="text"  id="currency.currencyName" value="${param.currencyName }" />
                <input name="currencyId" type="hidden" id="currency.currencyId" value="${param.currencyId }"/>
                <a class="btnLook" href="<%=path %>/bankMgr/getCurrencyListBrightBack" width="500" height="350"  rel="addCurrencyToMerchan" mask="true" lookupGroup="currency" lookupPk="bankId">查找带回</a>
		 	</li>
		
			<li>
				<label>处理人：</label>
				<input name="lastUpdateBy" value="${param.lastUpdateBy}">
			</li>
			
		</ul>
			<ul class="searchContent">
			<li>
				<label>网站：</label>
				<input type="text" name="payWebSite" value="${param.payWebSite}">
			</li>
			<li>
				<label>商户是否可见：</label>
				<select name="isMerchantSee" class="combox">
					<option value='' ${form['isMerchantSee']==''?'selected':''}>所有</option>
					<option value='0' ${form['isMerchantSee']=='0'?'selected':''}>可见</option>
					<option value='1' ${form['isMerchantSee']=='1'?'selected':''}>不可见</option>
				</select>
			</li>
			<li>
				<label>特殊拒付：</label>
				<select name="isSp" class="combox">
					<option value='' ${param.isSp==''?'selected':''}>所有</option>
					<option value='0' ${param.isSp=='0'?'selected':''}>不是</option>
					<option value='1' ${param.isSp=='1'?'selected':''}>是</option>
				</select>
			</li>
			
		</ul>
		
			<ul class="searchContent">
			<li>
				<label>已申诉：</label>
				<select name="isComp" class="combox">
					<option value='' ${param.isComp==''?'selected':''}>所有</option>
					<option value='0' ${param.isComp=='0'?'selected':''}>未申诉</option>
					<option value='1' ${param.isComp=='1'?'selected':''}>已申诉</option>
				</select>
			</li>
			<li>
				<label>拒付原因：</label>
				<input name="ctId" type="text"  id="cId" value="${param.ctId }" /><a class="btnLook"
								href="<%=path %>/complaint/complaintListBack?type=1" width="1100"
								height="350" rel="rateRelMerchant" mask="true" lookupGroup=""
								lookupPk="complaintTypeValue">查找带回</a>
			</li>
			<li  class="dateRange">
			  <label>CPD日期:</label>
			  <input type="text" name="CPDDateStart"  id = "CPDDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.CPDDateStart}"/>
		       <span class="limit">-</span>
			  <input type="text" name="CPDDateEnd"  id = "CPDDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${param.CPDDateEnd}"/>		
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
			<li>
				<label>卡种：</label>
				<select name="cardType" class="combox">
					<option value="" ${param.cardType==''?'selected':'' }>所有</option>
					<option value="visa" <c:if test="${param.cardType=='visa'}">selected</c:if>>VISA</option>
					<option value="master" <c:if test="${param.cardType=='master'}">selected</c:if>>Master</option>
					<option value="jcb" <c:if test="${param.cardType=='jcb'}">selected</c:if>>JCB</option>
				</select>
			</li>
			<li>
				<label>是否伪冒：</label>
				<select name="isFake" class="combox">
					<option value='' ${param.isFake==''?'selected':''}>所有</option>
					<option value='0' ${param.isFake=='0'?'selected':''}>伪冒</option>
					<option value='1' ${param.isFake=='1'?'selected':''}>未伪冒</option>
				</select>
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>创建人：</label>
				<input name="createdBy" value="${param.createdBy}">
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
			<li><a class="add" href="<%=path %>/complaint/goAddDishonorInfo" target="dialog" width="550" height="380" mask="true" rel="add"><span>添加拒付单</span></a></li>
			<li><a class="edit" href="javascript:downDisFile()" ><span>下载批量添加拒付模板</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/goBatchAddDishonorInfo" target="dialog" width="550" height="380" mask="true" rel="add"><span>批量添加拒付单</span></a></li>
			<li><a class="edit" href="#" onclick="toUpdateComplaintInfo(2)"><span>批量申诉成功</span></a></li>
			<li><a class="edit" href="#" onclick="toUpdateComplaintInfo(3)"><span>批量申诉失败</span></a></li>
			<li><a class="edit" href="#" onclick="toUpdateComplaintInfo(4)"><span>批量拒付</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/exportDishonorTransInfo" target="dwzExport" width="550" height="380" mask="true" rel="add"><span>导出列表数据</span></a></li>
			<li><a class="edit" href="<%=path %>/complaint/goUpdateDisCPDDate?id={id}" target="dialog" width="550" height="380" mask="true" rel="11111" warn="请选择要修改的数据"><span>修改CPD日期</span></a></li>
			<li><a class="add" href="<%=path %>/complaint/uploadCPDDateBatchFile" target="dialog" targetType="navTab"rel="addC22ur11rency1111" width="1000" height="600" mask="true"><span>批量修改CPD日期/特殊拒付/申诉</span></a></li>
			<li><a  class="edit" style="color:red" href="<%=path %>/complaint/updateDisOrderToIsComp?id={id}" target="ajaxTodo" title="确定要修改为已申诉?" mask="true"><span>已申诉</span></a></li>
		</ul>
	</div>

	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="265" style="text-align:center;" >
		<thead>
			<tr>
				<th><input type="checkbox" name="selectAll" id="selectAllDishonor" onclick="selectAllDishonor()"></th>
				<th>商户号</th>
				<th>流水号</th>
				<th>特殊拒付 </th>
				<th>商户可见</th>
				<th>是否申诉</th>
				<th>交易金额</th>
				<th>拒付金额</th>
				<th>拒付通知时间</th>
				<th>拒付原因</th>
				<th>处理截止日期</th>
				<th>CPD日期</th>
				<th>拒付处理状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>处理人</th>
				<th>处理时间</th>
				<th>申诉资料查看</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr target="id" rel="${info.id }">
					<td>
						<input type="checkbox" name="lsitDishonorInfo_ids" value="${info.id}" id="ids">
					</td>
					<td>${info.merNo }</td>
					<td><a target="dialog" title="交易明细" width="900" height="520" mask="true" href="<%=path %>/transmgr/queryTransByTradeNo?tradeNo=${info.tradeNo }"> ${info.tradeNo }</a></td>
					<td>${info.isSp==0?'否':'<font color="red">是</font>' }</td>
					<td>${info.isMerchantSee==0?'是':'<font color="red">否</font>' }</td>
					<td>${info.isComp==0?'未申诉':'<font color="red">已申诉</font>' }</td>
					<td>${info.merBusCurrency } ${info.merTransAmount }</td>
					<td>${info.merBusCurrency }&nbsp; 
					<c:choose>
						<c:when test="${info.amount == 0.00 }">
							${info.merTransAmount }
						</c:when>
						<c:otherwise>
							${info.amount }
						</c:otherwise>
					</c:choose>
					
					
					</td>
					<td>${info.complaintDate }</td>
					<td>${info.complaintTypeValue }</td>
					<td>${info.deadline }</td>
					<td title="${info.CPDUpdateBy } ${info.CPDUpdateDate}">${info.CPDDate }</td>
					<td>${funcs:getStringColumnKey('COMPLAINT_STATUS_1',info.status,'未知状态')}</td>
					<td>${info.createdBy}</td>
					<td>${info.createdDate }</td>
					<td>${info.lastUpdateBy }</td>
					<td>${info.lastUpdateDate }</td>
					<td>
						<c:if test="${info.filePath !=null && info.filePath!='' }">
							<%--<a href="<%=path %>/complaint/downloadFile?id=${info.id }" onclick="alert('${info.filePath}')" >下载申诉资料</a> --%>
							<a href="https://merchant.wantspay.com/dishonor/goToDownloadInfo?id=${info.id }" onclick="alert('${info.filePath}')" >下载申诉资料</a>
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