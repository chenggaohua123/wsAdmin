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
<title>添加商户终端信息</title>
</head>
<script type="text/javascript">
    function queryAjax(phoneNo,picType,div,a){
      if(a.isClick!="ok"){
    	$.ajax({
            type: "POST",
            url: "<%=path %>/merchantmgr/queryPicImg?phoneNo="+phoneNo+"&picType="+picType,
            async: false, //设为false就是同步请求
            cache: false,
            success: function (msg) {
            	var path="data:image/"+msg.picInfo.picExtName+";base64,"+msg.picInfo.picBuffer;
            	//$("#imgId").attr('src',path);
            	$("#"+div+" img").attr('src',path);
           }
        });
      }
    }
    
    function selectAddUpdateMerchentTerInfoChecked(){ 
    	var str=$("textarea[name='transCurrency']").val(); 
		if ($("#selectAddUpdateMerchentTerInfo").attr("checked")) {  
	    	$("input[name='transCurrency1']").each(function(){
		        $(this).attr("checked", true); 
		        if(str.indexOf($(this).val())<0){
		        	if(str==''){
		    			str+=$(this).val();
		    		}else{
		    			str+=','+$(this).val();
		    		}
	    		}
	    	});
	    } else {  
	    	$("input[name='transCurrency1']").each(function(){
		        $(this).attr("checked", false);  
		        if(str.indexOf($(this).val())>=0){
		        	if(str.indexOf($(this).val())>=0&&str.indexOf($(this).val())<=2){
		    			str=str.replace($(this).val()+',','');
		    		}else if(str.indexOf($(this).val())>2){
		    			str=str.replace(','+$(this).val(),'');
		    		}
	    		}
	    	});
	    }
		$("textarea[name='transCurrency']").val(str);
	} 
    
    $(document).ready(function(){
    	var sourceCurrencyCode = "${terInfo.sourceCurrencyCode }";
    	var transCurrency1 = "${terInfo.transCurrency }";
    	var source = sourceCurrencyCode.split(",");
    	var currency = transCurrency1.split(",");
    	var str = '<p><input type="checkbox" id="selectAddUpdateMerchentTerInfo" onclick="selectAddUpdateMerchentTerInfoChecked()" value="" >全选&nbsp;';
    	for(var i=0;i<source.length;i++){
    		if(0 == (i+1)%7){
    			str += '</p><p>'
    		}
    		var flag = false;
    		for(var y=0;y<currency.length;y++){
    			if(currency[y] == source[i]){
    				flag = true;
    			}
    		}
    		if(flag){
	    		str += '&nbsp; <input type="checkbox" onclick="setValue(this)" checked="checked" name="transCurrency1"value="'+source[i]+'">'+source[i]
    		}else{
	    		str += '&nbsp; <input type="checkbox" onclick="setValue(this)" name="transCurrency1"value="'+source[i]+'">'+source[i]
    		}
    	}
    	str += '</p>';
    	$("#updateMerchentTerInfo").html(str);
    	
    });
    
    function setValue(cb){
    	var str=$("textarea[name='transCurrency']").val(); 
    	if(cb.checked){
    		if(str==''){
    			str+=$(cb).val();
    		}else{
    			str+=','+$(cb).val();
    		}
    	}else{
    		if(str.indexOf($(cb).val())>=0&&str.indexOf($(cb).val())<=2){
    			str=str.replace($(cb).val()+',','');
    		}else if(str.indexOf($(cb).val())>2){
    			str=str.replace(','+$(cb).val(),'');
    		}
    	}
 		$("textarea[name='transCurrency']").val(str);
    }
    
</script>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/addTerInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
           <p>
                <label>商户号：</label>
                <input name="merNo" type="text" size="30" class="required number" value="${merchantInfo.merNo}" readonly="readonly"/>
            </p>
            <p>
                <label>终端名称：</label>
                <input name="terName" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>交易币种：</label>
               <textarea name="transCurrency"  rows="2" cols="30" >${terInfo.transCurrency }</textarea>
               </p>
               <p><p>
              <p> 
	          <div id="updateMerchentTerInfo" style="margin-left:140px;">
	           	</div>
           	 </p>
            <p>
                <label>结算币种：</label>
                <input name="settleCurrency" type="text" size="30" class="required" value="CNY"/>
            </p>
            <p>
                <label>收款账户名：</label>
                <input name="accountName" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>收款银行：</label>
                <input name="accountAddress" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>收款账号：</label>
                <input name="accountNo" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>账户国家：</label>
                <input name="accountContryCode" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>账户省份：</label>
                <input name="accountState" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>账户城市：</label>
                <input name="accountCity" type="text" size="30" class="required"/>
            </p>
            <p>
                <label>品牌限制：</label>
               <input type="radio"  name="brandStatus" value="1" checked="checked">限制<input type="radio" name="brandStatus" value="0" >不限制
            </p>
             <p>
                <label>备注：</label>
                <textarea name="remark" cols="22" rows="2"></textarea>
            </p>
            <p>
                <label>商户地址：</label>
                <input name="merchantAddress"   type="text" value=""  size="30" class=""/>
            </p>
            <p>
                <label>SWIFT：</label>
                <input name="swift"   type="text" value=""  size="30" class=""/>
            </p>
            <p>
                <label>银行地址：</label>
                <input name="bankAddress"   type="text" value=""  size="30" class=""/>
            </p>
            <p>
                <label>银行行号：</label>
                <input name="bankNo" type="text" size="30" class="required"/>
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