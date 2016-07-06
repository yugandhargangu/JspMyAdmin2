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
		<li><a href="${pageContext.request.contextPath}/table_data"><img
				alt="${lbl_table_data}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				${lbl_table_data}</a></li>
		<li><a href="${pageContext.request.contextPath}/table_structure"><img
				alt="${lbl_structure}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_structure}</a></li>
		<li><a href="#"><img alt="Foreign Keys" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				Foreign Keys</a></li>
		<li><a href="#"><img alt="Partitioning" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				Partitioning</a></li>
		<li><a href="#"><img alt="${lbl_sql}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_sql}</a></li>
		<li><a href="#"><img alt="Insert" class="icon"
				src="${pageContext.request.contextPath}/components/icons/statistics.png">
				Insert</a></li>
		<li><a href="${pageContext.request.contextPath}/table_info"><img
				alt="${lbl_information}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				${lbl_information}</a></li>
		<li><a href="#"><img alt="Export" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				Export</a></li>
		<li><a href="#"><img alt="Import" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				Import</a></li>
		<li><a href="#"><img alt="Triggers" class="icon"
				src="${pageContext.request.contextPath}/components/icons/code.png">
				Triggers</a></li>
		<li><a href="#"><img alt="Privileges" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				Privileges</a></li>
		<li><a href="#"><img alt="Search" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				Search</a></li>
		<li><a href="#"><img alt="Options" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				Options</a></li>
	</ul>
</div>