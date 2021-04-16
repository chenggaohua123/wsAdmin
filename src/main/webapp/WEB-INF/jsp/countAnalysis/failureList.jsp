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
<title>失败订单分析</title>
</head>
<script src="<%=path%>/dwz/js/esl.js"></script> 
<script type="text/javascript">
$(document).ready(function(){
	$("#amg").click(function(){
		if($("#amg span").text()=="失败订单分析饼图"){
			$("#amg span").text("显示数据");
			show();
			$("#drawPic").show();
			$("#drawTable").hide();
		}else{
			$("#amg span").text("失败订单分析饼图");
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
	$(".searchBar input,select").each(function(){
		if($(this).attr("name")!=undefined){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	$.ajax({
      type:"POST",
      data:"",
      url:"<%=path%>/countAnalysis/failureListForPic"+param,
      dataType:"json",
    	success: function(param){
			showAmg(param.aaData);
    	}
	});
}

function showAmg(data){
	
	var dataLength = data.length;//数据量
	var country = []; //原因
	var countryData = [];//原因数据
	var countNum = 0;//前5个原因统计
	var countTotolNum = 0;//所有原因统计
	if(5 <= dataLength){
		for(var i=0;i<5;i++){
			country[i] = data[i][0];
			var obj = new Object();
			obj.value=data[i][1];
			obj.name=data[i][0];
			countryData[i] = obj;
			countNum = parseInt(countNum) + parseInt(data[i][1]);
		}
		for(var i=0;i<dataLength;i++){
			countTotolNum = parseInt(countTotolNum) + parseInt(data[i][1]);
		}
		country[5] = "其它";
		var obj = new Object();
		obj.value=parseInt(countTotolNum)-parseInt(countNum);
		obj.name="其它";
		countryData[5] = obj;
	}else{
		for(var i=0;i<dataLength;i++){
			country[i] = data[i][0];
			var obj = new Object();
			obj.value=data[i][1];
			obj.name=data[i][0];
			countryData[i] = obj;
			countNum = countNum + data[i][1];	
		}
	}
	
	//入口使用
	require(
	    [
	        'echarts',
	        'echarts/chart/pie' // 使用折线图就加载pie模块
	    ],function(ec) {
	    	var myChart = ec.init(document.getElementById('main')); 
	    	option = {
	    		    title : {
	    		        text: '失败订单分析',
	    		        subtext: '失败订单分析',
	    		        x:'center'
	    		    },
	    		    tooltip : {
	    		        trigger: 'item',
	    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    		    },
	    		    legend: {
	    		        orient : 'vertical',
	    		        x : 'left',
	    		        data: country
	    		    },
	    		    toolbox: {
	    		        show : true,
	    		        feature : {
	    		            mark : {show: true},
	    		            dataView : {show: true, readOnly: false},
	    		            magicType : {
	    		                show: true, 
	    		                type: ['pie', 'funnel'],
	    		                option: {
	    		                    funnel: {
	    		                        x: '25%',
	    		                        width: '50%',
	    		                        funnelAlign: 'left',
	    		                        max: countTotolNum
	    		                    }
	    		                }
	    		            },
	    		            restore : {show: true},
	    		            saveAsImage : {show: true}
	    		        }
	    		    },
	    		    calculable : true,
	    		    series : [
	    		        {
	    		            name:'失败订单分析',
	    		            type:'pie',
	    		            radius : '55%',
	    		            center: ['50%', '60%'],
	    		            data: countryData
	    		        }
	    		    ]
	    		};
	    	myChart.setOption(option); 
	    });
}
function exportInfos(respMsg){
	var param='?';
	$("#FailureListsearchBar input,select").each(function(){
		if($(this).attr("name")!=undefined){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	window.location.href="<%=path%>/countAnalysis/exportFailTransInfo"+param+"respMsg="+respMsg;
}
</script>
<body>
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/failureList">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/failureList" method="post">
	<div class="searchBar" id="FailureListsearchBar">
		<ul class="searchContent">
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo }">
			</li>
			 <li  >
			  <label>交易日期</label>
			  <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss" class="date" value="${form['startDate'] }"/>
		       </li>
		       
		       <li  > <span class="limit">-</span>
			  <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd HH:mm:ss"  class="date" value="${form['endDate']}"/>		
			</li>
		</ul>
		<ul class="searchContent">
		<li>
				<label>网站：</label>
				<input type="text" name="merWebsite" value="${param.merWebsite }">
			</li>
			
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
		</ul>
		<ul  class="searchContent">
			<li>
				<label>商户类别</label>
				<select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=MERCHANTTYPE" relValue="columnKey" selectedValue="${param.type }" relText="columnvalue" name="type">
					<option value="">所有</option>
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
		<li><a class="add" id="amg" href="#"   width="550" height="300" mask="true"><span>失败订单分析饼图</span></a></li>
		</ul>
	</div>
	<div id="drawTable">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="165" style="text-align:center;" >
		<thead>
			<tr>
				  <th>失败原因</th>
                  <th>失败笔数</th>
                  <th>占比</th>
                  <th>导出 </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.data }" var="info">
				<tr >
					<td>${info.respMsg }</td>
					<td>${info.countRespMsg }</td>
					<td><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.respMsgRate*100 }"></fmt:formatNumber>%</td>
					<td><a href="javascript:exportInfos(&quot;${info.respMsg }&quot;)">导出</a></td>
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
	<div id="drawPic" style="display: none;">
          <div id="main" style="height:400px;width:100%;"></div>
     </div>
</div>
</body>
</html>