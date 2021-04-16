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
<style type="text/css">
.disfineTable {
	width: 100%;
}
.disfineTable tr td {
	padding-top: 5px;
	padding-bottom: 5px;
}
</style>
<title>添加商户拒付扣款规则信息</title>
</head>
<body>
<div class="pageContent">
	<form method="post" action="<%=path %>/merchantmgr/saveMerchantDisFineInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<font color='red'>
				注：1.开始值所填条数,在规则中为大于等于。
				<br/>
				<br/>  2.结束值所填条数,在规则中为小于。
				<br/>
				<br/>  3.收取金额所填金额,在规则中为商户拒付扣除金额
				<br/>
				<br/>  4.结束值为“∞”则表示无上限(无穷大),已给出默认填写框(不能删除)
		</font>
		<table class="disfineTable">
			<tr>
				<td>商户号</td>
				<td colspan="6">
					<input name="merNo" type="text" id="merNo" size="30" class="required" readonly="readonly" value="${info.merNo }"/>
                 	<a class="btnLook" href="<%=path %>/ratemgr/getTerListBack?type=exceptionType" width="1100" height="350"  rel="rateRelMerchant" mask="true" lookupGroup="" lookupPk="merNo">查找带回</a>
				</td>
			</tr>
			<tr>
				<td>终端号</td>
				<td colspan="6">
					<input name="terNo"  id="terNo" type="text" size="30" class="required number" readonly="readonly" value="${info.terNo }"/>
				</td>
			</tr>
			<tr>
				<td>币种</td>
				<td colspan="6">
					<input name="currency"  id="currency" type="text" size="30" class="required" readonly="readonly" value="${info.currency }"/>
				</td>
			</tr>
			
			<tr>
				<td>状态</td>
				<td colspan="6">
					<input type="hidden" name="id" id="id" value="${info.id }"/>
					<select name="enabled" id="enabled">
						<option ${info.enabled=='1'?'selected':'' } value="1">有效</option>
						<option ${info.enabled=='0'?'selected':'' } value="0">无效</option>
					</select>
				</td>
			</tr>
			
			<c:if test="${empty info.ruleList }">
				<tr del="false">
					<td>开始值:</td>
					<td>
						<input name="starts"  id="starts" type="text" size="6" class="required number"/>
					</td>
					<td>结束值:</td>
					<td>
						<input name="ends"  id="ends" type="text" size="6" class="required number" readonly="readonly" value="∞"/>
					</td>
					<td>收取金额:</td>
					<td>
						<input  name="amounts"  id="amounts" type="text" size="6" class="required"/>
					</td>
					<td>
						<a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="<%=path%>/dwz/uploadify/img/del.png"></a>&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="addElement(this)"><img alt="添加图片" src="<%=path%>/dwz/uploadify/img/add.png"></a>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty info.ruleList }">
				<c:forEach items="${info.ruleList }" var="rule">
					<tr del="${empty rule.end?false:true }">
						<td>开始值:</td>
						<td>
							<input type="hidden" name="ids" value="${rule.id }"/>
							<input name="starts"  id="starts" type="text" size="6" class="required number" value="${rule.start }"/>
						</td>
						<td>结束值:</td>
						<td>
							<input name="ends" id="ends" type="text" size="6" class="required number" ${empty rule.end?'readonly':'' } value="${empty rule.end?'∞':rule.end }"/>
						</td>
						<td>收取金额:</td>
						<td>
							<input  name="amounts"  id="amounts" type="text" size="6" class="required" value="${rule.amount }"/>
						</td>
						<td>
							<a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="<%=path%>/dwz/uploadify/img/del.png"></a>&nbsp;&nbsp;
							<a href="javascript:void(0)" onclick="addElement(this)"><img alt="添加图片" src="<%=path%>/dwz/uploadify/img/add.png"></a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
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
	<script type="text/javascript">
		function addElement(o){
			var elenet = '<tr><td>开始值:</td><td><input name="starts"  id="starts" type="text" size="6" class="required number"/></td><td>结束值:</td><td><input name="ends"  id="ends" type="text" size="6" class="required number"/></td><td>收取金额:</td><td><input  name="amounts"  id="amounts" type="text" size="6" class="required"/></td><td><a href="javascript:void(0)" onclick="deleteElement(this)"><img alt="删除图片" src="<%=path%>/dwz/uploadify/img/del.png"></a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="addElement(this)"><img alt="添加图片" src="<%=path%>/dwz/uploadify/img/add.png"></a></td></tr>';
			$(o).parent().parent().parent().append(elenet);
		}
		function deleteElement(o){
			var parent = $(o).parent().parent();
			var del = parent.attr('del');
			if((del!=null && del!=undefined) && del=="false" || del==false){
				alert("此元素不能删除");
			}else{
				parent.remove();
			}
		}
	</script>
</body>
</html>