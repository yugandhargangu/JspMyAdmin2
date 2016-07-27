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
.div-inline-item {
	display: inline-block;
	vertical-align: top;
	margin-top: 0.5em;
	width: calc(100% - 260px);
}

.div-inline-item h4 {
	padding-bottom: 1em;
}

.div-item {
	display: inline-block;
	vertical-align: top;
	margin: 0.2em;
	width: 200px;
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
			<div id="header-div"><jsp:include
					page="../../database/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.users_privileges" />
							(${requestScope.command.user})
							<button type="button" class="btn" id="btn-back"
								style="float: right;">
								<m:print key="lbl.go_back" />
							</button>
						</h3>
					</div>
					<jma:notEmpty name="#column_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_column_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<input type="hidden" name="table"
								value="${requestScope.command.table}"><input
								type="hidden" name="fetch" value="1">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.grant_rights_user" />
									(${requestScope.command.table})
								</div>
								<div class="group-widget group-content">
									<div class="form-input">
										<label>SELECT</label> <select class="form-control"
											name="select_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#column_list" scope="command">
												<jma:forLoop items="#column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>INSERT</label> <select class="form-control"
											name="insert_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#column_list" scope="command">
												<jma:forLoop items="#column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>UPDATE</label> <select class="form-control"
											name="update_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#column_list" scope="command">
												<jma:forLoop items="#column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>REFERENCES</label> <select class="form-control"
											name="reference_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#column_list" scope="command">
												<jma:forLoop items="#column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
								</div>
								<div class="group-widget group-footer">
									<button type="submit" class="btn btn-grant">
										<m:print key="lbl.grant" />
									</button>
								</div>
							</div>
						</form>
					</jma:notEmpty>

					<jma:notEmpty name="#column_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_column_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<input type="hidden" name="table"
								value="${requestScope.command.table}"><input
								type="hidden" name="fetch" value="2">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.revoke_rights_user" />
									(${requestScope.command.table})
								</div>
								<div class="group-widget group-content">
									<div class="form-input">
										<label>SELECT</label> <select class="form-control"
											name="select_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#select_column_list" scope="command">
												<jma:forLoop items="#select_column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>INSERT</label> <select class="form-control"
											name="insert_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#insert_column_list" scope="command">
												<jma:forLoop items="#insert_column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>UPDATE</label> <select class="form-control"
											name="update_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#update_column_list" scope="command">
												<jma:forLoop items="#update_column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="form-input">
										<label>REFERENCES</label> <select class="form-control"
											name="reference_columns" multiple="multiple" size="15">
											<jma:notEmpty name="#reference_column_list" scope="command">
												<jma:forLoop items="#reference_column_list" name="column"
													scope="command">
													<option value="${column}">${column}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
								</div>
								<div class="group-widget group-footer">
									<button type="submit" class="btn btn-revoke">
										<m:print key="lbl.revoke" />
									</button>
								</div>
							</div>
						</form>
					</jma:notEmpty>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>
	<jma:notEmpty name="#err" scope="command">
		<div class="dialog">
			<div class="dialog-widget dialog-error">
				<div class="close" id="error-close2">&#10005;</div>
				<div class="dialog-header">
					<m:print key="lbl.errors" />
				</div>
				<div class="dialog-content">
					<p>${requestScope.command.err}</p>
				</div>
			</div>
		</div>
	</jma:notEmpty>
	<jma:notEmpty name="#err_key" scope="command">
		<div class="dialog">
			<div class="dialog-widget dialog-error">
				<div class="close" id="error-close1">&#10005;</div>
				<div class="dialog-header">
					<m:print key="lbl.errors" />
				</div>
				<div class="dialog-content">
					<p>
						<m:print key="err_key" scope="command" />
					</p>
				</div>
			</div>
		</div>
	</jma:notEmpty>
	<div style="display: none;">
		<form method="get" accept-charset="utf-8" id="back-form"
			action="${pageContext.request.contextPath}/database_table_privileges.html">
			<input type="hidden" name="token"
				value="${requestScope.command.table_token}">
		</form>
	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(10)").addClass('active');

		$(function() {

			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#error-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#btn-back').click(function() {
				$('#back-form').submit();
			});
		});
	</script>
</body>
</html>
