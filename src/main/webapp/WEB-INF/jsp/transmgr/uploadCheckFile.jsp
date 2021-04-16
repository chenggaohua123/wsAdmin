<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
	String path = request.getContextPath();
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=path %>/dwz/css/uploader.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=path %>/dwz/css/demo.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=path %>/dwz/js/dmuploader.js"></script>
    <script type="text/javascript" src="<%=path %>/dwz/js/demo.js"></script>
<title>Insert title here</title>
</head>
<script type="text/javascript">
$(function(){
	$("#c_div_2").show();
});

	function changeType(){
		var type = $("#c_exportType").val();
		if(2 == type){
			$("#c_div_2").hide();
			$("#c_div_3").hide();
			$("#c_div_4").hide();
			$("#c_spanInfo").text("流水号,银行交易金额");
		}
		if(3 == type){
			$("#c_div_2").hide();
			$("#c_div_3").show();
			$("#c_div_4").hide();
			$("#c_spanInfo").text("前六后四卡号,交易日期,银行交易金额,授权码");
		}
		if(4 == type){
			$("#c_div_2").hide();
			$("#c_div_3").hide();
			$("#c_div_4").show();
			$("#c_spanInfo").text("全卡号,交易日期,银行交易金额,授权码");
		}
	}
	//新开窗口打开方法
	var NewWindow=0;
	function openWindow(httpUrl){
	//判断该窗口(NewWindow)是否已经存在，如果已经存在，则先关闭窗口，然后再打开新窗口
	if(NewWindow){
		if(!NewWindow.closed)
		NewWindow.close();
	}
	//根据参数定位弹出窗口的展示位置
	NewWindow = window.open (httpUrl,'newwindow','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=true, resizable=no,location=no, status=no')
	}
	
function downloadTransModel(){
	location.href="<%=path %>/transmgr/downloadTransModel?type="+$('#c_exportType').val();
}
</script>
<body>
<div class="pageHeader" >
<select id="c_exportType" onchange="changeType()">
	<option value="2">上传：流水号,银行交易金额</option>
	<option value="3">上传：前六后四卡号,交易日期,银行交易金额,授权码</option>
	<option value="4">上传：全卡号,交易日期,银行交易金额,授权码</option>
</select><button onclick="downloadTransModel()">下载&nbsp;<span id="c_spanInfo" onclick="downloadTransModel()">流水号,银行交易金额</span>&nbsp;模板</button>
<li><a class="edit" href="<%=path %>/transchangemgr/transSuccessToFail" target="dialog" width="500" height="250" mask="true"><button>强制失败</button></a></li>
<div id="c_div_2" style="display:none;">
	<font color="red">
		<b>上传注意事项:<br>
		1.上传文件为txt类型 <br>
		2.文件内容:流水号,银行交易金额<br>
		3.第一行为类型，第二行为标题，第一、二行不允许删除<br>
		4.一行内容为一条订单<br>
		</b>
	</font>
</div>
<div id="c_div_3" style="display:none;">
	<font color="red">
	<b>上传注意事项:<br>
	1.上传文件为txt类型 <br>
	2.文件内容:前六后四卡号,交易日期,银行交易金额,授权码<br>
	3.分别用英文逗号隔开<br>
	4.第一行为目录<br>
	5.一行内容为一条订单<br>
	6.第一行为类型，第二行为标题，第一、二行不允许删除<br>
	</b></font>
</div>
<div id="c_div_4" style="display:none;">
	<font color="red">
	<b>上传注意事项:<br>
	1.上传文件为txt类型 <br>
	2.文件内容:全卡号,交易日期,银行交易金额,授权码<br>
	3.分别用英文逗号隔开<br>
	4.第一行为目录<br>
	5.一行内容为一条订单<br>
	6.第一行为类型，第二行为标题，第一、二行不允许删除<br>
	</b></font>
</div>
    <div class="container demo-wrapper">
      <div class="row demo-columns">
        <div class="col-md-6">
          <div id="drag-and-drop-zone" class="uploader">
            <div>请拖放文件在这里</div>
            <div class="or">-或者-</div>
            <div class="browser">
              <label>
                <span>点击这里选择文件</span>
                <input type="file" name="file" multiple="multiple" title='点击添加文件'>
              </label>
            </div>
          </div>
          <div class="drag-and-drop-zone">
            <div class="panel-heading">
              <h3 class="panel-title">上传信息</h3>
            </div>
            <div class="panel-body demo-panel-debug">
              <ul id="demo-debug">
              </ul>
            </div>
          </div>
        
          <div class="drag-and-drop-zone">
            <div class="panel-heading">
              <h3 class="panel-title">上传进度</h3>
            </div>
            <div class="panel-body demo-panel-files" id='demo-files'>
              <span class="demo-note">No Files have been selected/droped yet...</span>
            </div>
          </div>
        </div>
      </div>
    </div>
</div>
    <script type="text/javascript">
      $('#drag-and-drop-zone').dmUploader({
        url: '<%=path%>/transmgr/acceptCheckFile',
        dataType: 'json',
        allowedTypes: 'text/*',
        maxFileSize:1024*1024,
        /*extFilter: 'jpg;png;gif',*/
        onInit: function(){
          $.danidemo.addLog('#demo-debug', 'default', '初始化完成。');
        },
        onBeforeUpload: function(id){
          $.danidemo.addLog('#demo-debug', 'default', '开始上传：文件ID:' + id);

          $.danidemo.updateFileStatus(id, 'default', '上传中...');
        },
        onNewFile: function(id, file){
          $.danidemo.addFile('#demo-files', id, file);
        },
        onComplete: function(){
          $.danidemo.addLog('#demo-debug', 'default', '所有文件上传完毕');
        },
        onUploadProgress: function(id, percent){
          var percentStr = percent + '%';

          $.danidemo.updateFileProgress(id, percentStr);
        },
        onUploadSuccess: function(id, data){
          $.danidemo.addLog('#demo-debug', 'success', '上传文件ID：' + id + ' 完成');

          $.danidemo.addLog('#demo-debug', 'info', '服务器返回信息 #' + id + ': ' + data.message);

          $.danidemo.updateFileStatus(id, 'success', '上传完成');

          $.danidemo.updateFileProgress(id, '100%');
          
          openWindow("<%=path %>/transmgr/goQueryAcceptCheckPage");
          //alertMsg.correct(data.message);
        },
        onUploadError: function(id, message){
          $.danidemo.updateFileStatus(id, 'error', message);

          $.danidemo.addLog('#demo-debug', 'error', 'Failed to Upload file #' + id + ': ' + message);
        },
        onFileTypeError: function(file){
          $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' cannot be added: must be an image');
        },
        onFileSizeError: function(file){
          $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' cannot be added: size excess limit');
        },
        onFileExtError: function(file){
          $.danidemo.addLog('#demo-debug', 'error', 'File \'' + file.name + '\' has a Not Allowed Extension');
        },
        onFallbackMode: function(message){
          $.danidemo.addLog('#demo-debug', 'info', 'Browser not supported(do something else here!): ' + message);
        }
      });
    </script>

</body>
</html>