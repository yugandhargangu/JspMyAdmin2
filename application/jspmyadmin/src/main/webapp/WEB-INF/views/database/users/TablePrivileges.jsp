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
					<jma:notEmpty name="#table_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_table_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.assign_rights_user" />
								</div>
								<div class="group-widget group-content">
									<div class="form-input" style="width: 250px;">
										<label><m:print key="lbl.select_tables" /> </label> <select
											class="form-control" name="tables" multiple="multiple"
											size="20">
											<jma:notEmpty name="#table_list" scope="command">
												<jma:forLoop items="#table_list" name="table"
													scope="command">
													<option value="${table}">${table}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="div-inline-item">
										<h4>
											<m:print key="lbl.privileges" />
										</h4>
										<jma:forLoop items="#privilege_table_list" name="obj_priv"
											scope="command" index="_obj_priv_index">
											<div class="div-item">
												<label><input type="checkbox" name="privileges"
													value="${obj_priv}"> ${obj_priv}</label>
											</div>
										</jma:forLoop>
									</div>
								</div>
								<div class="group-widget group-footer">
									<button type="button" class="btn btn-select-all">
										<m:print key="lbl.select_all" />
									</button>
									<button type="button" class="btn btn-unselect-all">
										<m:print key="lbl.unselect_all" />
									</button>
									<button type="submit" class="btn btn-run">
										<m:print key="lbl.run" />
									</button>
								</div>
							</div>
						</form>
					</jma:notEmpty>
					<jma:notEmpty name="#table_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_table_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.assign_rights_user" />
								</div>
								<div class="group-widget group-content">
									<div class="form-input" style="width: 250px;">
										<label><m:print key="lbl.select_table" /> </label> <select
											class="form-control" id="table-single-select" name="table">
											<jma:notEmpty name="#table_list" scope="command">
												<jma:forLoop items="#table_list" name="table"
													scope="command">
													<jma:switch name="#table" scope="command">
														<jma:case value="#table" scope="page">
															<option value="${table}" selected="selected">${table}</option>
														</jma:case>
														<jma:default>
															<option value="${table}">${table}</option>
														</jma:default>
													</jma:switch>

												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="div-inline-item">
										<h4>
											<m:print key="lbl.privileges" />
										</h4>
										<jma:forLoop items="#privilege_table_list" name="obj_priv"
											scope="command" index="_obj_priv_index">
											<div class="div-item">
												<label><jma:fetch index="#_obj_priv_index"
														name="obj_rights_val" key="obj_rights_val" object="info"
														scope="command" /> <jma:switch name="#obj_rights_val"
														scope="page">
														<jma:case value="1">
															<input type="checkbox" name="privileges"
																value="${obj_priv}" checked="checked">
														</jma:case>
														<jma:default>
															<input type="checkbox" name="privileges"
																value="${obj_priv}">
														</jma:default>

													</jma:switch> ${obj_priv}</label>
											</div>
										</jma:forLoop>
									</div>
								</div>
								<div class="group-widget group-footer">
									<button type="button" class="btn"
										onclick="callColumnForm('${requestScope.command.column_token}')">
										<m:print key="lbl.column_privileges" />
									</button>
									<button type="button" class="btn btn-select-all">
										<m:print key="lbl.select_all" />
									</button>
									<button type="button" class="btn btn-unselect-all">
										<m:print key="lbl.unselect_all" />
									</button>
									<button type="submit" class="btn btn-run">
										<m:print key="lbl.run" />
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
		<form method="post" accept-charset="utf-8" id="fetch-form"
			action="${pageContext.request.contextPath}/database_table_privileges.html">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token"
				value="${requestScope.command.token}"> <input type="hidden"
				name="user" value="${requestScope.command.user}"> <input
				type="hidden" name="fetch" value="1"> <input type="hidden"
				name="table">
		</form>
		<form method="get" accept-charset="utf-8" id="column-form"
			action="${pageContext.request.contextPath}/database_column_privileges.html">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token">
		</form>
		<form method="get" accept-charset="utf-8" id="back-form"
			action="${pageContext.request.contextPath}/database_privileges.html">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
		</form>
	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(10)").addClass('active');

		function callColumnForm(token) {
			$('#column-form').find('input[name="token"]').val(token);
			$('#column-form').submit();
		}

		$(function() {

			$('.div-inline-item').each(function() {
				var length1 = $(this).find('input[name="privileges"]').length;
				var length2 = $(this).find('input[name="privileges"]:checked').length;
				if (length2 != 0 && length1 == length2) {
					$(this).find('h4').find('input[type="checkbox"]').prop('indeterminate', false);
					$(this).find('h4').find('input[type="checkbox"]').prop('checked', true);
				} else if (length2 != 0 && length1 != length2) {
					$(this).find('h4').find('input[type="checkbox"]').prop('indeterminate', true);
				} else {
					$(this).find('h4').find('input[type="checkbox"]').prop('indeterminate', false);
					$(this).find('h4').find('input[type="checkbox"]').prop('checked', false);
				}
			});

			$('.btn-select-all').click(function() {
				$('.div-inline-item').find('h4').find('input[type="checkbox"]').prop('indeterminate', false);
				$('.div-inline-item').find('h4').find('input[type="checkbox"]').prop('checked', true);
				$(this).parent().parent().find('input[name="privileges"]').prop('checked', true);
			});

			$('.btn-unselect-all').click(function() {
				$('.div-inline-item').find('h4').find('input[type="checkbox"]').prop('indeterminate', false);
				$('.div-inline-item').find('h4').find('input[type="checkbox"]').prop('checked', false);
				$(this).parent().parent().find('input[name="privileges"]').prop('checked', false);
			});

			$('input[name="privileges"]').change(function() {
				var div = $(this).parent().parent();
				var length1 = $(div).find('input[name="privileges"]').length;
				var length2 = $(div).find('input[name="privileges"]:checked').length;
				if (length2 != 0 && length1 == length2) {
					$(div).find('h4').find('input[type="checkbox"]').prop('indeterminate', false);
					$(div).find('h4').find('input[type="checkbox"]').prop('checked', true);
				} else if (length2 != 0 && length1 != length2) {
					$(div).find('h4').find('input[type="checkbox"]').prop('indeterminate', true);
				} else {
					$(div).find('h4').find('input[type="checkbox"]').prop('indeterminate', false);
					$(div).find('h4').find('input[type="checkbox"]').prop('checked', false);
				}
			});

			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#error-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#table-single-select').change(function() {
				showWaiting();
				$('#fetch-form').find('input[name="table"]').val($(this).val());
				$('#fetch-form').submit();
			});
			$('#btn-back').click(function() {
				$('#back-form').submit();
			});
		});
	</script>
</body>
</html>
