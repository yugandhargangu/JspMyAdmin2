<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<m:open />
<div style="padding: 2px 1.5em;">
	<ul class="breadcrumb">
		<li><a ui-sref="home">${pageContext.request.serverName}:${pageContext.request.serverPort}&#40;${applicationScope.hostname}&#41;</a></li>
		<li ng-show="topbarIndex >= 1"><a href="${pageContext.request.contextPath}/database_structure.html?token=${requestScope.command.request_token}">${requestScope.command.request_db}</a></li>
		<li ng-show="topbarIndex >= 2"><a href="${pageContext.request.contextPath}/table_data.html?token=${requestScope.command.request_token}">${requestScope.command.request_table}</a></li>
		<li ng-show="topbarIndex === 3"><a href="${pageContext.request.contextPath}/view_data.html?token=${requestScope.command.request_token}">${requestScope.command.request_view}</a></li>
	</ul>
</div>
<div id="left-menu-btn">
	<img alt="Pervious" class="icon" src="${pageContext.request.contextPath}/components/icons/arrow-88-24.png">
</div>
<div id="right-menu-btn">
	<img alt="Next" class="icon" src="${pageContext.request.contextPath}/components/icons/arrow-24-24.png">
</div>