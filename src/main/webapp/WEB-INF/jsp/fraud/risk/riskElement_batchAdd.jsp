<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>    
<script type="text/javascript">
        $(document).ready(function()
        {	
            $("#selectFile").uploadify({
            	'swf':'<%=request.getContextPath() %>/static/manager/js/uploadify/scripts/uploadify.swf',
                'uploader': '<%=request.getContextPath() %>/manager/fraud/risk/batchAddRiskElementInfo?jsessionid=<%=request.getSession().getId()%>',
                'cancelImg': '<%=request.getContextPath() %>/static/manager/js/uploadify/img/cancel.jpg',
                'buttonImage':'<%=request.getContextPath() %>/static/manager/js/uploadify/img/add.jpg',
                'button_image_url':'<%=request.getContextPath() %>/static/manager/js/uploadify/img/add.jpg',
                'buttonText':'',
                'buttonClass':'my-uploadify-button',
                'queueID': 'fileQueue',
                'auto': false,
                'multi': false,
                'width':'102',
                'fileObjName':'Filedata',
                'fileSizeLimit':1024*10,
                'debug':true,
                'onUploadSuccess':function(file,data,response){
                	var json = $.parseJSON(eval(data)); 
                	var list = json.list;
                	var errorLine = json.errorLine;
                	var count = json.count;
                	var table = "<table class='table' id='riskInfolist' width='100%' layoutH=350 ><thead><tr><th>元素类型</th><th>元素值</th><th>创建人</th></tr></thead><tbody>";
                	for(var i = 0;i<list.length;i++){
                		table+="<tr><td>"+list[i].elementType+"</td><td>"+list[i].elementValue+"</td><td>"+list[i].createBy+"</td></tr>";
                	}
                	table+="</tbody></table>";
                	$("#pageContent").append(table);
                	$("#riskInfolist").jTable();
                	var msg = "更新了"+count+"数据.";
                	if(""!=errorLine){
                		msg += "第"+errorLine+"数据格式有错误，请检查之后再提交.";	
                	}
                	alertMsg.info(msg);
                }
            });
        });  
</script>

<div class="pageHeader">
		<input id="selectFile" type="file" name="file" name="image2" />
		<div class="divider"></div>
		<div id="fileQueue" class="fileQueue">
			<input type="image" src="<%=request.getContextPath() %>/static/manager/js/uploadify/img/upload.jpg" onclick="$('#selectFile').uploadify('upload', '*');" /> 
			<input type="image" src="<%=request.getContextPath() %>/static/manager/js/uploadify/img/cancel.jpg" onclick="$('#selectFile').uploadify('cancel', '*');" />
			<a href="<%=request.getContextPath()%>/template/batchaAddRiskInfo.txt" target="_blank">模板下载</a>
		</div>
</div>
<div class="pageContent" id="pageContent">
</div>
