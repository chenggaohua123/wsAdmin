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
<script src="<%=path%>/dwz/js/esl.js"></script> 
<title>品牌交易统计</title>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$("#brandCountamg").click(function(){
		if($("#brandCountamg span").text()=="饼图"){
			$("#brandCountamg span").text("显示数据");
			show();
			$("#drawPic").show();
			$("#drawTable").hide();
		}else{
			$("#brandCountamg span").text("饼图");
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
	$("#brandSearchBar input,select").each(function(){
		if($(this).attr("name")!=undefined){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	$.ajax({
      type:"POST",
      data:"",
      url:"<%=path%>/countAnalysis/queryBrandCountInfoForPic"+param,
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
	if(10 <= dataLength){
		for(var i=0;i<10;i++){
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
		country[10] = "其它";
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
	    		        text: '品牌统计分析',
	    		        subtext: '品牌统计分析',
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
	    		            name:'品牌统计分析',
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
</script>
<body>
<div class="pageHeader">
<form id="pagerForm" method="post" action="<%=path %>/countAnalysis/queryBrandCountInfo">
	<input type="hidden" name="pageNum" value="${page.nowPage }" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
</form>
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="<%=path %>/countAnalysis/queryBrandCountInfo" method="post">
	<div class="searchBar" id="brandSearchBar">
		<ul class="searchContent">
			<li>
				<label>品牌：</label>
				<input type="text" name="brand" value="${param.brand}"/>
			</li>
			<li>
				<label>商户号：</label>
				<input type="text" name="merNo" value="${param.merNo}"/>
			</li>
			 <li  class="dateRange">
			  <label>交易日期</label>
			  <input type="text" name="transDateStart"  id = "transDateStart" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${form['transDateStart'] }"/>
		       <span class="limit">-</span>
			  <input type="text" name="transDateEnd"  id = "transDateEnd" readonly="readonly" dateFmt="yyyy-MM-dd"  class="date" value="${form['transDateEnd']}"/>		
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
		 		<label>国家：</label>
		 		<input name="countryNameCN" type="text"  id="currency.countryNameCN" value="${param.countryNameCN }" />
                <input name="cardCountry" type="hidden" id="currency.countryNameSimple" value="${param.cardCountry }"/>
                <a class="btnLook" href="<%=path %>/countAnalysis/getCountryListBrightBack" width="500" height="350"  rel="addCurrencyToMercha1n" mask="true" lookupGroup="currency"   lookupPk="countryNameSimple">查找带回</a><!-- -->
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
		<li><a class="add" href="<%=path %>/countAnalysis/exportbrandCountInfo" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><span>导出</span></a></li>
		<li><a class="add" id="brandCountamg" href="#"   width="550" height="300" mask="true"><span>饼图</span></a></li>
		</ul>
	</div>
	<div id="drawTable">
	<div id="w_list_print">
	<table class="list" width="100%" targetType="navTab"  layoutH="127" style="text-align:center;" >
		<thead>
			<tr>
				  <th>品牌</th>
                  <th>交易完成笔数</th>
                  <th>交易金额</th>
                  <th>成功笔数</th>
                  <th>成功金额</th>
                  <th>失败笔数</th>
                   <th>失败重复笔数</th>
                  <th>风险单笔数</th>
                  <th>拒付笔数</th>
                  <th>退单笔数</th>
                  <th >投诉笔数</th>
                   <th >伪冒笔数</th>
                  <th >成功率</th>
                  <th >拒付率</th>
                  <th >退单率</th>
                  <th >投诉率</th>
                  <th >伪冒率</th>
                  <th>占比</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${infos }" var="info">
				<tr >
					<td>${info.brand }</td>
					<td>${info.transCount }</td>
					<td>
						<c:forEach var="transAmountMap" items="${info.transAmountMap}">
							${transAmountMap.key} <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${transAmountMap.value  }"/><br>
						</c:forEach>
					</td>
					<td>${info.successCount }</td>
					<td>
						<c:forEach var="successAmountMap" items="${info.successAmountMap}">
							${successAmountMap.key} <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${successAmountMap.value  }"/><br>
						</c:forEach>
					</td>
					<td>${info.failCount }</td>
					<td>${info.dupCount }</td>
					<td>${info.riskCount }</td>
					<td>${info.disCount }</td>
					<td>${info.refundCount }</td>
					<td>${info.comCount }</td>
					<td>${info.fakeCount }</td>
					<td>
					<c:if test="${(info.transCount-riskCount-dupCount)==0 }">
						0.00%
					</c:if>
					<c:if test="${info.transCount!=0 }">
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.successCount*1.0/(info.transCount-riskCount-dupCount)*100 }"></fmt:formatNumber>%
					</c:if>
					</td>
					<td>
					<c:if test="${info.successCount==0 }">
						0.00%
					</c:if>
					<c:if test="${info.successCount!=0 }">
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.disCount*1.0/info.successCount*100 }"></fmt:formatNumber>%
					</c:if>
					</td>
					<td>
					<c:if test="${info.successCount==0 }">
						0.00%
					</c:if>
					<c:if test="${info.successCount!=0 }">
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.refundCount*1.0/info.successCount*100 }"></fmt:formatNumber>%
					</c:if>
					</td>
					<td>
					<c:if test="${info.successCount==0 }">
						0.00%
					</c:if>
					<c:if test="${info.successCount!=0 }">
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.comCount*1.0/info.successCount*100 }"></fmt:formatNumber>%
					</c:if>
					</td>
					<td>
					<c:if test="${info.successCount==0 }">
						0.00%
					</c:if>
					<c:if test="${info.successCount!=0 }">
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.fakeCount*1.0/info.successCount*100 }"></fmt:formatNumber>%
					</c:if>
					</td>
					<td>
					<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${info.transRate*100 }"/>%
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	<div id="drawPic" style="display: none;">
          <div id="main" style="height:400px;width:100%;"></div>
     </div>
</div>
</body>
</html>