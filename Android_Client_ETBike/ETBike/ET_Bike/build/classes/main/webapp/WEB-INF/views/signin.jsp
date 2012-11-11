<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<head>
<meta name="decorator" content="web_common">
</head>

<body>
<div class="well">
	<form class="form-horizontal" id="signin" action="<c:url value="/signin/authenticate" />" method="post">
		<input type="text" name="j_username" placeholder="input the user-id.." /><br/>
		<input type="password" name="j_password" placeholder="input the password.." /> 
		<button class="btn" type="submit">Sign In</button>
	</form>		

	<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST">
        <input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
		<input type="submit" class="btn btn-info" value="Sign-in with FaceBook" />
	</form>
</div>
</body>