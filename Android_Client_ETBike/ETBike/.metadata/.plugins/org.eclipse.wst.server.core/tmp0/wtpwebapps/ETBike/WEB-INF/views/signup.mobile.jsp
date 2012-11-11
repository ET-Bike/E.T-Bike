<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<head>
<meta name="decorator" content="mobile_common">
</head>

<body>
<div data-role="page" id="signUpPage">
	<div data-role="header" data-position="fixed">
		<h1>E.T Bike</h1>
	</div>
	
	<div data-role="content" id="signUpContent">
		<c:url value="/signup" var="signupUrl" />
		<form:form action="${signupUrl }" method="post" modelAttribute="signupForm" data-ajax="false">
			<div data-role="fieldcontain">
				<label for="username">User ID</label>
				<form:input path="username" placeholder="input the username.." />
			</div>
			
			<div data-role="fieldcontain">
				<label for="password">Password</label>
				<form:password path="password" placeholder="Password (at least 6 characters) "/>
			</div>
				
			<div data-role="fieldcontain">
				<label class="control-label" for="firstName">First Name</label>
				<form:input path="firstName" placeholder="input the firstName.." />
			</div>
		
			<div data-role="fieldcontain">
				<label class="control-label" for="lastName">Last Name</label>
				<form:input path="lastName" placeholder="input the lastName.." />
			</div>
				
			<div><button type="submit" data-theme="b">Submit</button></div>
		</form:form>
	</div>
</div>
</body>