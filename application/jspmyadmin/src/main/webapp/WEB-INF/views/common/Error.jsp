<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<style type="text/css">
html {
	font-size: ${sessionScope.fontsize}%;
}
</style>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">
<style type="text/css">
html {
	overflow: auto;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jquery.js"></script>
</head>
<body>
	<div style="width: 100%; text-align: center; margin-top: 50px;">
		<div style="width: 400px; margin-left: auto; margin-right: auto;">
			<img alt="Logo" id="site-logo"
				src="${pageContext.request.contextPath}/components/images/logo.png">
		</div>
	</div>
	<div style="text-align: center;">
		<b style="color: red;">MySql Error: </b>
		<span>${requestScope.mysql_error}</span>
	</div>
	<h2 align="center" style="color: red;">
		<m:print key="err.config1" />
		<m:print key="err.config2" />
		<a href="${pageContext.request.contextPath}/uninstall.html"> <m:print
				key="err.config3" />
		</a>
		<m:print key="err.config4" />
	</h2>
</body>
</html>