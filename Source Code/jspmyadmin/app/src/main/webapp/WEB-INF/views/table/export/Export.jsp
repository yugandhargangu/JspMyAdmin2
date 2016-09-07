<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../includes/Head.jsp" />
<style type="text/css">
.gap-item {
	margin-right: 2em;
	display: inline-block;
}

.div-inline-item {
	display: inline-block;
	width: auto;
	margin-top: 0.5em;
	vertical-align: top;
	margin-left: 3em;
}

.div-inline-item h4 {
	padding-bottom: 1em;
}

.div-inline-item div {
	padding: 0.5em 0 0.5em 1em;
}

#footer {
	margin-top: 0px;
}
</style>
</head>
<body>
	<div id="content">
		<div id="sidebar" style="width: 20%;">
			<jsp:include page="../../includes/SideBar.jsp" />
		</div>
		<div id="main-content" style="width: calc(80% - 3px);">
			<div id="topbar">
				<jsp:include page="../../includes/TopBar.jsp" />
			</div>
			<div id="header-div"><jsp:include page="../Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.1em 0.2em;">
					<div class="page-head">
						<h3>Table Export</h3>
					</div>
					<form action="${pageContext.request.contextPath}/table_export.html"
						accept-charset="utf-8" method="post" id="export-form">
						<input type="hidden" name="request_db"
							value="${requestScope.command.request_db}"> <input
							type="hidden" name="request_table"
							value="${requestScope.command.request_table}"> <input
							type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">Export Information</div>
							<div class="group-widget group-content">
								<div class="div-inline-item">
									<h4>Select Columns</h4>
									<div>
										<select multiple="multiple" name="column_list"
											class="form-control" id="column_list" size="20">
											<jma:notEmpty name="#column_list" scope="command">
												<jma:forLoop items="#column_list" name="column"
													scope="command">
													<option selected="selected" value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
								</div>
								<div class="div-inline-item">
									<h4>Options</h4>
									<br>
									<div class="form-input">
										<label>File Name</label><input type="text" name="filename"
											class="form-control" value="${requestScope.command.filename}">
									</div>
									<br>
									<div class="form-input">
										<label>Export Type</label> <select name="export_type"
											class="form-control">
											<jma:notEmpty name="#type_list" scope="command">
												<jma:forLoop items="#type_list" name="export_type"
													scope="command">
													<option value="${export_type}">${export_type}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="submit" class="btn" id="btn-run">
									<m:print key="lbl.run" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#header-menu li:nth-child(8)").addClass('active');
	</script>
</body>
</html>