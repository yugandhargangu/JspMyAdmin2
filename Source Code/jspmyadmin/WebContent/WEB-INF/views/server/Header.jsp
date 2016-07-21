<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<li><a
			href="${pageContext.request.contextPath}/server_databases.html">
				<img alt="Databases" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				<m:print key="lbl.databases" />
		</a></li>
		<li><a href="${pageContext.request.contextPath}/server_sql.html"><img
				alt="SQL" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				<m:print key="lbl.sql" /></a></li>
		<li><a
			href="${pageContext.request.contextPath}/server_status.html"> <img
				alt="Status" class="icon"
				src="${pageContext.request.contextPath}/components/icons/statistics.png">
				<m:print key="lbl.status" />
		</a></li>
		<li><a
			href="${pageContext.request.contextPath}/server_users.html"><img
				alt="Users" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				<m:print key="lbl.users" /></a></li>
		<li><a href="#"><img alt="Export" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				<m:print key="lbl.export" /></a></li>
		<li><a href="#"><img alt="Import" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				<m:print key="lbl.import" /></a></li>
		<li><a
			href="${pageContext.request.contextPath}/server_variables.html">
				<img alt="Variables" class="icon"
				src="${pageContext.request.contextPath}/components/icons/code.png">
				<m:print key="lbl.variables" />
		</a></li>
		<li><a
			href="${pageContext.request.contextPath}/server_charsets.html"> <img
				alt="Charsets" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				<m:print key="lbl.charsets" />
		</a></li>
		<li><a
			href="${pageContext.request.contextPath}/server_engines.html"> <img
				alt="Engines" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-config.png">
				<m:print key="lbl.engines" />
		</a></li>
		<li><a
			href="${pageContext.request.contextPath}/server_plugins.html"> <img
				alt="Plugins" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				<m:print key="lbl.plugins" />
		</a></li>
	</ul>
</div>