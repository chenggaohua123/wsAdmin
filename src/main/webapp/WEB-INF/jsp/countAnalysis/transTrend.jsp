<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=path %>/btp/css/bootstrap.min.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/uniform.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/select2.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/matrix-style.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/matrix-media.css" />
<link rel="stylesheet" href="<%=path %>/btp/font-awesome/css/font-awesome.css"  />
<link rel="stylesheet" href="<%=path %>/btp/css/colorpicker.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/datepicker.css" />
<link rel="stylesheet" href="<%=path %>/btp/css/bootstrap-wysihtml5.css" />
<script src="<%=path%>/btp/js/jquery.min.js"></script> 
<script src="<%=path%>/btp/js/jquery.ui.custom.js"></script> 
<script src="<%=path%>/btp/js/bootstrap.min.js"></script> 
<script src="<%=path%>/btp/js/jquery.uniform.js"></script> 
<script src="<%=path%>/btp/js/select2.min.js"></script> 
<script src="<%=path%>/btp/js/jquery.dataTables.min.js"></script> 
<script src="<%=path%>/btp/js/matrix.js"></script> 
<script src="<%=path%>/btp/js/bootstrap-colorpicker.js"></script> 
<script src="<%=path%>/btp/js/jquery.toggle.buttons.html"></script> 
<script src="<%=path%>/btp/js/wysihtml5-0.3.0.js"></script> 
<script src="<%=path%>/btp/js/jquery.peity.min.js"></script> 
<script src="<%=path%>/btp/js/bootstrap-wysihtml5.js"></script> 
<script src="<%=path%>/btp/js/bootstrap-datepicker.js"></script> 
<script src="<%=path%>/btp/js/DatePicker/WdatePicker.js"></script> 
<script src="<%=path%>/btp/js/ajaxfileupload.js"></script>
<script src="<%=path%>/btp/js/esl.js"></script>
<title>交易统计</title>
</head>
<script type="text/javascript">
$(document).ready(function(){
	$( "#d11" ).val( new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate());
	$( "#d12" ).val( new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate());
	
	var table = $('.data-table').dataTable({
		"bJQueryUI": true,
		"sAjaxSource":"<%=path%>/countAnalysis/transTrendForTable",
		"sPaginationType": "full_numbers",
		"sScrollY":document.body.clientHeight-180-50,
		"bServerSide": true,	
		"bProcessing":true,
		"bInfo":true,
		"bPaginate":false,
		"bFilter":false,
		"oLanguage" : { //主要用于设置各种提示文本
			"sProcessing" : "正在处理...", //设置进度条显示文本
			"sLengthMenu" : "每页_MENU_行",//显示每页多少条记录
			"sEmptyTable" : "没有找到记录",//没有记录时显示的文本
			"sZeroRecords" : "没有找到记录",//没有记录时显示的文本
			"sInfoEmpty" : "",//没记录时,关于记录数的显示文本
			"sSearch" : "搜索:",//搜索框前的文本设置
			"sInfo" : "",
			"oPaginate" : {
			"sFirst" : "首页",
			"sLast" : "未页",
			"sNext" : "下页",
			"sPrevious" : "上页"
			}
			},
		"aoColumnDefs": [ {
				"aTargets": [0],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					
					var strs=sData.lastIndexOf("&nbsp;");
					if(strs>18){
						$(nTd).html(sData.replace("&nbsp;","-").replace("&nbsp;","-"));
					}else{
						$(nTd).html(sData.replace("&nbsp;","-"));
					}
				}
			},{
				"aTargets": [1],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData==''){
						$(nTd).html("0");
					}
				}
			},{
				"aTargets": [2],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData=='&nbsp;&nbsp;'){
						$(nTd).html("CNY 0.00");
					}
				}
			},{
				"aTargets": [3],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData==''){
						$(nTd).html("0");
					}
				}
			},{
				"aTargets": [4],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData=='&nbsp;&nbsp;'){
						$(nTd).html("CNY 0.00");
					}
				}
			},{
				"aTargets": [5],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData==''){
						$(nTd).html("0");
					}
				}
			},{
				"aTargets": [6],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData==''){
						$(nTd).html("0");
					}
				}
			},{
				"aTargets": [7],
				"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
					if(sData==''){
						$(nTd).html("00.0%");
					}
				}
			}
			
			],
		"bSort":false,
		"bLengthChange":false,
		"sAjaxDataProp": "aaData",
		"fnServerParams":function(aoData){
			$("#selectForm input,select").each(function(){
				aoData.push({"name":$(this).attr("name"),"value":$(this).val()});
			});
		},
		"fnServerData": function ( sSource, aoData, fnCallback ) {
			 $.ajax( {
			 "dataType": 'json',
			 "type": "POST",
			 "url": sSource,
			 "data": aoData,
			 "success": fnCallback
			 } );},
		"fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
			$(nRow).click(function(){
				$(".row-selected").removeClass("row-selected");
				$(this).addClass("row-selected");
			});
		}
		  
	});
	
	$("#searchFrom").click(function(){
		var oSettings = table.fnSettings();
		 oSettings._iDisplayStart=0;
		 clickFrom();
		table.fnDraw();
	});
	clickFrom();
	
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	$("span.icon input:checkbox, th input:checkbox").click(function() {
		var checkedStatus = this.checked;
		var checkbox = $(this).parents('.widget-box').find('tr td:first-child input:checkbox');		
		checkbox.each(function() {
			this.checked = checkedStatus;
			if (checkedStatus == this.checked) {
				$(this).closest('.checker > span').removeClass('checked');
			}
			if (this.checked) {
				$(this).closest('.checker > span').addClass('checked');
				
			}
		});
	});	
	
	$("#show").click(function (){
		if($("#show").text()=='显示统计图'){
			$("#show").text("显示数据");
			$("#drawPic").show();
			$("#drawTable").hide();
		}else{
			$("#show").text("显示统计图");
			$("#drawPic").hide();
			$("#drawTable").show();
			table.fnDraw();
		}
	});
	
});
	//路径配置
	require.config({
		//chart:指一个完整的图表(如一个基本表或组合图表，可能包括坐标轴、图例等)
	    paths:{ 
	        'echarts' : '<%=path%>/btp/js/echarts',
	        'echarts/chart/line' : '<%=path%>/btp/js/echarts'
	    }
	});

