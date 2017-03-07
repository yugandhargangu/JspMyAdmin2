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
						<h3>
							<m:print key="lbl.export" />
						</h3>
					</div>
					<form
						action="${pageContext.request.contextPath}/server_export.html"
						accept-charset="utf-8" method="post" id="export-form">
						<input type="hidden" name="token" id="token"
							value="${requestScope.command.token}">

						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.export_sql_file" />
							</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label><m:print key="lbl.select_databases" /></label> <select
										name="databases" id="databases" class="form-control"
										multiple="multiple" size="25">
										<jma:notEmpty name="#database_list" scope="command">
											<jma:forLoop items="#database_list" name="database"
												scope="command">
												<option value="${database}" selected="selected">${database}</option>
											</jma:forLoop>
										</jma:notEmpty>
									</select>
								</div>
								<div class="div-inline-item">
									<h4>
										<m:print key="lbl.structure_options" />
									</h4>
									<div>
										<label><input type="radio" name="export_type"
											value="0"> <m:print key="lbl.structure_only" /></label>
									</div>
									<div>
										<label><input type="radio" name="export_type"
											checked="checked" value="1"> <m:print
												key="lbl.structure_and_data" /></label>
									</div>
									<br>
									<div>
										<label><input type="checkbox" name="disable_fks"
											value="1"> <m:print
												key="lbl.disable_foreign_key_checks" /></label>
									</div>

									<br>
									<div class="form-input">
										<label><m:print key="lbl.file_name" /></label><input
											type="text" name="filename" class="form-control"
											value="${requestScope.command.filename}">
									</div>
								</div>
								<div class="div-inline-item">
									<h4>
										<m:print key="lbl.include_options" />
									</h4>
									<div>
										<label><input type="checkbox" name="include_views"
											checked="checked" value="1"> <m:print
												key="lbl.include" /> VIEWS</label>
									</div>
									<div>
										<label><input type="checkbox"
											name="include_procedures" checked="checked" value="1">
											<m:print key="lbl.include" /> PROCEDURES</label>
									</div>
									<div>
										<label><input type="checkbox" name="include_functions"
											checked="checked" value="1"> <m:print
												key="lbl.include" /> FUNCTIONS</label>
									</div>
									<div>
										<label><input type="checkbox" name="include_events"
											checked="checked" value="1"> <m:print
												key="lbl.include" /> EVENTS</label>
									</div>
									<div>
										<label><input type="checkbox" name="include_triggers"
											checked="checked" value="1"> <m:print
												key="lbl.include" /> TRIGGERS</label>
									</div>
									<div>
										<label><input type="checkbox" name="add_drop_db"
											value="1"> <m:print key="lbl.include" /> Drop
											DATABASE Statements</label>
									</div>
									<div>
										<label><input type="checkbox" name="add_drop_table"
											value="1"> <m:print key="lbl.include" /> Drop
											TABLE/VIEW / PROCEDURE / FUNCTION / EVENT / TRIGGER
											Statements</label>
									</div>
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-run">
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
		$("#header-menu li:nth-child(5)").addClass('active');
		$(function() {
			$('#btn-run').click(function() {
				if ($('#databases').val().length > 0) {
					$('#export-form').submit();
				} else {
					$('#sidebar-error-msg').text('Please Select at least one database to export.');
					$('#sidebar-error-dialog').show();
				}
			});
		});
	</script>
</body>
</html>