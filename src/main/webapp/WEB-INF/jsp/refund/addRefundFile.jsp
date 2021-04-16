<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="funcs" uri="funcs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=path %>/dwz/css/uploader.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=path %>/dwz/css/demo.css" rel="stylesheet" />
	<script type="text/javascript" src="<%=path %>/dwz/js/dmuploader.js"></script>
    <script type="text/javascript" src="<%=path %>/dwz/js/demo.js"></script>
    
    <script src="<%=path%>/dwz/js/ajaxfileupload.js"></script> 
    
    
<title>批量上传退款信息</title>
</head>
<body>
<font color="red"><b>上传注意事项:<br>
1.上传文件为txt类型 <br>
2.文件内容:交易流水号,退款金额,退款原因,退款备注<br>
3.分别用英文逗号隔开<br>
4.一行内容为一条退款信息<br>
例如:<br>
BR1508100956058644,0.1,批量退款1,备注1<br>
BR1508100956058644,0.1,批量退款2,备注2<br>
BR1506231111154493,0.1,批量退款3,备注3<br>
</b></font>
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
    <script type="text/javascript">
      $('#drag-and-drop-zone').dmUploader({
        url: '<%=path%>/refund/addRefundFile',
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
          
          alertMsg.correct(data.message);
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