function clickFrom(){
	var param = [];
	var url="<%=path%>/countAnalysis/transTrend?terNo="+$("#terNo").val()+"&year="+$("#year").val()+"&month="+$("#month").val();
	 $.ajax({
            type:"POST",
            data:param,
            url:url,
            dataType:"json",
            success: function(json){
                 showRequire(json);
            }, 
		  	error:function(){ 
		  		$("#alert #myModalLabel").text("");
		      	$("#alert .modal-body").text("数据异常");
		      	$('#alert').modal('show'); 
			}    
        });
}

function showRequire(data){
	showDiv();
	if(data[0].month){
		var dataTitle = data[0].year+ "年 "+ data[0].month + "月交易趋势统计";
		var dataName = "日交易统计"
	}else{
		var dataName = "月交易统计"
		var dataTitle = data[0].year+ "年交易趋势统计";
	}
	var dataX = new Array(data.length);//X轴
	var dataValue = new Array(data.length);//交易笔数
	var dataRate = new Array(data.length);//成功率
	var transAmount = new Array(data.length);//交易金额
	var transSuccessAmount = new Array(data.length);//成功金额
	var transSuccessCount = new Array(data.length);//成功笔数
	var transFailureCount = new Array(data.length);//失败笔数
	for(var i = 0;i<data.length;i++){
		if(12 == data.length){
			dataX[i] = (i+1) + "月";
		}else{
			dataX[i] = (i+1) + "日";
		}
		if(data[i].transCount){
			dataValue[i] = data[i].transCount;
		}else{
			dataValue[i] = 0;
		}
		if(data[i].successRate){
			var str = data[i].successRate.split("%");
			dataRate[i] = parseInt(str[0]);
		}else{
			dataRate[i] = 0;
		}
		if(data[i].transAmount){
			transAmount[i] = data[i].transAmount;
		}else{
			transAmount[i] = 0;
		}
		if(data[i].transSuccessAmount){
			transSuccessAmount[i] = data[i].transSuccessAmount;
		}else{
			transSuccessAmount[i] = 0;
		}
		if(data[i].transSuccessCount){
			transSuccessCount[i] = data[i].transSuccessCount;
		}else{
			transSuccessCount[i] = 0;
		}
		if(data[i].transFailureCount){
			transFailureCount[i] = data[i].transFailureCount;
		}else{
			transFailureCount[i] = 0;
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
	        var myChart1 = ec.init(document.getElementById('main1')); 
	        var myChart2 = ec.init(document.getElementById('main2')); 
	        var myChart3 = ec.init(document.getElementById('main3')); 
	        //图表选项
			var option = {
				title : {
					text: dataTitle,
					subtext: '交易笔数统计'
				},
				tooltip : {
					////显示当前数据点x、y轴信息和数值信息。
					trigger: 'axis'
				},
				legend: {
					data:[dataName,'成功笔数','失败笔数']
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
						name:dataName,
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
							data : [
								{type : 'max', name: '成交笔数'}
							]
						}
					},
					{
						name:'成功笔数',
						type:'line',
						itemStyle: {
							normal: {
								lineStyle: {
									shadowColor : 'rgba(0,0,0,0.4)',
									shadowBlur: 5,
									shadowOffsetX: 3,
									shadowOffsetY: 3
								}
							}
						},
						data: transSuccessCount,
						markPoint : {
							data : [
								{type : 'transAmount', name: '成功笔数'}
							]
						}
					},
					{
						name:'失败笔数',
						type:'line',
						itemStyle: {
							normal: {
								lineStyle: {
									shadowColor : 'rgba(0,0,0,0.4)',
									shadowBlur: 5,
									shadowOffsetX: 3,
									shadowOffsetY: 3
								}
							}
						},
						data: transFailureCount,
						markPoint : {
							data : [
								{type : 'transAmount', name: '失败笔数'}
							]
						}
					}
				]
			};
	        
	        // 为echarts对象加载数据 
	        myChart1.setOption(option); 
	        //图表选项
			var option2 = {
				title : {
					text: dataTitle,
					subtext: '交易成功率统计'
				},
				tooltip : {
					////显示当前数据点x、y轴信息和数值信息。
					trigger: 'axis'
				},
				legend: {
					data:['成功率']
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
							formatter: '{value} %'
						},
						splitArea : {show : true}
					}
				],
				series : [
					{
						name:dataName,
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
						data: dataRate,
						markPoint : {
							data : [
								{type : 'rate', name: '成功率'}
							]
						}
					}
				]
			};
	        myChart2.setOption(option2); 
	        //图表选项
			var option3 = {
				title : {
					text: dataTitle,
					subtext: '交易金额统计'
				},
				tooltip : {
					////显示当前数据点x、y轴信息和数值信息。
					trigger: 'axis'
				},
				legend: {
					data:['交易金额','成功金额']
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
							formatter: '{value} '+data[0].transCurrency
						},
						splitArea : {show : true}
					}
				],
				series : [
					{
						name:'交易金额',
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
							data : [
								{type : 'rate', name: '交易金额'}
							]
						}
					},
					{
						name:'成功金额',
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
							data : [
								{type : 'rate', name: '成功金额'}
							]
						}
					}
				]
			};
			myChart3.setOption(option3); 
	    }
	);
}

