<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<m:store name="lblview_data" key="lbl.view_data" />
		<li><a href="${pageContext.request.contextPath}/view_data.html?token=${requestScope.command.request_token}"><img
				alt="${lblview_data}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				${lblview_data}</a></li>
		<m:store name="lbl_structure" key="lbl.structure" />
		<li><a
			href="${pageContext.request.contextPath}/view_structure.html?token=${requestScope.command.request_token}"><img
				alt="${lbl_structure}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/structure.png">
				${lbl_structure}</a></li>
		<m:store name="lbl_query_editor" key="lbl.query_editor" />
		<li><a href="${pageContext.request.contextPath}/view_sql.html?token=${requestScope.command.request_token}"><img
				alt="${lbl_query_editor}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_query_editor} </a></li>
		<m:store name="lbl_export" key="lbl.export" />
		<li><a href="#"><img alt="${lbl_export}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				${lbl_export}</a></li>
		<m:store name="lbl_import" key="lbl.import" />
		<li><a href="${pageContext.request.contextPath}/view_import.html?token=${requestScope.command.request_token}"><img
				alt="${lbl_import}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				${lbl_import} </a></li>
	</ul>
</div>