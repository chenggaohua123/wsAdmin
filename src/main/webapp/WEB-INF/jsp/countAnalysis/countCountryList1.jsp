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
<title>国家交易分布</title>
</head>
<script type="text/javascript">

$(document).ready(function(){
	$( "#d11" ).val( new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate());
	$( "#d12" ).val( new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate());

	var table = $('.data-table').dataTable({
		"bJQueryUI": true,
		"sAjaxSource":"<%=path%>/countAnalysis/countCountryList",
		"sPaginationType": "full_numbers",
		"sScrollY":document.body.clientHeight-180-140,
		"bServerSide": true,
		"bProcessing":true,
		"bInfo":true,
		"bFilter":false,
		"bAutoWidth": true,
		"oLanguage" : { //主要用于设置各种提示文本
			"sProcessing" : "正在处理...", //设置进度条显示文本
			"sLengthMenu" : "每页_MENU_行",//显示每页多少条记录
			"sEmptyTable" : "没有找到记录",//没有记录时显示的文本
			"sZeroRecords" : "没有找到记录",//没有记录时显示的文本
			"sInfoEmpty" : "",//没记录时,关于记录数的显示文本
			"sSearch" : "搜索:",//搜索框前的文本设置
			"sInfo" : "总记录数_TOTAL_当前显示_START_至_END_",
			"oPaginate" : {
			"sFirst" : "首页",
			"sLast" : "未页",
			"sNext" : "下页",
			"sPrevious" : "上页"
			}
			},
			
			"aoColumnDefs": [
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
		 clickImg();
		 table.fnDraw();
	});
	
	
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
	
	$("#uploadCountryList").click(function(){
		var param='?';
		$("#selectForm input,select").each(function(){
			if($(this).attr("name")!=undefined){
				param+=$(this).attr("name")+"="+$(this).val()+"&";
			}
		});
		window.location.href="<%=path%>/countAnalysis/uploadCountryList"+param;
	});
	
	
	$("#img").click(function(){
		if($("#img").text()=="国家交易统计饼图"){
			$("#img").text("显示数据");
			clickImg();
			$("#drawPic").show();
			$("#drawTable").hide();
		}else{
			$("#img").text("国家交易统计饼图");
			$("#drawPic").hide();
			$("#drawTable").show();
			table.fnDraw();
		}
	});
	
});

function clickImg(){
	var param='?';
	$("#selectForm input,select").each(function(){
		if($(this).attr("name")!=undefined){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	$.ajax({
        type:"POST",
        data:"",
        url:"<%=path%>/countAnalysis/countCountryList"+param,
        dataType:"json",
      	success: function(param){
      		$('#addModel').modal("show");
			showAmg(param.aaData);
      	}
	});
}

//路径配置
require.config({
	//chart:指一个完整的图表(如一个基本表或组合图表，可能包括坐标轴、图例等)
    paths:{ 
        'echarts' : '<%=path%>/btp/js/echarts',
        'echarts/chart/line' : '<%=path%>/btp/js/echarts'
    }
});

function showAmg(data){
	if(!data){
		$("#alert #myModalLabel").text("");
      	$("#alert .modal-body").text("查询数据为空");
      	$('#alert').modal('show');
		return false;
	}
	var dataLength = data.length;//数据量
	var country = []; //国家数组
	var countryData = [];//国家数据
	var countNum = 0;//前5个国家交易量
	var countTotolNum = 0;//所有交易总量
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
	    		        text: '国家交易分布',
	    		        subtext: '国家交易分布',
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
	    		            name:'国家交易分布比列',
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
<body style="background-color:#EEEEEE; ">

 <div class="container-fluid">
    <div class="row-fluid" style="margin-top: 0px;">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>国家交易分布</h5>
          </div>
         <div class="widget-content nopadding">
          <br>
          	  <div class="span12 btn-icon-pg" id="selectForm">
                 		终端号：<select name="terNo"  style="width:9%;">
                 	  			<option value="">所有</option>
								<c:forEach items="${SESSION_AUTHENTICATION.terNos }" var="terNo">
								<option value="${terNo }">${terNo }</option>
								</c:forEach>
							</select>&nbsp;
                 	　　	卡种：<select name="cardType"  style="width:150px;">
                 	  			<option value="">所有</option>
								<option value="visa">visa</option>
								<option value="master">master</option>
								<option value="jcb">jcb</option>
							</select>
                 		交易时间：<input type="text" id="d11" name="statusDate" onClick="WdatePicker()"  style="width:89px;" />
		               	-
		               	<input type="text" id="d12" name="endDate" onClick="WdatePicker()"  style="width:89px;"/>
		                <br>
                 		<font color="red">注：成功率=成功笔数/（交易完成笔数-风险单笔数）*100%</font>
               </div>
	           <div class="controls controls-row">
		           <div class="span12" align="right" >
		           		<button class="btn btn-info" id="searchFrom" style="margin-right: 10px;">检索</button>
		           		<button class="btn btn-info" id="uploadCountryList" style="margin-right: 10px;">导出国家交易统计</button>
		           		<button class="btn btn-info" id="img" style="margin-right: 10px;">国家交易统计饼图</button>
		           </div>
	          </div>
          </div>
         <div id="drawTable">
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" style="border-bottom: 1px solid #dddddd;" >
              <thead>
                <tr>
                  <th style="font-size: 14px;">国家</th>
                  <th style="font-size: 14px;">交易完成笔数</th>
                  <th style="font-size: 14px;">交易金额</th>
                  <th style="font-size: 14px;">成功笔数</th>
                  <th style="font-size: 14px;">成功金额</th>
                  <th style="font-size: 14px;">失败笔数</th>
                  <th style="font-size: 14px;">风险单笔数</th>
                  <th style="font-size: 14px;">拒付笔数</th>
                  <th style="font-size: 14px;">退单笔数</th>
                  <th style="font-size:	14px;">投诉笔数</th>
                  <th style="font-size:	14px;">成功率</th>
                  <th style="font-size:	14px;">拒付率</th>
                  <th style="font-size:	14px;">退单率</th>
                  <th style="font-size:	14px;">投诉率</th>
                  <th style="font-size:	14px;">交易占比</th>
                </tr>
              </thead>
              <tbody>
                
              </tbody>
            </table>
          </div>
          </div>
          <div id="drawPic" style="display: none;">
          <div id="main" style="height:400px;width:600px;" ></div>
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
		</div>
	</div>
	<!-- end 警告 -->
</body>
</html>