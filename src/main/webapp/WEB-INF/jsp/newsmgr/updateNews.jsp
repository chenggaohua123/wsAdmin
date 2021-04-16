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
<title>修改新闻公告</title>
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
	<form method="post" action="<%=path %>/newsmgr/updateNews" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input name="id" value="${info.id }" type="hidden">
        <div class="pageFormContent" layoutH="50">
            <p>
                <label>标题：</label>
                <input name="newsTitle" type="text" id="newsTitle" size="30" class="required" value="${info.newsTitle }"/>
            </p>
            <p> 
                <label>类型：</label>
                <input name="newsType" type="radio" size="30"  ${info.newsType=='0'?'checked=\"checked\"':'' } value="0"/>新闻
                <input name="newsType" type="radio" size="30"   ${info.newsType=='1'?'checked=\"checked\"':'' } value="1"/>公告
            </p>
            <p>
                <label>开始时间：</label>
                 <input type="text" name="startDate"  id = "startDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${funcs:formatDate(info.startDate,'yyyy-MM-dd')}" />
            </p>
             <p>
                <label>结束时间：</label>
               <input type="text" name="endDate"  id = "endDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${funcs:formatDate(info.endDate,'yyyy-MM-dd')}"/>
            </p>
            <p>
                <label>是否置顶：</label>
                 <input name="isTop" type="radio" size="30"  ${info.isTop=='0'?'checked=\"checked\"':'' } value="0"/>否
                <input name="isTop" type="radio" size="30"   ${info.isTop=='1'?'checked=\"checked\"':'' } value="1"/>是
            </p>
             <p>
             <span id="showDate"   ${info.isTop=='1'?'style=\"display:block;\"':'style=\"display:none;\"' }>
                <label>置顶时间：</label>
                 <input type="text" name="topDate"  id = "topDate" readonly="readonly" dateFmt="yyyy-MM-dd" class="date" value="${funcs:formatDate(info.topDate,'yyyy-MM-dd')}"/>
           	</span>
           	</p>
           	<p> 
                <label>发布状态：</label>
                <input name="enableFlag" type="radio" size="30"  ${info.enableFlag=='0'?'checked=\"checked\"':'' } value="0"/>未发布
                <input name="enableFlag" type="radio" size="30"   ${info.enableFlag=='1'?'checked=\"checked\"':'' } value="1"/>已发布
            </p>
            <p></p>
            <p>
            <label>内容：</label>
            </p>
            <div class="unit">
				<textarea class="editor" name="newsContext" rows="9" cols="100" tools="mfull" >${info.newsContext }</textarea>
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