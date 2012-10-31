<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<head>
<meta name="decorator" content="mobile_common">
</head>

<body>
<div data-role="page" id="signInPage">
	<div data-role="header" data-position="fixed">
		<h1>E.T Bike</h1>
	</div>

	<div data-role="content" id="signInContent">	
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
	</div>
</div>
</body>