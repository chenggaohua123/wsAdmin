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

<title>??????????????????</title>
</head>
<script type="text/javascript">

$(document).ready(function(){
	var table = $('.data-table').dataTable({
		"bJQueryUI": true,
		"sAjaxSource":"<%=path%>/countAnalysis/riskList",
		"sPaginationType": "full_numbers",
		"sScrollY":document.body.clientHeight-180-140,
		"bServerSide": true,
		"bProcessing":true,
		"bInfo":true,
		"bFilter":false,
		"bAutoWidth": true,
		"oLanguage" : { //????????????????????????????????????
			"sProcessing" : "????????????...", //???????????????????????????
			"sLengthMenu" : "??????_MENU_???",//???????????????????????????
			"sEmptyTable" : "??????????????????",//??????????????????????????????
			"sZeroRecords" : "??????????????????",//??????????????????????????????
			"sInfoEmpty" : "",//????????????,??????????????????????????????
			"sSearch" : "??????:",//???????????????????????????
			"sInfo" : "????????????_TOTAL_????????????_START_???_END_",
			"oPaginate" : {
			"sFirst" : "??????",
			"sLast" : "??????",
			"sNext" : "??????",
			"sPrevious" : "??????"
			}
			},
			
			"aoColumnDefs": [
				{
					"aTargets": [4],
					"fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
						var s = sData.replace(/,/g, "");
						if(s){
							var strs=s.split("&nbsp;");
							var str = '<a href="javascript:showRiskPer(\''+strs[0]+'\')" >??????????????????</a> | <a href="javascript:downloadRiskInfo(\''+strs[0]+'\')" >??????????????????</a>';
							$(nTd).html(str);
							$(nTd).css("white-space","nowrap");
						}else{
							$(nTd).html("");
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
	
	$("#amg").click(function(){
		var param='?';
		$("#selectForm input,select").each(function(){
			if($(this).attr("name")!=undefined){
				param+=$(this).attr("name")+"="+$(this).val()+"&";
			}
		});
		$.ajax({
	        type:"POST",
	        data:"",
	        url:"<%=path%>/countAnalysis/failureList"+param,
	        dataType:"json",
	      	success: function(param){
	      		$('#addModel').modal("show");
				showAmg(param.aaData);
	      	}
		});
	});
	
});
function showRiskPer(terNo){
	var param='?';
	$("#selectForm input,select").each(function(){
		if($(this).attr("name")!=undefined&&$(this).attr("name")!='terNo'){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});
	var url = "<%=path%>/countAnalysis/showRiskPer"+param+"terNo="+terNo;
	$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:"",
		success: function(json){
            $("#alert #myModalLabel").text("??????????????????");
            str="<table border=\"0\" cellspacing=\"1px\" bgcolor=\"black\" width='100%'>";
            str+="<tr bgcolor='white'><td>??????????????????</td><td>??????????????????</td><td>??????</td></tr>";
            for(var i=0;i<json.length;i++){
            	 str+="<tr bgcolor='white'><td>"+json[i].reason+"</td><td>"+json[i].riskCount+"</td><td>"+(json[i].riskRate*100).toFixed(2)+"%</td></tr>";	
            }
            str+="<table>";
            $("#alert .modal-body").html(str);
            $('#add').modal("hide");
            $('#alert').modal('show'); 
		} 
        });
}

function downloadRiskInfo(terNo){
	var param='?';
	$("#selectForm input,select").each(function(){
		if($(this).attr("name")!=undefined&&$(this).attr("name")!='terNo'){
			param+=$(this).attr("name")+"="+$(this).val()+"&";
		}
	});	    
	var url="<%=path%>/countAnalysis/downloadRiskInfo"+param+"terNo="+terNo;
	window.location.href=url;
}
</script>
<body style="background-color:#EEEEEE; ">
 <div class="container-fluid">
    <div class="row-fluid" style="margin-top: 0px;">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>??????????????????</h5>
          </div>
         <div class="widget-content nopadding">
          <br>
          	  <div class="span12 btn-icon-pg" id="selectForm">
                 		????????????<select name="terNo"  style="width:9%;">
                 	  			<option value="">??????</option>
								<c:forEach items="${SESSION_AUTHENTICATION.terNos }" var="terNo">
								<option value="${terNo }">${terNo }</option>
								</c:forEach>
							</select>&nbsp;
						?????????<select name="cardType"  style="width:150px;">
                 	  			<option value="">??????</option>
								<option value="visa">visa</option>
								<option value="master">master</option>
								<option value="jcb">jcb</option>
							</select>
                 		?????????????????????<input type="text" name="statusDate" onClick="WdatePicker()"  style="width:5%;" readonly="readonly"/>
		               	-
		               	<input type="text" name="endDate" onClick="WdatePicker()"  style="width:5%;" readonly="readonly"/>
		                <br>
                 		<br/>
                 		<font color="red">
		                ??????????????????=????????????/??????????????????*100%
			</font>
               </div>
	           <div class="controls controls-row">
		           <div class="span12" align="right" >
		           		<button class="btn btn-info" id="searchFrom" style="margin-right: 10px;">??????</button>
		           </div>
	          </div>
          </div>
         
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" style="border-bottom: 1px solid #dddddd;" >
              <thead>
                <tr>
                  <th style="font-size: 14px;">?????????</th>
                  <th style="font-size: 14px;">??????????????????</th>
                  <th style="font-size: 14px;">??????????????????</th>
                  <th style="font-size: 14px;">??????????????????</th>
                  <th style="font-size: 14px;">??????</th>
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
  
	 <!-- ???????????? -->
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
					<button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
				</div>
			</div>
		</div>
	</div>
	<!-- end ?????? -->
</body>
</html>