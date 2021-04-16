<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="funcs" uri="funcs"%>    
<%
	String path = request.getContextPath();
%>     
<!DOCTYPE html PUBLIC "-//W3C//Dlabel HTML 4.01 pansitional//EN" "http://www.w3.org/p/html4/loose.dlabel">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户详细信息</title>
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
                	$("#"+div+" img").attr('src',path);
                	a.isClick = "ok";
               }
            });
    	}
    }
</script>
</head>
<body>
<div class="pageContent" layoutH="0">
				<div class="tabs" currentIndex="0" eventType="click" style="width:100%">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
							   <li><span>基本信息</span></li>
								 <c:forEach  items="${userInfo.list }" var="type" varStatus="status">
								  <li><a href="javascript:void(0);" onclick="queryAjax('${userInfo.phoneNo}','${type.picType}','div${status.index+1}',this);"><span> ${funcs:getStringColumnKey('PIC_TYPE',type.picType ,'未知状态')}</span></a></li>
							   </c:forEach>
							</ul>
						</div>
					</div>
					<div class="tabsContent pageFormContent" >
					    <div>
						         <dl>
						               <dt>用户名</dt>
									   <dd>${userInfo.userName }</dd>
								</dl>
								<dl>	   
									   <dt>真实姓名</dt>
									   <dd>${userInfo.realName }</dd>
								 </dl> 
						         <dl>
						               <dt>邮箱</dt>
									   <dd>${userInfo.email }</dd>
								</dl> 
						         <dl>	   
									   <dt>电话</dt>
									   <dd>${userInfo.phoneNo}</dd>
						        </dl> 
						         <dl>
						               <dt>登陆方式</dt>
									   <dd>${funcs:getStringColumnKey('VERTION',userInfo.verificationType,'未知状态')}</dd>
								</dl> 
						         <dl>	   
									   <dt>创建时间</dt>
									   <dd>${funcs:formatDate(userInfo.createTime,'yyyy-MM-dd HH:mm')}</dd>
						          </dl> 
						         <dl>
						               <dt>创建人</dt>
									   <dd>${userInfo.createBy}</dd>
								</dl> 
						         <dl>	   
									   <dt>地址</dt>
									   <dd>${userInfo.address}</dd>
						          </dl> 
						         <dl>
						              <dt>是否关闭</dt>
									   <dd>${funcs:getStringColumnKey('USERSTATUS',userInfo.enabled,'未知状态')}</dd>
								</dl> 
						         <dl>	   
									   <dt>所属系统</dt>
									   <dd>${funcs:getStringColumnKey('SYSTEMID',userInfo.systemId,'未知状态')}</dd>
						         </dl> 
						         <dl>	   
									   <dt>身份证号码</dt>
									   <dd>${userInfo.IDCardNo }</dd>
						         </dl> 
						         <dl>
						               <dt>公司名称</dt>
									   <dd>${userInfo.comName }</dd>
								</dl> 
						         <dl>	   
									   <dt>公司地址</dt>
									   <dd>${userInfo.comAdress }</dd>
						         </dl> 
						         <dl>
						               <dt>营业开始时间</dt>
									   <dd>${userInfo.busTimeStart }</dd>
								</dl> 
						         <dl>	   
									   <dt>营业结束时间</dt>
									   <dd>${userInfo.busTimeEnd }</dd>
						          </dl> 
						         <dl>
						               <dt>单笔最大交易金额</dt>
									   <dd>${userInfo.maxTransAmount }</dd>
						        </dl>
						        <dl>
						               <dt>动态密钥</dt>
									   <dd>${userInfo.seed }</dd>
								</dl>
						        <dl></dl>
						         <dl>
						               <dt>google身份验证密钥</dt>
									   <dd>
									   		<c:if test="${userInfo.verificationCode != null }">
												${userInfo.verificationCode }
											</c:if>
									   		<c:if test="${userInfo.verificationCode == null }">
												暂无
											</c:if>
									   </dd>
						        </dl>
						        <dl></dl>
						        <%-- <dl>
						               <dt>google身份验证器二维码 </dt>
									   <dd><img src="<%=path %>/dwz/chart/qr/qr_${qrName}.png"  onerror="this.src='<%=path %>/dwz/chart/qr/qr_logo.png'" width="100px" ></dd>
						        </dl> --%>
						</div>
						<c:forEach  items="${userInfo.list }" var="type1" varStatus="status">
							<div id="div${status.index+1}"><img src="" ></div>
					   </c:forEach>
					</div>
           
	</div>
</div>
</body>
</html>