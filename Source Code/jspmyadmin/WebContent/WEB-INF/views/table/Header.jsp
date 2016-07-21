<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<m:store name="lbl_table_data" key="lbl.table_data" />
		<m:store name="lbl_structure" key="lbl.structure" />
		<m:store name="lbl_sql" key="lbl.sql" />
		<m:store name="lbl_information" key="lbl.information" />
		<m:store name="lbl_foreign_keys" key="lbl.foreign_keys" />
		<li><a href="${pageContext.request.contextPath}/table_data.html"><img
				alt="${lbl_table_data}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				${lbl_table_data}</a></li>
		<li><a
			href="${pageContext.request.contextPath}/table_structure.html"><img
				alt="${lbl_structure}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_structure}</a></li>
		<li><a title="${lbl_foreign_keys}"
			href="${pageContext.request.contextPath}/table_foreign_keys.html"><img
				alt="Foreign Keys" class="icon" title="${lbl_foreign_keys}"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				${lbl_foreign_keys}</a></li>
		<li><a
			href="${pageContext.request.contextPath}/table_partitions.html"><img
				alt="Partitioning" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				Partitioning</a></li>
		<li><a href="${pageContext.request.contextPath}/table_sql.html"><img
				alt="${lbl_sql}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_sql}</a></li>
		<li><a
			href="${pageContext.request.contextPath}/table_insert_update.html"><img
				alt="Insert" class="icon"
				src="${pageContext.request.contextPath}/components/icons/statistics.png">
				Insert</a></li>
		<li><a href="${pageContext.request.contextPath}/table_info.html"><img
				alt="${lbl_information}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				${lbl_information}</a></li>
		<li><a href="#"><img alt="Export" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				Export</a></li>
		<li><a href="#"><img alt="Import" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				Import</a></li>
		<li><a
			href="${pageContext.request.contextPath}/table_maintenance.html"><img
				alt="Maintenance" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				Maintenance</a></li>
		<li><a href="#"><img alt="Privileges" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				Privileges</a></li>
	</ul>
</div>