<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易统计</title>
</head>
<script src="<%=path%>/dwz/js/esl.js"></script> 
<script type="text/javascript">
$(document).ready(function(){
	$("#amg").click(function(){
		if($("#amg span").text()=="曲线图"){
			$("#amg span").text("显示数据");
			show();
			$("#drawPic").show();
			$("#drawTable").hide();
		}else{
			$("#amg span").text("曲线图");
			$("#drawPic").hide();
			$("#drawTable").show();
		}
	});
});

//路径配置
require.config({
	//chart:指一个完整的图表(如一个基本表或组合图表，可能包括坐标轴、图例等)
  paths:{ 
      'echarts' : '<%=path%>/dwz/js/echarts',
      'echarts/chart/line' : '<%=path%>/dwz/js/echarts'
  }
});
function show(){
	var param='?';
	$("#transHourSearch input,select").each(function(){
		if($(this).attr("name")!=undefined){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	$.ajax({
        type:"POST",
        data:"",
        url:"<%=path%>/sellmgr/queryTransHourCountForPic"+param,
        dataType:"json",
      	success: function(json){
			showAmg(json);
      	}
	});
}

function showAmg(data){
	var dataTitle = "交易时间段分布";//交易时间段分布
	var dataX = new Array(24);//X轴
	var dateCurrency=data[0].currency;
	var dataValue = new Array(24);//交易笔数
	var dataRate = new Array(24);//成功率
	var transAmount = new Array(24);//交易金额
	var transSuccessAmount = new Array(24);//成功金额
	var transSuccessCount = new Array(24);//成功笔数
	var transFailureCount = new Array(24);//失败笔数
	var transRiskCount = new Array(24);//失败笔数
		var dateCurrency_u=data[24].currency;
		var dataValue_u = new Array(24);//交易笔数
		var dataRate_u = new Array(24);//成功率
		var transAmount_u = new Array(24);//交易金额
		var transSuccessAmount_u = new Array(24);//成功金额
		var transSuccessCount_u = new Array(24);//成功笔数
		var transFailureCount_u = new Array(24);//失败笔数
		var transRiskCount_u = new Array(24);//失败笔数
	for(var i = 0;i<24;i++){
		dataX[i] = data[i].time;//
		if(data[i].finishCount){
			dataValue[i] = data[i].finishCount;
		}else{
			dataValue[i] = 0;
		}
		if(data[i].finishCount-data[i].dupCount-data[i].riskCount){
			dataRate[i] = data[i].successCount/(data[i].finishCount-data[i].dupCount-data[i].riskCount);
		}else{
			dataRate[i] = "0.00";
		}
		if(data[i].totalAmount){
			transAmount[i] = data[i].totalAmount;
		}else{
			transAmount[i] = 0;
		}
		if(data[i].successAmount){
			transSuccessAmount[i] = data[i].successAmount;
		}else{
			transSuccessAmount[i] = 0;
		}
		if(data[i].successCount){
			transSuccessCount[i] = data[i].successCount;
		}else{
			transSuccessCount[i] = 0;
		}
		if(data[i].failCount){
			transFailureCount[i] = data[i].failCount;
		}else{
			transFailureCount[i] = 0;
		}
		if(data[i].riskCount){
			transRiskCount[i] = data[i].riskCount;
		}else{
			transRiskCount[i] = 0;
		}
	}
	for(i = 24;i<data.length;i++){
		if(data[i].finishCount){
			dataValue_u[i-24] = data[i].finishCount;
		}else{
			dataValue_u[i-24] = 0;
		}
		if(data[i].finishCount-data[i].dupCount-data[i].riskCount){
			dataRate_u[i-24] = data[i].successCount/(data[i].finishCount-data[i].dupCount-data[i].riskCount);
		}else{
			dataRate_u[i-24] = "0.00";
		}
		if(data[i].totalAmount){
			transAmount_u[i-24] = data[i].totalAmount;
		}else{
			transAmount_u[i-24] = 0;
		}
		if(data[i].successAmount){
			transSuccessAmount_u[i-24] = data[i].successAmount;
		}else{
			transSuccessAmount_u[i-24] = 0;
		}
		if(data[i].successCount){
			transSuccessCount_u[i-24] = data[i].successCount;
		}else{
			transSuccessCount_u[i-24] = 0;
		}
		if(data[i].failCount){
			transFailureCount_u[i-24] = data[i].failCount;
		}else{
			transFailureCount_u[i-24] = 0;
		}
		if(data[i].riskCount){
			transRiskCount_u[i-24] = data[i].riskCount;
		}else{
			transRiskCount_u[i-24] = 0;
		}
	}
	//入口使用
	require(
	    [
	        'echarts',
	        'echarts/chart/line' // 使用折线图就加载pie模块
	    ],
	    function(ec) {
	        // 基于准备好的dom，初始化echarts图表
	        var myChart = ec.init(document.getElementById('main')); 
	        //图表选项
			var option = {
				title : {
					text: dataTitle,
					subtext: '交易笔数统计'//交易笔数统计
				},
				tooltip : {
					////显示当前数据点x、y轴信息和数值信息。
					trigger: 'axis'
				},
				legend: {//成功笔数,失败笔数
					data:['CNY总笔数','CNY成功笔数','CNY总金额','CNY成功金额','USD总笔数','USD成功笔数','USD总金额','USD成功金额']
				},
				toolbox: {
					show : true,
					feature : {
						mark : {show: true},
						dataView : {show: true, readOnly: false},
						magicType : {show: true, type: ['line', 'bar']},
						restore : {show: true},
						saveAsImage : {show: true}
					}
				},
				//网格
				grid : {
					width : 450,
					height : 200,
					backgroundColor :'#ffffcc'
				},
				calculable : true,
				xAxis : [
					{
						type : 'category',
						boundaryGap : false,
						data : dataX
					}
				],
				yAxis : [
					{
						type : 'value',
						axisLabel : {
							formatter: '{value}'
						},
						splitArea : {show : true}
					}
				],
				series : [
							{
								name:"CNY总笔数",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: dataValue,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '交易笔数'}
									]
								}
							},{
								name:"CNY成功笔数",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: transSuccessCount,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '成功笔数'}
									]
								}
							},{
								name:"CNY总金额",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: transAmount,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '交易金额'}
									]
								}
							},{
								name:"CNY成功金额",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: transSuccessAmount,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '成功金额'}
									]
								}
							},{
								name:"USD总笔数",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: dataValue_u,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '交易笔数'}
									]
								}
							},{
								name:"USD成功笔数",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: transSuccessCount_u,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '成功笔数'}
									]
								}
							},{
								name:"USD总金额",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: transAmount_u,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '交易金额'}
									]
								}
							},{
								name:"USD成功金额",
								type:'line',
								itemStyle: {
									//对象normal/emphasis
									normal: {
										//线条样式
										lineStyle: {
											//折线主线(IE8+)有效，阴影色彩
											shadowColor : 'rgba(0,0,0,0.4)',
											//折线主线(IE8+)有效，阴影模糊度
											shadowBlur: 10,
											//折线主线(IE8+)有效，阴影横向偏移，正值往右，负值往左
											shadowOffsetX: 3,
											//折线主线(IE8+)有效，阴影纵向偏移，正值往下，负值往上
											shadowOffsetY: 3
										}
									}
								},
								data: transSuccessAmount_u,
								markPoint : {
									data : [//成交笔数
										{type : 'max', name: '成功金额'}
									]
								}
							}
						]
			};
	        
	        // 为echarts对象加载数据 
	        myChart.setOption(option); 
	    }
	);
}
</script>
<body>
<div class="pageHeader">
<form id="pagerForm" method="post" action="<%=path %>/sellmgr/queryTransHourCount">
</form>
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/sellmgr/queryTransHourCount" method="post">
	<div class="searchBar" id="transHourSearch">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			<li>
				<label>终端号：</label>
				<input type="text" name="terNo" value="${param.terNo}"/>
			</li>
			 <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['startDate'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['endDate']}"/>		
			</li>
		</ul>
		<ul  class="searchContent">
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
				</select>
			</li>
			<li>
			<label>币种</label>
				<select class="combox" name="currency">
					<option value='' ${param.currency==''?'selected':'' }>所有</option>
					<option value='CNY' ${param.currency=='CNY'?'selected':'' }>CNY</option>
					<option value='USD' ${param.currency=='USD'?'selected':'' }>USD</option>					
				</select>
			</li>
		</ul>
		<ul>
		<li><font color="red">成功率=成功笔数/（交易完成笔数-风险单笔数-失败重复笔数）*100%</font>
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
		<li><a class="add" id="amg" href="#"   width="550" height="300" mask="true"><span>曲线图</span></a></li>
		</ul>
	</div>
	<div id="drawTable">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="150" style="text-align:center;" >
		<thead>
			<tr>
				  <th>交易时间段</th>
                  <th>交易完成笔数</th>
                  <th>交易金额</th>
                  <th>成功笔数</th>
                  <th>成功金额</th>
                   <th>失败重复笔数</th>
                  <th>失败笔数</th>
                  <th>风险单笔数</th>
                  <th >成功率</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page }" var="info">
				<tr >
					<td>${info.time }</td>
					<td>
					<c:choose>
						<c:when test="${empty info.finishCount}">
						0
						</c:when>
						<c:otherwise>
								${info.finishCount }
						</c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${empty info.totalAmount}">
						0.00
						</c:when>
						<c:otherwise>
								${info.currency} ${info.totalAmount }
						</c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${empty info.successCount}">
						0
						</c:when>
						<c:otherwise>
								${info.successCount }
						</c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${empty info.successAmount}">
						0.00
						</c:when>
						<c:otherwise>
								${info.currency} ${info.successAmount }
						</c:otherwise>
					</c:choose>
				</td>
					<td>
					<c:choose>
						<c:when test="${empty info.dupCount}">
						0
						</c:when>
						<c:otherwise>
							${info.dupCount }
						</c:otherwise>
					</c:choose>
					</td>
					<td>
					<c:choose>
						<c:when test="${empty info.failCount}">
						0
						</c:when>
						<c:otherwise>
							${info.failCount }
						</c:otherwise>
					</c:choose>
					
					</td>
					<td>
					<c:choose>
						<c:when test="${empty info.riskCount}">
						0
						</c:when>
						<c:otherwise>
							${info.riskCount }
						</c:otherwise>
					</c:choose>
					
					</td>
					<td>
					<c:choose>
						<c:when test="${empty info.finishCount || info.finishCount-dupCount-riskCount==0 }">
						0.00%
						</c:when>
						<c:otherwise>
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${(info.successCount)/(info.finishCount-dupCount-riskCount)*100 }"></fmt:formatNumber>%
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="panelBar" >
		<div class="pages">
	</div>
	</div>
</div>
	<div id="drawPic" style="display: none;">
          <div id="main" style="height:400px;width:100%;"></div>
     </div>
     </div>
</body>
</html>