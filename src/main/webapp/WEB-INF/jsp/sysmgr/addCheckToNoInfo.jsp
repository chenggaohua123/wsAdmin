<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//labelD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.labeld">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<div class="pageContent">
	<form method="post" action='<%=path %>/sysmgr/addCheckToNoInfo' class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
        <input type="hidden" name="id" value="${info.id }">
        	<p>
        		 <label>商户号：</label>
                 <input name="merNo" type="text" size="30" class="required" value="${info.merNo }"/>
        	</p>
        	<p>
        		 <label>终端号：</label>
                 <input name="terNo" type="text" size="30" class="required" value="${info.terNo }"/>
        	</p>
            <p>
                <label>卡号：</label>
                 <input name="checkNo" type="text" size="30" class="required" value="${info.cardSixAndFour }"/>
            </p>
            <p>
                <label>CVV：</label>
                <input name="c" type="text" size="30" class="required" value="${info.c }"/>
            </p>
             <p>
                <label>有效年：</label>
                <select name="y">
                	<option value="2016" ${info.y=='2016'?'selected':'' }>2016</option>
                	<option value="2017" ${info.y=='2017'?'selected':'' }>2017</option>
                	<option value="2018" ${info.y=='2018'?'selected':'' }>2018</option>
                	<option value="2019" ${info.y=='2019'?'selected':'' }>2019</option>
                	<option value="2020" ${info.y=='2020'?'selected':'' }>2020</option>
                	<option value="2021" ${info.y=='2021'?'selected':'' }>2021</option>
                	<option value="2022" ${info.y=='2022'?'selected':'' }>2022</option>
                	<option value="2023" ${info.y=='2023'?'selected':'' }>2023</option>
                	<option value="2024 ${info.y=='2024'?'selected':'' }">2024</option>
                </select>
            </p>
             <p>
                <label>有效月：</label>
                <select name="m">
                	<option value="01" ${info.m=='01'?'selected':'' }>01</option>
                	<option value="02" ${info.m=='02'?'selected':'' }>02</option>
                	<option value="03" ${info.m=='03'?'selected':'' }>03</option>
                	<option value="04" ${info.m=='04'?'selected':'' }>04</option>
                	<option value="05" ${info.m=='05'?'selected':'' }>05</option>
                	<option value="06" ${info.m=='06'?'selected':'' }>06</option>
                	<option value="07" ${info.m=='07'?'selected':'' }>07</option>
                	<option value="08" ${info.m=='08'?'selected':'' }>08</option>
                	<option value="09" ${info.m=='09'?'selected':'' }>09</option>
                	<option value="10" ${info.m=='10'?'selected':'' }>10</option>
                	<option value="11" ${info.m=='11'?'selected':'' }>11</option>
                	<option value="12" ${info.m=='12'?'selected':'' }>12</option>
                </select>
            </p>
            <p>
                <label>最低消费额：</label>
                <input name="floor" type="text" size="30" class="required" value="${info.floor }"/>
            </p>
             <p>
                <label>最高消费额：</label>
                <input name="ceil" type="text" size="30" class="required" value="${info.ceil }"/>
            </p>
             <p>
                <label>支付次数限制：</label>
                <input name="countLimit" type="text" size="30" class="required" value="${info.countLimit }"/>
            </p>
             <p>
                <label>币种：</label>
                <input name="currency" type="text" size="30" class="required" value="${info.currency }"/>
            </p>
             <p>
                <label>余额：</label>
                <input name="balance" type="text" size="30" class="required" value="${info.balance }"/>
            </p>
             <p>
                <label>有效：</label>
                <select name="enabled">
                	<option value="1" ${info.enabled=='1'?'selected':'' }>有效</option>
                	<option value="0" ${info.enabled=='0'?'selected':'' }>无效</option>
                </select>
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