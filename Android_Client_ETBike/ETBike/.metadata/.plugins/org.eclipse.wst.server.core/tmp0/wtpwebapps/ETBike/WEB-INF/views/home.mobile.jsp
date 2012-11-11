<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false" %>

<head>
<meta name="decorator" content="mobile_common">
</head>

<body>
<div data-role="page" id="homePage">
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

	<div data-role="content" id="homeContent">	
		<sec:authorize access="!isAuthenticated()">
			<form action="<c:url value="/signin/authenticate" />" method="post" data-ajax="false">
				<div data-role="fieldcontain">
					<label for="j_username">User ID</label>
					<input type="text" name="j_username" id="j_username" placeholder="User id" />
				</div>		
				<div data-role="fieldcontain">
					<label for="j_password">Password</label>
					<input type="password" name="j_password" id="j_password"  placeholder="Password" />
				</div>		
				<div><button type="submit">Sign in</button></div>
			</form>
			<form action="<c:url value="/signin/facebook"/>" method="POST" data-ajax="false">
		        <input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
				<div><button type="submit" data-theme="b">Sign in with Facebook</button></div>
			</form>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
	        <h1>Hello, world!</h1>
	        <p>This is a template for a simple marketing or informational website. It includes a large callout called the hero unit and three supporting pieces of content. Use it as a starting point to create something more unique.</p>
		</sec:authorize>
	</div>
</div>
</body>