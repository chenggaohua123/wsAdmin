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
<title>权限与角色关联</title>
</head>
<body>
<form method="post" action="<%=path %>/sysmgr/saveAddMenuToRole" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="roleId" value="${roleId }"/>
        <div class="pageFormContent" layoutH="56">
			<div class="accordion" fillSpace="sideBar">
				<div class="accordionContent">
					<ul class="tree treeFolder treeCheck expand" oncheck="kkk">
						<c:forEach items="${menuList }" var="menu">
							<li>
								<c:if test="${menu.parentNo == 0}">
									
									<a tname="menuNo" tvalue="${ menu.menuNo}" 
										<c:forEach items="${userMenuList }" var="userMenu">
											<c:if test="${userMenu.menuNo == menu.menuNo}">
												checked="true"
											</c:if>
										</c:forEach>
									>${menu.menuName }(${menu2.menuType == '1'?'菜单':'权限' })</a>
										<ul>
											<c:forEach items="${menuList }" var="menu1">
											    <c:if test="${menu1.parentNo == menu.menuNo }">
													<li><a tname="menuNo" tvalue="${ menu1.menuNo}"
														<c:forEach items="${userMenuList }" var="userMenu">
															<c:if test="${userMenu.menuNo == menu1.menuNo}">
																checked="true"
															</c:if>
														</c:forEach>
													>${menu1.menuName}(${menu1.menuType == '1'?'菜单':'权限' })</a>
														<c:forEach items="${menuList }" var="menu2">
															<c:if test="${menu1.menuNo == menu2.parentNo }">
																<ul>
																	<li><a tname="menuNo" tvalue="${ menu2.menuNo}"
																		<c:forEach items="${userMenuList }" var="userMenu">
																			<c:if test="${userMenu.menuNo == menu2.menuNo}">
																				checked="true"
																			</c:if>
																		</c:forEach>
																	>${menu2.menuName}(${menu2.menuType == '1'?'菜单':'权限' })</a>
																		
																	</li>
																</ul>
															</c:if>
														</c:forEach>
													</li>
												</c:if>
											</c:forEach>
											
										</ul>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
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
</body>
</html>