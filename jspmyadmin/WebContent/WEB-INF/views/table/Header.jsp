<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<li><a href="${pageContext.request.contextPath}/table_data"><img
				alt="Data" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				Data</a></li>
		<li><a href="${pageContext.request.contextPath}/table_structure"><img
				alt="Structure" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				Structure</a></li>
		<li><a href="${pageContext.request.contextPath}/table_structure"><img
				alt="Alter Columns" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				Alter Columns</a></li>
		<li><a href="#"><img alt="SQL" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				SQL</a></li>
		<li><a href="#"><img alt="Status" class="icon"
				src="${pageContext.request.contextPath}/components/icons/statistics.png">
				Insert</a></li>
		<li><a href="#"><img alt="Users" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				Information</a></li>
		<li><a href="#"><img alt="Export" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				Export</a></li>
		<li><a href="#"><img alt="Import" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				Import</a></li>
		<li><a href="#"><img alt="Variables" class="icon"
				src="${pageContext.request.contextPath}/components/icons/code.png">
				Triggers</a></li>
		<li><a href="#"><img alt="Charsets" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				Privileges</a></li>
		<li><a href="#"><img alt="Plugins" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				Search</a></li>
	</ul>
</div>