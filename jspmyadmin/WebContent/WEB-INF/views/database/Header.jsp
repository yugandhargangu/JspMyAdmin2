<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<li><a
			href="${pageContext.request.contextPath}/database_structure"><img
				alt="Tables" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				Tables</a></li>
		<li><a
			href="${pageContext.request.contextPath}/database_view_list"><img
				alt="Views" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				Views</a></li>
		<li><a
			href="${pageContext.request.contextPath}/database_procedures"><img
				alt="Procedures" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-config.png">
				Procedures</a></li>
		<li><a
			href="${pageContext.request.contextPath}/database_functions"><img
				alt="Functions" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-config.png">
				Functions</a></li>
		<li><a href="${pageContext.request.contextPath}/database_events"><img
				alt="Plugins" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				Events</a></li>
		<li><a
			href="${pageContext.request.contextPath}/database_triggers"><img
				alt="Triggers" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				Triggers</a></li>
		<li><a href="#"><img alt="SQL" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				SQL</a></li>
		<li><a href="#"><img alt="Status" class="icon"
				src="${pageContext.request.contextPath}/components/icons/statistics.png">
				Search</a></li>
		<li><a href="#"><img alt="Users" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				Query</a></li>
		<li><a href="#"><img alt="Export" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				Export</a></li>
		<li><a href="#"><img alt="Import" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				Import</a></li>
		<li><a href="#"><img alt="Variables" class="icon"
				src="${pageContext.request.contextPath}/components/icons/code.png">
				Operations</a></li>
		<li><a href="#"><img alt="Charsets" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				Privileges</a></li>
	</ul>
</div>