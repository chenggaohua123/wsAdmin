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
<title>投诉订单分析</title>
</head>
<script type="text/javascript">
$(document).ready(function(){
	var table = $('.data-table').dataTable({
		"bJQueryUI": true,
		"sAjaxSource":"<%=path%>/countAnalysis/countComplaintList",
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
					{
						"aTargets": [7],
						"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
							var strs=sData.split("&nbsp;");
							var time=strs[0];
							var tradeNos=strs[1];
							if(time!='0'){
								var str = '<a href="javascript:showRes(\''+time+'\')" >投诉构成</a> | <a href="javascript:downloadTransInfo(\''+tradeNos+'\')">导出明细</a>';
								$(nTd).html(str);
								$(nTd).css("white-space","nowrap");
							}else{
								$(nTd).html("");
							}
						}
					},{
						"aTargets": [0],
						"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
							if(sData!='0'){
								$(nTd).html(sData);
							}else{
								$(nTd).html("");
							}
						}
					},{
						"aTargets": [4],
						"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
							$(nTd).html((sData*100).toFixed(2)+"%");
						}
					},{
						"aTargets": [6],
						"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
							$(nTd).html((sData*100).toFixed(2)+"%");
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
	
});


function downloadTransInfo(tradeNos){
	window.location.href="<%=path%>/countAnalysis/downloadTransInfo?tradeNoS="+tradeNos+"&type=2";
}
function showRes(time){
	var url = "<%=path%>/countAnalysis/showComPer?time=" + time;
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:"",
		success: function(json){
            $("#alert #myModalLabel").text("投诉占比");
            str="<table border=\"0\" cellspacing=\"1px\" bgcolor=\"black\" width='100%'>";
            str+="<tr bgcolor='white'><td>投诉原因</td><td>投诉笔数</td><td>投诉占比</td><td>投诉转换拒付笔数</td><td>拒付转换率</td></tr>";
            for(var i=0;i<json.length;i++){
            	 str+="<tr bgcolor='white'><td>"+json[i].reason+"</td><td>"+json[i].comCount+"</td><td>"+(json[i].comRate*100).toFixed(2)+"%</td><td>"+json[i].comToDisCount+"</td><td>"+(json[i].comToDisRate*100).toFixed(2)+"%</td></tr>";	
            }
            str+="<table>";
            $("#alert .modal-body").html(str);
            $('#add').modal("hide");
            $('#alert').modal('show'); 
		} 
        });
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
</script>
<body style="background-color:#EEEEEE; ">
 <div class="container-fluid">
    <div class="row-fluid" style="margin-top: 0px;">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>投诉订单分析</h5>
          </div>
         <div class="widget-content nopadding">
          <br>
          	  <div class="span12 btn-icon-pg" id="selectForm">
                 		终端号：<select name="terNo" style="width:150px;">
                 	  			<option value="">所有</option>
								<c:forEach items="${SESSION_AUTHENTICATION.terNos }" var="terNo">
								<option value="${terNo }">${terNo }</option>
								</c:forEach>
							</select>
                 	　　	时间周期选择：<select name="year" id="year"  onchange="monthChange()" style="width:150px;">
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
								<span id="monthDiv" style="display:none;" >
									<select name="month" id="month" style="width:150px;">
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
								</span>
		                <br>
		                <font color="red">
		                总投诉率=总投诉通知笔数/总交易成功笔数*100%<br>
			月投诉率=当月通知投诉笔数/当月交易成功笔数*100%<br>
			拒付转换率=投诉转换拒付笔数/投诉笔数*100%<br></font>
               </div>
	           <div class="controls controls-row">
		           <div class="span12" align="right" >
		           		<button class="btn btn-info" id="searchFrom" style="margin-right: 10px;">检索</button>
		           </div>
	          </div>
          </div>
         
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" style="border-bottom: 1px solid #dddddd;" >
              <thead>
                <tr>
                  <th style="font-size: 14px;">时间</th>
                  <th style="font-size: 14px;">统计方式</th>
                  <th style="font-size: 14px;">交易成功笔数</th>
                  <th style="font-size: 14px;">投诉笔数</th>
                  <th style="font-size: 14px;">投诉率</th>
                  <th style="font-size: 14px;">投诉转换拒付笔数</th>
                  <th style="font-size: 14px;">拒付转换率</th>
                  <th style="font-size: 14px;">操作</th>
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