<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ page session="false" %>

<!doctype html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">body {padding-top: 60px; padding-bottom: 40px;}</style>
<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/css/bootstrap-responsive.min.css" rel="stylesheet">


<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/js/bootstrap.min.js"></script>
<script src="<c:url value="/asset/libs/jquery/jquery.tmpl.min.js"/>"></script>
<script src="<c:url value="/asset/libs/jquery/jquery.form.js"/>"></script>

<script src="<c:url value="/asset/js/common.js"/>"></script>
<script src="<c:url value="/asset/js/templateLoader.js"/>"></script>

<script>
$(function(){
	$common.init({contextPath : '${pageContext.request.contextPath }', progressImgSrc : '<c:url value="/asset/img/loading2.gif" />'});
	templateLoader.init({templateUrl : '<c:url value="/asset/template/" />'});
});
</script>
<title><decorator:title default="E.T Bike" /></title>
<decorator:head />

</head>
<body>

<jsp:include page="web_navigation.jsp" flush="true" />

<div class="container">
	<decorator:body />
</div>

</body>
</html>