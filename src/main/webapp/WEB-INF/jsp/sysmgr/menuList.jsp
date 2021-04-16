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
<title>菜单列表</title>
</head>
<body>
<%
	int leaf1 = 0;
	int leaf2 = 0;
%>
<div class="pageContent"  layoutH="0">
	<c:forEach items="${menuMap }" var="listMenu">
			<div style=" float:left; display:block; margin:10px; overflow:auto; width:300px; overflow:auto; line-height:21px; background:#FFF;">
			<p><c:if test="${listMenu.key == 1 }"><h2><a href="<%=path %>/sysmgr/goAddMenu?parentNo=0&systemId=1"  title ="添加菜单" mask="true" target="dialog">管理后台菜单</a></h2></c:if>
			<c:if test="${listMenu.key == 2 }"><h2><a href="<%=path %>/sysmgr/goAddMenu?parentNo=0&systemId=2"  title ="添加菜单" mask="true" target="dialog">代理商后台菜单</a></h2></c:if></p>
			<ul class="tree treeFolder expand">
				<c:forEach items="${listMenu.value }" var="menu">
					<li>
						<c:if test="${menu.parentNo == 0}">
							<a href="<%=path %>/sysmgr/goAddMenu?parentNo=${menu.menuNo}&systemId=${menu.systemId}" title ="添加菜单" mask="true" target="dialog">${menu.menuName }(${menu2.menuType == '1'?'菜单':'权限' })</a>
									<c:forEach items="${listMenu.value  }" var="menu1">
									    <c:if test="${menu1.parentNo == menu.menuNo }">
											<%
												leaf1++;
											%>
											<%=leaf1 == 1?"<ul>":"" %>
											<li><a href="<%=path %>/sysmgr/goAddMenu?parentNo=${menu1.menuNo}&systemId=${menu1.systemId}" title ="添加菜单"  mask="true" target="dialog">${menu1.menuName}(${menu1.menuType == '1'?'菜单':'权限' })</a>
												<c:forEach items="${listMenu.value  }" var="menu2">
													<c:if test="${menu1.menuNo == menu2.parentNo }">
														<%
															leaf2++;
														%>
														<%=leaf2 == 1?"<ul>":"" %>
															<li> 
																<a>${menu2.menuName}(${menu2.menuType == '1'?'菜单':'权限' })</a>
															</li>
													</c:if>
												</c:forEach>
												<%=leaf2 >= 1?"</ul>":"" %>
												<%leaf2=0; %>
											</li>
										</c:if>
									</c:forEach>
								<%=leaf1 >= 1?"</ul>":"" %>
								<%leaf1=0; %>
						</c:if>
					</li>
				</c:forEach>
			</ul>
			</div>
	</c:forEach>
</div>
</body>
</html>