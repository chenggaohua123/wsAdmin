<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加用户</title>
</head>
<script type="text/javascript">
 $(function(){
	 $("#uName").val("");
	 $("#pWord").val("");
 });

</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/sysmgr/addUserInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>用 户 名：</label>
                <input name="userName" id="uName" type="text" size="30" class="required" value=""/>
            </p>
            <p>
                <label>密    码：</label>
                <input name="passWord" id="pWord" type="password" size="30" class="required alphanumeric" value="" minlength="6" maxlength="20" alt="字母、数字、下划线 6-20位"/>
            </p>
            <p>
                <label>动态密钥：</label>
                <input name="seed" type="text" size="30" class="required"/>
            </p>
             <p>
                <label>联系电话：</label>
                <input name="phoneNo" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>真实姓名：</label>
                <input name="realName" type="text" size="30" class=""/>
            </p>
            <p>
               <label>登录方式</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=VERTION" relValue="columnKey" relText="columnvalue" name="verificationType">
			    </select>
            </p>
            <p>
               <label>选择系统</label>
               <select class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=SYSTEMID" relValue="columnKey" relText="columnvalue" name="systemId">
			    </select>
            </p>
            <p> 
                <label>邮    箱：</label>
                <input name="email" type="text" size="30" class="email"/>
            </p>
            <p>
                <label>联系地址：</label>
                <input name="address" type="text" size="30" class=""/>
            </p>
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