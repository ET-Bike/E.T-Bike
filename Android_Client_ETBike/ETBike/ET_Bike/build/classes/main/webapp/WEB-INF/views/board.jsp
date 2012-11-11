<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false" %>

<head>
<meta name="decorator" content="web_board">
</head>

<body>
	<div class="row">
		<div class="span2 well affix">
			<ul id="sideMenu" class="nav nav-list" style="cursor:pointer">
				<c:forEach items="${boardCategories }" var="boardCategory" varStatus="status">
					<li class="menuItem"><a category="${boardCategory }">${boardCategory.description }</a></li>
					<c:if test="${not status.last }"><li class="divider"></li></c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="span8 offset3">
			<div id="boardsSection"><div id="topRow" style="display:none"></div></div>
			<div id="loadmoreajaxloader" style="display:none; text-align:center"><img src="<c:url value="/asset/img/loading2.gif" />"/></div>
		</div>
	</div>
</body>