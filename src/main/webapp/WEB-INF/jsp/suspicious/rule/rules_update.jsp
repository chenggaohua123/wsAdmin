<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Update Rules</title>
</head>
<body>
<div class="pageContent">
	 <form method="post" action="suspicious/updateRules" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	    <div class="pageFormContent" layoutH="56">
	         <p>
	             <label>规则名称：</label>
	             <input name="ruleId" value="${info.ruleId }" type="hidden"> 
	             <input name="ruleName" value="${info.ruleName }" type="text" size="30" class="required"/>
	         </p>
	         <p>
	             <label>来源参数：</label>
	               <input type="hidden" id="paramId" name="paramId"  value="${info.paramId }">
	             <input name="paramDescName" id="paramDescName"  readonly="readonly" type="text"  value="${info.paramDescName }"  size="30" class="required"/>
	             <a class="btnLook" href="suspicious/queryParamList?comFrom=1" target="dialog" lookupGroup="" lookupPk="paramDescName"  mask="true"  width="650" height="400">查找带回</a>		
	         </p>
	         <p>
	             <label>匹配方式：</label>
	             <input type="hidden" name="processClassId" id="processClassId" value="${info.processClassId }">
	             <input name="processClassName" id="processClassName" readonly="readonly" value="${info.processClassName }" type="text" size="30" class="required" suggestFields="processClassId,processClassName" suggestUrl="suspicious/queryProcessClassList"/>
	        
	         </p>
	         <p>
	             <label>匹配参数：</label>
	            <input type="hidden" name="ruleParamValueId" id="ruleParamValueId" value="${info.ruleParamValueId }">
	             <input name="ruleParamValueDescName" readonly="readonly" value="${info.ruleParamValueDescName }"  type="text" size="30" class="required" id="ruleParamValueDescName"/>
	             <a class="btnLook" href="suspicious/queryRuleParamList" target="dialog" lookupGroup="" lookupPk="ruleParamValueDescName"  mask="true"  width="650" height="400">查找带回</a>
	         </p>
	         <p>
	             <label>状态：</label>
	             <select name="status" class="required combox">
			           <option value="1" ${info.status == '1' ? "selected" : ""}>有效</option>
			           <option value="0" ${info.status == '0' ? "selected" : ""}>无效</option>
			     </select>
	         </p>
	         <!-- 
	         <label>状态：</label>
	             <select name="action" class="required combox">
			           <option value="2" ${info.action == '2'?'selected':'' }>拒绝交易</option>
			           <option value="1" ${info.action == '1'?'selected':'' }>接受交易</option>
			           <option value="3" ${info.action == '3'?'selected':'' }>Review</option>
			     </select>
	          -->
	     </div>
	     
	     <div class="formBar">
	         <ul>
	             <li>
	             	<div class="buttonActive"><div class="buttonConte nt"><button type="submit">保存</button></div></div>
	             </li>
	             <li>
	                 <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
	             </li>
	         </ul>
	     </div>
	 </form>
	</div>
</body>
</html>