<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false" %>

<head>
<meta name="decorator" content="mobile_common">

<script src="<c:url value="/asset/libs/jquery/jquery.tmpl.min.js"/>"></script>
<script src="<c:url value="/asset/libs/jquery/jquery.form.js"/>"></script>
<script src="<c:url value="/asset/js/common.mobile.js"/>"></script>
<script src="<c:url value="/asset/js/templateLoader.mobile.js"/>"></script>
<script src="<c:url value="/asset/js/board/boardManager.mobile.js"/>"></script>
<script>
$(function(){
	$common.init({contextPath : '${pageContext.request.contextPath }', progressImgSrc : '<c:url value="/asset/img/loading2.gif" />'});
	templateLoader.init({templateUrl : '<c:url value="/asset/template/" />'});
	boardManager.init({
		url : '<c:url value="/boards"/>'
		, currentUserAccountId : ${empty userAccount.id ? 0 : userAccount.id }
	});
	
	<c:forEach items="${boardCategories }" var="boardCategory">
		boardManager.load('${boardCategory }',1,'#boardList_${boardCategory }');
	</c:forEach>
});
</script>
</head>

<body>
<div data-role="page" id="boardPage">
	<div data-role="header" data-position="fixed">
		<h1>E.T Bike</h1>	
		<sec:authorize access="isAuthenticated()">
			<a href="<c:url value="/signout" />" class="ui-btn-right" data-ajax="false">Sign out</a>
			<div data-role="navbar">
				<ul>
					<li><a href="<c:url value="/" />" data-ajax="false">Home</a></li>
					<li><a href="<c:url value="/board" />" data-ajax="false">Board</a></li>
				</ul>
			</div>
		</sec:authorize>
	</div>

	<div data-role="content" id="boardContent">	
		<ul data-role="listview">
			<c:forEach items="${boardCategories }" var="boardCategory">
				<li><a href="#boardPage_${boardCategory }">${boardCategory.description }</a></li>
			</c:forEach>
		</ul>
	</div>
</div>

<c:forEach items="${boardCategories }" var="boardCategory">
<div data-role="page" id="boardPage_${boardCategory }">
	<div data-role="header" data-position="fixed">
		<h1>${boardCategory.description }</h1>	
		<a data-rel="back">back</a>
	</div>

	<div data-role="content" id="boardContent_${boardCategory }">	
		<ul data-role="listview" id="boardList_${boardCategory }" class="boardList"></ul>
	</div>
</div>
</c:forEach>
</body>