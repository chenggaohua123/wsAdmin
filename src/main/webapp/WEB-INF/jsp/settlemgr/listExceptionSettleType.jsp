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
<title>结算方式管理</title>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		$("#listExceptionSettleType #selectAll").click(function(){
			 $('#listExceptionSettleType input:checkbox').each(function () {
				 var flag=false;
				 if($("#listExceptionSettleType #selectAll").attr("checked")){
					 flag=true;
				 }
				 $(this).attr("checked",flag);
			});
		});
		
		
	});
	function deleteExceptionSettleTypeInfo(){
		if(!confirm("是否删除")){
			return ;
		}
		var data=$("#del").serialize();
		 $.ajax({
	            type:"POST",
	            data:data,
	            url:"<%=path %>/settlemgr/deleteExceptionSettleTypeInfo",
	            dataType:"json",
	            success: function(json){
	                if(json.statusCode==200){
	                	alert(json.message);
	                	$("#reSearch #search").click();
		            }else{
		            	alert(json.message);
		            }
	            }
	        });
		
	}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/settlemgr/listExceptionSettleType">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader" id="reSearch">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/settlemgr/listExceptionSettleType" method="post">
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
				 <label>银行名称：</label>
               <input type="text" name="bankName" value="${param.bankName}"/>
			</li>
			</ul>
			<ul  class="searchContent">
			<li>
				<label>条件类型：</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=GATHERTYPE" relValue="columnKey" selectedValue="${param.gatherType }" relText="columnvalue" name="gatherType">
					<option value="">所有</option>
				</select>
			</li>
			<li>
			 <label>是否有效：</label>
              <select name="enabled" class="combox">
                <option value="" ${param.enabled==''?'selected':'' }>所有</option>
				<option value="1" ${param.enabled=='1'?'selected':'' }>有效</option>
				<option value="0" ${param.enabled=='0'?'selected':'' }>无效</option>
				</select>
			</li>
			
		</ul>
		<font color='red'>
			注：1.当选择拒付罚金时，有一个百分比条件的输入框。百分比收取方式按照商户号总拒付率计算收取。例如输入5%，当商户号中拒付率大于5%时，则该条件才生效。
			<br>　　2.当选择退款交易手续费时，有一个有效时间的输入框。例如输入24小时，商户提交退款申请的时间与交易时间相差不超过24小时的时候，此条件才生效。
			<br> 3.部分/全部收取，仅仅针对拒付罚金，例如设置拒付罚金 5%部分收取 那么超过多少订单，就收取多少条，如果全部收取那么就所有订单都收取
			<br> 4.阶梯收取，针对拒付罚金。《阶梯收取》例如设置0-5% 100,5%-10% 200,10%-∞ 300,假设拒付11%,那么0-5%的订单收取100,5%-10%的订单收取200,10%到∞的订单收取300.
			<br> 5.阶梯收取全部，针对拒付罚金.《阶梯收取全部》例如设置0-5% 100,5%-10% 200,10%-∞ 300,如果商户月拒付率刚好11%，应该是所有拒付都按照300一笔收取.
		</font>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="search">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<%=path %>/settlemgr/goAddExceptionSettleTypeInfo" target="dialog" width="550" height="400" mask="true"  rel="settleType" ><span>添加商户结算方式</span></a></li>
			<li><a class="edit" href="<%=path %>/settlemgr/goUpdateExceptionSettleTypeInfo?id={stId}" target="dialog" width="550" height="400" mask="true"  rel="settleType"  warn="请选择修改的数据"><span>修改商户结算方式</span></a></li>
			<li><a class="delete" href="javascript:deleteExceptionSettleTypeInfo()" id="deleteExceptionSettleTypeInfo"  width="1000" height="600" mask="true"  rel="settleType1" ><span>批量删除</span></a></li>
			<li>
			</li>
		</ul>
	</div>
	<div id="w_list_print">
	<form id="del">
	<table class="list" width="100%" targetType="navTab" layoutH="200" style="text-align: center;" id="listExceptionSettleType">	
		<thead>
			<tr>
				<th><input name="selectAll" id="selectAll" type="checkbox" > </th>
				<th>商户号</th>
				<th>终端号</th>
				<th>银行名称</th><th>卡种</th>
				<th>条件类型</th>
				<th>收取类型</th>
				<th>收取币种</th>
				<th>收取金额</th>
				<th>拒付罚金百分比条件</th>
				<th>部分/全部</th>
				<th>退款交易手续费有效时间</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>最后修改人</th>
				<th>最后修改时间</th>
			</tr>
		</thead>
		<tbody >
			<c:forEach items="${page.data }" var="st">
				<tr  target="stId" rel="${st.id }"   >
					<td><input name="ids" type="checkbox" value="${st.id }"> </td>
					<td>${st.merNo=='0'?'所有':st.merNo }</td>
					<td>${st.terNo=='0'?'所有':st.terNo }</td>
					<td>${st.bankId=='0'?'所有':st.bankName }</td>
					<td>${(empty st.cardType)?'所有':st.cardType }</td>
					<td>${funcs:getStringColumnKey('GATHERTYPE',st.gatherType,'未知状态')}</td>
					<td>${st.enabled==1?'有效':'无效'}</td>
					<td>${st.currency}</td>
					<td>${st.amount}</td>
					<td>
					<c:if test="${st.disRate>0.0 }">
						${st.disRate }%
					</c:if>
					<c:if test="${st.stepId>0 }">
						(${st.start1}% ~ ${st.end1==-1?'∞':st.end1}%] | ${st.stepAmount1 }<br/>
						<c:if test="${st.start2>0 }">
							(${st.start2}% ~ ${st.end2==-1?'∞':st.end2}%] | ${st.stepAmount2 }<br/>
						</c:if>
						<c:if test="${st.start3>0 }">
							(${st.start3}% ~ ${st.end3==-1?'∞':st.end3}%] | ${st.stepAmount3 }<br/>
						</c:if>
						<c:if test="${st.start4>0 }">
							(${st.start4}% ~ ${st.end4==-1?'∞':st.end4}%] | ${st.stepAmount4 }<br/>
						</c:if>
						<c:if test="${st.start5>0 }">
							(${st.start5}% ~ ${st.end5==-1?'∞':st.end5}%] | ${st.stepAmount5 }<br/>
						</c:if>
					</c:if>
					</td>
					<td>
					<c:choose>
						<c:when test="${st.gatherType==2 }">
						<c:choose>
							<c:when test="${st.isAllOrOver==2 }">
								阶梯收取
							</c:when>
							<c:when test="${st.isAllOrOver==3 }">
								按阶梯收取全部订单
							</c:when>
						</c:choose>
					<c:if test="${st.disRate>0.0 }">
						${st.isAllOrOver==0?'部分收取':'全部收取' }
					</c:if>
						</c:when>
						<c:when test="${st.gatherType==4 }">
							${st.refundReturn==0?'部分返还':'部分不返还' }
						</c:when>
					</c:choose>
					
					</td>
					<td>
					<c:if test="${st.refundHour>0 }">
						${st.refundHour }小时
					</c:if>
					</td>
					<td>${st.createBy}</td>
					<td>${st.createDate}</td>
					<td>${st.lastModifyBy}</td>
					<td>${st.lastModifyDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
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