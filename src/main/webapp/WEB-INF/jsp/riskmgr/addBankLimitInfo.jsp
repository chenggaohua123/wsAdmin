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
<title>Insert title here</title>
</head>

<script type="text/javascript">
	function showCountrys(){
		window.location.href='<%=path %>/riskmgr/queryCountryList?countrys='+$("#countryNameSimple").val();
	}
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/riskmgr/addBankLimitInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>商户号：</label>
                <input name="merNo" type="text" id="merNo" size="30" class="required number" readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/ratemgr/getTerListBack?type=exceptionType" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
            </p>
             <p>
                <label>终端号：</label>
                <input name="terNo"  id="terNo" type="text" size="30" class="required number" />
            </p>
            <p>
                <label>银行名称：</label>
                <select class="combox" selectedValue="0"
								loadurl="<%=path %>/ratemgr/queryBankInfoList"
								relValue="bankId" relText="bankName" name="bankId">
				</select>
            </p>
            <p>
               <label>国家：</label>
                
                                <input name="countrys" type="text" id="countryNameSimple" size="30"  readonly="readonly"/>
                 <a class="btnLook" href="<%=path %>/riskmgr/queryCountryList" width="1100" height="350"  rel="countryNameSimple" mask="true" lookupGroup="" lookupPk="countryNameSimple">查找带回</a>
            </p>
            <p>
           	 <label>卡种：</label>
				<select name="cardType" class="combox">
					<option value="0" ${param.ischecked=='0'?'selected':'' }>所有</option>
					<option value="visa" <c:if test="${param.cardType=='visa'}">selected</c:if>>VISA</option>
					<option value="master" <c:if test="${param.cardType=='master'}">selected</c:if>>Master</option>
					<option value="jcb" <c:if test="${param.cardType=='jcb'}">selected</c:if>>JCB</option>
				</select>
            </p>
              <p>
           	 <label>限制条件：</label>
				<select name="enabled" class="combox">
					<option value="0" <c:if test="${param.enabled=='0'}">selected</c:if>>不限制</option>
					<option value="1" <c:if test="${param.enabled=='1'}">selected</c:if>>限制</option>
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