<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="funcs" uri="funcs"%> 
 <%
	String path = request.getContextPath();
%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加新闻公告</title>
</head>

<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='isTop']").click(function(){
			if($("input[name='isTop']:checked").val()=='1'){
				$("#showDate").show();
			}else{
				$("#showDate").hide();
			}
		});		
	});
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/newsmgr/addNews" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>标题：</label>
                <input name="newsTitle" type="text" id="newsTitle" size="30" class="required"/>
            </p>
            <p> 
                <label>类型：</label>
                <input name="newsType" type="radio" size="30"  checked="checked" value="0"/>新闻
                <input name="newsType" type="radio" size="30"   value="1"/>公告
            </p>
            <p>
                <label>开始时间：</label>
                 <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" />
            </p>
             <p>
                <label>结束时间：</label>
               <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" />
            </p>
            <p>
                <label>是否置顶：</label>
                 <input name="isTop" type="radio" size="30"  checked="checked" value="0"/>否
                <input name="isTop" type="radio" size="30"   value="1"/>是
            </p>
             <p>
             <span id="showDate" style="display:none;">
                <label>置顶时间：</label>
                 <input type="text" name="topDate"  id = "topDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" />
           	</span>
           	</p>
            <p>
            <label>内容：</label>
            </p>
            <div class="unit">
				<textarea class="editor" name="newsContext" rows="9" cols="100" tools="mfull"></textarea>
			</div>
            
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>