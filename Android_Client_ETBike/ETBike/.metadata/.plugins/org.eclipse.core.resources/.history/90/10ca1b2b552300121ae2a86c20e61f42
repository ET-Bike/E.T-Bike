<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<head>
<meta name="decorator" content="web_common">
</head>

<body>
<div class="well">
	<h3>Sign Up</h3>
	<c:url value="/signup" var="signupUrl" />
	<form:form class="form-horizontal" id="signup" action="${signupUrl }" method="post" modelAttribute="signupForm">
		<div class="control-group">
			<label class="control-label" for="username">User ID</label>
			<div class="controls">
				<form:input path="username" placeholder="input the username.." />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="password">Password</label>
			<div class="controls">
				<form:password path="password" placeholder="Password (at least 6 characters) "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="firstName">First Name</label>
			<div class="controls">
				<form:input path="firstName" placeholder="input the firstName.." />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="lastName">Last Name</label>
			<div class="controls">
				<form:input path="lastName" placeholder="input the lastName.." />
			</div>
		</div>

		<div class="control-group">
			<div class="controls">
				<button class="btn" type="submit">Sign Up</button>
			</div>
		</div>
	</form:form>
</div>
</body>