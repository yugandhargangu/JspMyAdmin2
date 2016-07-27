<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<m:store name="lbl_tables" key="lbl.tables" />
		<li><a
			href="${pageContext.request.contextPath}/database_structure.html"><img
				alt="${lbl_tables}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				${lbl_tables} </a></li>
		<m:store name="lbl_views" key="lbl.views" />
		<li><a
			href="${pageContext.request.contextPath}/database_view_list.html"><img
				alt="${lbl_views}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/newspaper.png">
				${lbl_views} </a></li>
		<m:store name="lbl_procedures" key="lbl.procedures" />
		<li><a
			href="${pageContext.request.contextPath}/database_procedures.html"><img
				alt="${lbl_procedures}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/historical.png">
				${lbl_procedures}</a></li>
		<m:store name="lbl_functions" key="lbl.functions" />
		<li><a
			href="${pageContext.request.contextPath}/database_functions.html"><img
				alt="${lbl_functions}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/historical.png">
				${lbl_functions}</a></li>
		<m:store name="lbl_events" key="lbl.events" />
		<li><a
			href="${pageContext.request.contextPath}/database_events.html"><img
				alt="${lbl_events}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/time.png">
				${lbl_events}</a></li>
		<m:store name="lbl_triggers" key="lbl.triggers" />
		<li><a
			href="${pageContext.request.contextPath}/database_triggers.html"><img
				alt="${lbl_triggers}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/index.png">
				${lbl_triggers}</a></li>
		<m:store name="lbl_query_editor" key="lbl.query_editor" />
		<li><a
			href="${pageContext.request.contextPath}/database_sql.html"><img
				alt="${lbl_query_editor}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_query_editor}</a></li>
		<m:store name="lbl_export" key="lbl.export" />
		<li><a href="#"><img alt="${lbl_export}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				${lbl_export}</a></li>
		<m:store name="lbl_import" key="lbl.import" />
		<li><a href="#"><img alt="${lbl_import}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				${lbl_import}</a></li>
		<m:store name="lbl_users_privileges" key="lbl.users_privileges" />
		<li><a
			href="${pageContext.request.contextPath}/database_privileges.html"><img
				alt="${lbl_users_privileges}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				${lbl_users_privileges}</a></li>
	</ul>
</div>