function monthChange(){
	var year = $("#year").val();
	if(year){
		$("#monthDiv").show();
	}else{
		$("#month").val("");
		$("#monthDiv").hide();
	}
}

function showDiv(){
	var div = $("#showDiv").val();
	if(1 == div){
		$("#main1").show();
		$("#main2").hide();
		$("#main3").hide();
	}
	if(2 == div){
		$("#main1").hide();
		$("#main2").show();
		$("#main3").hide();
	}
	if(3 == div){
		$("#main1").hide();
		$("#main2").hide();
		$("#main3").show();
	}
}
</script>
<body style="background-color:#EEEEEE; ">
<div id="zb_div" class="zb_mask"><div class="zb_mask_img"></div></div>
 <div class="container-fluid">
    <div class="row-fluid" style="margin-top: 0px;">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>交易趋势统计</h5>
          </div>
         <div class="widget-content nopadding">
          <br>
          	  <div class="span12 btn-icon-pg" id="selectForm">
                 		终端号：<select name="terNo" id="terNo" style="width:100px;">
                 	  			<option value="">所有</option>
								<c:forEach items="${SESSION_AUTHENTICATION.terNos }" var="terNo">
								<option value="${terNo }">${terNo }</option>
								</c:forEach>
							</select>
                 	　　	时间周期选择：<select name="year" id="year"  onchange="monthChange()"  style="width:100px;">
                 						<option value="">请选择</option> 
									    <option value="2015">2015</option>
									    <option value="2016">2016</option>  
									    <option value="2017">2017</option>  
									    <option value="2018">2018</option>  
									    <option value="2019">2019</option>
									    <option value="2020">2020</option>
									    <option value="2021">2021</option>
									    <option value="2022">2022</option>
									    <option value="2023">2023</option>
									    <option value="2024">2024</option>
									    <option value="2025">2025</option>
									</select>年
									<select name="month" id="month"  style="width:100px;">
									    <option value="">请选择</option>  
									    <option value="01">1</option>  
									    <option value="02">2</option>  
									    <option value="03">3</option>  
									    <option value="04">4</option>  
									    <option value="05">5</option>  
									    <option value="06">6</option>  
									    <option value="07">7</option>  
									    <option value="08">8</option>  
									    <option value="09">9</option>  
									    <option value="10">10</option>  
									    <option value="11">11</option>  
									    <option value="12">12</option>
									</select>月
									<br>
									<font color='red'>注：成功率=成功笔数/（交易完成笔数-风险单笔数）*100%</font>
		                
               </div>
	           <div class="controls controls-row">
		           <div class="span12" align="right" >
		           		<button class="btn btn-info" id="searchFrom" style="margin-right: 10px;">检索</button>
		           		<span  id="showButton"><button class="btn btn-info" id="show" style="margin-right: 10px;">显示统计图</button></span>
		           </div>
	          </div>
          </div>
          <div id="drawPic" style="display: none;">
          <select id="showDiv" onchange="showDiv()" style="width: 250px;">
          	<option value="1">交易成功统计图</option>
          	<option value="2">交易成功率统计图</option>
          	<option value="3">交易金额统计图</option>
          </select><br/>
          <div id="main1" style="height:600px;width:900px" style="display: none;"></div><!-- 成功图 -->
          <div id="main2" style="height:600px;width:900px" style="display: none;"></div><!-- 成功率图 -->
          <div id="main3" style="height:600px;width:900px" style="display: none;"></div><!-- 交易金额图 -->
       	</div>
       	<div id="drawTable">
       	<div class="widget-content nopadding">
       	 <table class="table table-bordered data-table table-condensed" style="border-bottom: 1px solid #dddddd;" >
              <thead style="">
                <tr>
                <th style="font-size: 14px;" >时间</th>
                  <th style="font-size: 14px;" >交易完成笔数</th>
                  <th style="font-size: 14px;">交易金额</th>
                  <th style="font-size: 14px;" >成功笔数</th>
                  <th style="font-size: 14px;">成功金额</th>
                  <th style="font-size: 14px;">失败笔数</th>
                  <th style="font-size: 14px;">风险单笔数</th>
                  <th style="font-size: 14px;">成功率</th>
                </tr>
              </thead>
              <tbody>
                
              </tbody>
            </table>
            </div>
        </div>
        </div>
      </div>
    </div>
  </div>
	 <!-- 警告处理 -->
    <div class="modal fade" id="alert" tabindex="3" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		style="display: none;" >
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="false">&times;</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">确认</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- end 警告 -->
</body>
</html>