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
    
    
    
    
<title>交易导出</title>
</head>
<script type="text/javascript">

function gotoUploade(){
	var str = $("#tradeList").val();
	var flag= $("#modelFlag").val();
	//alert(flag);
	if(!str){
		alert("请填写终端号");
		return false;
	}
	if(!flag){
		alert("请选择查询类型");
	}
	location.href= "<%=path %>/transchangemgr/exportTransInfo?tradeList="+ str+"&modelFlag="+flag;
}

</script>
<body>
<span>流水号：</span><br>
<textarea rows="8" cols="55"  id="tradeList"></textarea><br>
<font color="red">提示：每个流水号以英文逗号隔开,选择输入的类型,默认是流水号类型</font><br>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a class="add" href="#" onclick="gotoUploade()" target="dwzExport" targetType="navTab"rel="addCurrency" width="550" height="300" mask="true"><button>交易数据导出</button></a>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<select  name="modelFlag" id="modelFlag">
	<option value="1" selected>订单号</option>
	<option value="2">流水号</option>
</select>

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <button type="button" class="close">取消</button>

</body>
</html>