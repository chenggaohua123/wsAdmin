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
	
    <script src="<%=path %>/dwz/js/jquery-1.7.2.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/jquery.cookie.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/jquery.easing.1.3.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/jquery.validate.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.core.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.database.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/speedup.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.accordion.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.ajax.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.combox.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.dialog.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.drag.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.effects.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.history.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.navTab.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.pagination.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.panel.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.print.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.resize.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.scrollCenter.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.stable.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.tab.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.theme.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.tree.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.ui.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.util.date.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.util.number.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/js/dwz.validate.method.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
			<script src="<%=path %>/dwz/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
			<script type="text/javascript" src="<%=path %>/dwz/js/dmuploader.js"></script>
    	<script type="text/javascript" src="<%=path %>/dwz/js/demo.js"></script>
<title>????????????????????????</title>
</head>

<body>
<div class="pageHeader" >


<div id="c_div_2" >
	<font color="red">
		<b>??????????????????:<br>
		1.???????????????txt?????? <br>
		2.????????????:??????<br>
		4.??????????????????<br>
		5.????????????????????????????????????<br>
		</b>
	</font>
</div>

    <div class="container demo-wrapper">
      <div class="row demo-columns">
        <div class="col-md-6">
          <div id="drag-and-drop-zone" class="uploader">
            <div>????????????????????????</div>
            <div class="or">-??????-</div>
            <div class="browser">
              <label>
                <span>????????????????????????</span>
                <input type="file" name="file" multiple="multiple" title='??????????????????'>
              </label>
            </div>
          </div>
          <div class="drag-and-drop-zone">
            <div class="panel-heading">
              <h3 class="panel-title">????????????</h3>
            </div>
            <div class="panel-body demo-panel-debug">
              <ul id="demo-debug">
              </ul>
            </div>
          </div>
        
          <div class="drag-and-drop-zone">
            <div class="panel-heading">
              <h3 class="panel-title">????????????</h3>
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
        url: '<%=path%>/merchantmgr/qureyManyWebsite',
        /*dataType: 'json',*/
        allowedTypes: 'text/*',
        maxFileSize:1024*1024,
        /*extFilter: 'jpg;png;gif',*/
        onInit: function(){
          $.danidemo.addLog('#demo-debug', 'default', '??????????????????');
        },
        onBeforeUpload: function(id){
          $.danidemo.addLog('#demo-debug', 'default', '?????????????????????ID:' + id);

          $.danidemo.updateFileStatus(id, 'default', '?????????...');
        },
        onNewFile: function(id, file){
          $.danidemo.addFile('#demo-files', id, file);
        },
        onComplete: function(){
          $.danidemo.addLog('#demo-debug', 'default', '????????????????????????');
        },
        onUploadProgress: function(id, percent){
          var percentStr = percent + '%';

          $.danidemo.updateFileProgress(id, percentStr);
        },
        onUploadSuccess: function(id, data){
          $.danidemo.addLog('#demo-debug', 'success', '????????????ID???' + id + ' ??????');

          $.danidemo.addLog('#demo-debug', 'info', '????????????????????? #' + id + ': ' + data.message);

          $.danidemo.updateFileStatus(id, 'success', '????????????');

          $.danidemo.updateFileProgress(id, '100%');
          window.opener.sele(data);
          window.close();
          //openWindow("<%=path %>/merchantmgr/goQueryManyWebsitePage");
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