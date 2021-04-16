<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加修改卡bin信息</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/sysmgr/saveCardBinInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           	<input type="hidden" name="id" value="${info.id }"/>
           	<input type="hidden" name="isSearch" value="${empty info.isSearch?'0':info.isSearch }"/>
           	<p>
           		binlist.net
           	</p>
            <p>
                <label>bin：</label>
                <input name="bin" type="text" size="30" class="required" value="${info.bin }"/>
            </p>
            <p>
                <label>卡种：</label>
                 <input name="brand" type="text" size="30" class="required" value="${info.brand }"/>
            </p>
            <%--
            <p>
            	<label>卡种类别名称</label>
            	<input name="sub_brand" size="30" type="text" class="required" value="${info.sub_brand }"/>
            </p>
             --%>
             <p>
                <label>国家编码：</label>
                <input name="country_code" type="text" size="30" class="required" value="${info.country_code }"/>
            </p>
             <p>
                <label>国家：</label>
                <input name="country_name" type="text" size="30" class="required" value="${info.country_name }"/>
            </p>
             <p>
                <label>发卡行：</label>
                <input name="bank" type="text" size="30" class="required" value="${info.bank }"/>
            </p>
            <p>
           		bins.pro
           	</p>
            <p>
                <label>卡类型：</label>
                <input name="card_type" type="text" size="30" class="required" value="${info.card_type }"/>
            </p>
            <p>
                <label>国家：</label>
                <input name="country" type="text" size="30" class="required" value="${info.country }"/>
            </p>
            <p>
                <label>卡种：</label>
                <input name="vendor" type="text" size="30" class="required" value="${info.vendor }"/>
            </p>
            <p>
                <label>卡类型：</label>
                <input name="type" type="text" size="30" class="required" value="${info.type }"/>
            </p>
            <p>
                <label>Level：</label>
                <input name="level" type="text" size="30" class="required" value="${info.level }"/>
            </p>
            <p>
                <label>发卡行：</label>
                <input name="proBank" type="text" size="30" class="required" value="${info.proBank }"/>
            </p>
            <p>
           		common
           	</p>
            <p>
                <label>经度：</label>
                <input name="longitude" type="text" size="30" class="required" value="${info.longitude }"/>
            </p>
            <p>
                <label>纬度：</label>
                <input name="latitude" type="text" size="30" class="required" value="${info.latitude }"/>
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