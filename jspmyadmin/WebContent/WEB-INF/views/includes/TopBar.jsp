<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div style="padding: 0.2em 1.5em;">
	<div style="display: inline-block; padding: 0.1em 1em;">
		<img alt="Server" class="icon"
			src="${pageContext.request.contextPath}/components/icons/server.png">
		<m:print key="lbl.server" />
		: <a href="${pageContext.request.contextPath}/home"
			class="topbar-link">
			${pageContext.request.serverName}:${pageContext.request.serverPort}
			&#40;${applicationScope.hostname}&#41;</a>
	</div>
	<jma:notEmpty name="#session_db" scope="session">
		<div style="display: inline-block;">
			<img alt="Database" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
			<m:print key="lbl.database" />
			: <a href="${pageContext.request.contextPath}/database_structure"
				class="topbar-link">${sessionScope.session_db}</a>
		</div>
	</jma:notEmpty>
	<jma:notEmpty name="#session_table" scope="session">
		<div style="display: inline-block;">
			<img alt="Server" class="icon"
				src="${pageContext.request.contextPath}/components/icons/newspaper.png">
			<m:print key="lbl.table" />
			: <a href="${pageContext.request.contextPath}/table_data"
				class="topbar-link">${sessionScope.session_table}</a>
		</div>
	</jma:notEmpty>
</div>
<div id="left-menu-btn">
	<img alt="Pervious" class="icon"
		src="${pageContext.request.contextPath}/components/icons/arrow-88-24.png">
</div>
<div id="right-menu-btn">
	<img alt="Next" class="icon"
		src="${pageContext.request.contextPath}/components/icons/arrow-24-24.png">
</div>