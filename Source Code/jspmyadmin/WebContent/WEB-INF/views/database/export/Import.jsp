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
						<h3>Import SQL script file</h3>
					</div>
					<form
						action="${pageContext.request.contextPath}/database_import.html"
						accept-charset="utf-8" method="post" id="import-form"
						enctype="multipart/form-data">
						<input type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.query" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><input type="checkbox" name="disable_fks"
										value="1"> Disable Foreign Keys</label>
								</div>
								<div class="form-input">
									<label><input type="checkbox" name="continue_errors"
										value="1"> Continue Execution with Errors</label>
								</div>
								<div class="form-input">
									<label><input type="checkbox" name="import_to_db"
										value="1"> Use ${sessionScope.session_db} database</label>
								</div>
								<div class="form-input">
									<input type="file" name="import_file">
								</div>
								<jma:notEmpty name="#err_key" scope="command">
									<div class="form-input" style="color: red;">
										<m:print key="err_key" scope="command" />
									</div>
								</jma:notEmpty>
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
		$("#header-menu li:nth-child(6)").addClass('active');
		$(function() {
			$('#btn-run').click(function() {
				showWaiting();
			});
		});
	</script>
</body>
</html>