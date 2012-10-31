<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false" %>

<c:set var="requestURI" value="${pageContext.request.requestURI }" />
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			  <span class="icon-bar"></span>
			  <span class="icon-bar"></span>
			  <span class="icon-bar"></span>
			</a>
			
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li><a class="brand" href="<c:url value="/"/>">E.T Bike</a></li>
					<sec:authorize access="isAuthenticated()">
						<li><a href="<c:url value="/board"/>">Board</a></li>
						<c:if test="${fn:contains(requestURI, '/board') }">
							<li><a href="#editor" id="editorBt" data-toggle="modal"><i class="icon-pencil icon-white"></i></a></li>
						</c:if>
					</sec:authorize>
				</ul>
				
				<sec:authorize access="!isAuthenticated()">
					<form class="navbar-form pull-right" action="<c:url value="/signin/facebook"/>" method="POST">
				        <input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
						<button type="submit" class="btn btn-info">Facebook</button>
					</form>&nbsp;
					<form class="navbar-form pull-right" action="<c:url value="/signin/authenticate" />" method="post">
						<input type="text" name="j_username" placeholder="User id" class="span2">
						<input type="password" name="j_password" placeholder="Password" class="span2">
						<button class="btn" type="submit">Sign in</button>
					</form>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<a href="<c:url value="/signout" />" class="btn btn-warning pull-right">Sign out</a>
				</sec:authorize>
			</div>
		</div>
	</div>
</div>
