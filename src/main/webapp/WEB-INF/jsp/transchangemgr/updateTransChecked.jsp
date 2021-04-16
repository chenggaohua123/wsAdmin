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
<title>复核勾兑记录</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/transchangemgr/updateTransChecked" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="id" value="${info.id }">
        <div class="pageFormContent" layoutH="56">
        <p>
                <label>流水号：</label>
                <input name="tradeNo" type="text" size="30" class="required" value="${info.tradeNo }" readonly="readonly" />
            </p>
           <p>
                <label>参考号：</label>
                <input name="relNo" type="text" size="30" class="required" value="${info.relNo }" readonly="readonly"  />
            </p>
            <p>
                <label>交易币种：</label>
                <input name="currency" type="text" size="30"  class="required" value="${info.currency }" readonly="readonly"/>
            </p>
            <p>
                <label>交易金额：</label>
                <input name="amount" type="text" size="30" class="required" value="${info.amount }" readonly="readonly"/>
            </p>
            <p>
          	  <label>状态：</label>
				<select name="status" onchange="show(this.value)">
					<option value="1" ${info.status=='1'?'selected':'' }>已勾兑</option>
					<%-- <option value="2" ${info.status=='2'?'selected':'' }>勾兑异常</option> --%>
					<option value="3" ${info.status=='3'?'selected':'' }>无效</option>
				</select>
            </p>
            <script type="text/javascript">
            	function show(status){
            		if(status==1){
            			$("#remind").show();
            		}else{
            			$("#remind").hide();
            		}
            	}
            </script>
            <p><span id="remind" style="color:red;display:none;">已勾兑：支付失败的单会直接变为支付成功！</span></p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">复核</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
	</div>
</body>
</html>