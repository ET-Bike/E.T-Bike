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
<style>
.alert.reply{margin-bottom:2px; padding: 4px 35px 4px 14px;}
.pager.reply{margin: 5px 0;}
</style>

<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/js/bootstrap.min.js"></script>
<script src="<c:url value="/asset/libs/jquery/jquery.tmpl.min.js"/>"></script>
<script src="<c:url value="/asset/libs/jquery/jquery.form.js"/>"></script>
<script src="<c:url value="/asset/libs/ckeditor/ckeditor.js" />"></script>

<script src="<c:url value="/asset/js/common.js"/>"></script>
<script src="<c:url value="/asset/js/templateLoader.js"/>"></script>
<script src="<c:url value="/asset/js/board/boardManager.js"/>"></script>
<script src="<c:url value="/asset/js/board/replyManager.js"/>"></script>

<script>
$(function(){
	$common.init({contextPath : '${pageContext.request.contextPath }', progressImgSrc : '<c:url value="/asset/img/loading2.gif" />'});
	templateLoader.init({templateUrl : '<c:url value="/asset/template/" />'});
	boardManager.init({
		url : '<c:url value="/boards"/>'
		, imageUploadUrl : '<c:url value="/upload/img"/>'
		, currentUserAccountId : ${empty userAccount.id ? 0 : userAccount.id }
		, replyManager : replyManager
	});
	replyManager.init({
		url : '<c:url value="/replies"/>'
		, currentUserAccountId : ${empty userAccount.id ? 0 : userAccount.id }
		, currentUsername : '${empty userAccount.username ? '' : userAccount.username }'
	});
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

<div id="editor" class="modal hide fade">
	<div class="modal-header">
		<a id="btEditorClose" class="close" data-dismiss="modal">&times;</a>
		<h3>Edit</h3>
	</div>
	<div class="modal-body">
		<form class="form-horizontal">
			<input type="hidden" id="boardId" value="0" />
			<input type="hidden" id="writer" value="${userAccount.username }" />
			<input type="text" name="title" id="title" placeholder="글제목을 입력하세요..." /><br/>
			<textArea name="content" id="contentEditor" placeholder="내용을 입력하세요..."></textArea><br/>
			<a class="btn btn-primary" id="saveBt">저장</a>
		</form>
	</div>
</div>
</body>
</html>
