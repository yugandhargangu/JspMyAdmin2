<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div id="header-menu-container">
	<ul id="header-menu">
		<m:store name="lbl_databases" key="lbl.databases" />
		<li><a
			href="${pageContext.request.contextPath}/server_databases.html">
				<img alt="${lbl_databases}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-g.png">
				${lbl_databases}
		</a></li>
		<m:store name="lbl_query_editor" key="lbl.query_editor" />
		<li><a href="${pageContext.request.contextPath}/server_sql.html"><img
				alt="${lbl_query_editor}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-file.png">
				${lbl_query_editor} </a></li>
		<m:store name="lbl_server_status" key="lbl.server_status" />
		<li><a
			href="${pageContext.request.contextPath}/server_status.html"> <img
				alt="${lbl_server_status}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/statistics.png">
				${lbl_server_status}
		</a></li>
		<m:store name="lbl_users_privileges" key="lbl.users_privileges" />
		<li><a
			href="${pageContext.request.contextPath}/server_users.html"><img
				alt="${lbl_users_privileges}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/users.png">
				${lbl_users_privileges} </a></li>
		<m:store name="lbl_export" key="lbl.export" />
		<li><a href="#"><img alt="${lbl_export}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/send-file-g.png">
				${lbl_export} </a></li>
		<m:store name="lbl_import" key="lbl.import" />
		<li><a href="#"><img alt="${lbl_import}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/import-g.png">
				${lbl_import} </a></li>
		<m:store name="lbl_variables" key="lbl.variables" />
		<li><a
			href="${pageContext.request.contextPath}/server_variables.html">
				<img alt="${lbl_variables}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/code.png">
				${lbl_variables}
		</a></li>
		<m:store name="lbl_charsets" key="lbl.charsets" />
		<li><a
			href="${pageContext.request.contextPath}/server_charsets.html"> <img
				alt="${lbl_charsets}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/text-justify.png">
				${lbl_charsets}
		</a></li>
		<m:store name="lbl_engines" key="lbl.engines" />
		<li><a
			href="${pageContext.request.contextPath}/server_engines.html"> <img
				alt="${lbl_engines}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/database-config.png">
				${lbl_engines}
		</a></li>
		<m:store name="lbl_plugins" key="lbl.plugins" />
		<li><a
			href="${pageContext.request.contextPath}/server_plugins.html"> <img
				alt="${lbl_plugins}" class="icon"
				src="${pageContext.request.contextPath}/components/icons/puzzle-piece.png">
				${lbl_plugins}
		</a></li>
	</ul>
</div>