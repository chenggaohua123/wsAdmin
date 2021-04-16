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
<title>修改商户结算条件</title>
</head>
<body>
<script type="text/javascript">
	function changeSettleService(settleType){
		if(settleType=='TN'){
			$("#settleService").val("merchantAccountCashByTN");
		}else if(settleType=="signed"){
			$("#settleService").val("merchantAccountCashBySigned");
		}else if(settleType=="sendGoods"){
			$("#settleService").val("merchantAccountCashBySendGoods");
		}
	}
</script>
<div class="pageContent">
	<form method="post" action="<%=path %>/settlemgr/updateSettleTypeInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
       <input type="hidden" name="settleService" value="${info.settleService }" id="settleService">
       <input type="hidden" name="id" value="${info.id }">
        <div class="pageFormContent" layoutH="56">
         <p>
                <label>商户号：</label>
                <input name="merNo" type="text" size="30" class="required number" value="${info.merNo }" readonly="readonly"/>
            </p>
            <p>
                <label>终端号：</label>
                <input name="terNo" type="text" size="30" class="required number" value="${info.terNo }" readonly="readonly"/>
            </p>
            <p>
                <label>结算类型：</label>
                <select onchange="changeSettleService(this.value)" class="combox" loadurl="<%=path %>/sysmgr/queryBaseDateInfo?tableName=SETTLETYPE" relValue="columnKey" relText="columnvalue" name="settleType" selectedValue="${info.settleType }">
				</select>
            </p>
            <p>
                <label>常规结算周期(天)：</label>
               <input name="settleCycle" type="text" size="30" class="required number" value="${info.settleCycle }" />
            </p>
            <p>
            	<label>保证金结算周期(天)：</label>
               <input name="bondCycle" type="text" size="30" class="required number" value="${info.bondCycle }"/>
            
            </p>
            <p>
               <label>是否有效：</label>
              	<select name="enabled" class="combox">
				<option value="1" ${info.enabled==1?'selected':'' }>有效</option>
				<option value="0" ${info.enabled==0?'selected':'' }>无效</option>
				</select>
            </p>
            <p>
                <label>备注：</label>
               <input name="remark" type="text" size="30" value="${info.remark }"/>
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