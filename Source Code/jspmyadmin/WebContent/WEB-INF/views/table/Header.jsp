<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<m:store name="lbl_table_data" key="lbl.table_data" />
		<li><a href="${pageContext.request.contextPath}/table_data.html"><img
				alt="${lbl_table_data}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				${lbl_table_data}</a></li>
		<m:store name="lbl_structure" key="lbl.structure" />
		<li><a
			href="${pageContext.request.contextPath}/table_structure.html"><img
				alt="${lbl_structure}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/structure.png">
				${lbl_structure}</a></li>
		<m:store name="lbl_foreign_keys" key="lbl.foreign_keys" />
		<li><a title="${lbl_foreign_keys}"
			href="${pageContext.request.contextPath}/table_foreign_keys.html"><img
				alt="${lbl_foreign_keys}" class="icon" title="${lbl_foreign_keys}"
				src="${pageContext.request.contextPath}/components/icons/link.png">
				${lbl_foreign_keys}</a></li>
		<m:store name="lbl_partitioning" key="lbl.partitioning" />
		<li><a
			href="${pageContext.request.contextPath}/table_partitions.html"><img
				alt="${lbl_partitioning}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/split.png">
				${lbl_partitioning} </a></li>
		<m:store name="lbl_query_editor" key="lbl.query_editor" />
		<li><a href="${pageContext.request.contextPath}/table_sql.html"><img
				alt="${lbl_query_editor}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_query_editor} </a></li>
		<m:store name="lbl_insert_data" key="lbl.insert_data" />
		<li><a
			href="${pageContext.request.contextPath}/table_insert_update.html"><img
				alt="${lbl_insert_data}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/add-list-g.png">
				${lbl_insert_data}</a></li>
		<m:store name="lbl_information" key="lbl.information" />
		<li><a href="${pageContext.request.contextPath}/table_info.html"><img
				alt="${lbl_information}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/info.png">
				${lbl_information}</a></li>
		<m:store name="lbl_export" key="lbl.export" />
		<li><a href="#"><img alt="${lbl_export}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				${lbl_export}</a></li>
		<m:store name="lbl_import" key="lbl.import" />
		<li><a
			href="${pageContext.request.contextPath}/table_import.html"><img
				alt="${lbl_import}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				${lbl_import} </a></li>
		<m:store name="lbl_maintenance" key="lbl.maintenance" />
		<li><a
			href="${pageContext.request.contextPath}/table_maintenance.html"><img
				alt="${lbl_maintenance}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/eye.png">
				${lbl_maintenance}</a></li>
	</ul>
</div>