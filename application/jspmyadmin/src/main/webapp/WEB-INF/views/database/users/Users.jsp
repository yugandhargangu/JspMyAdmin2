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
	width: 200px;
	vertical-align: top;
	margin-top: 0.5em;
}

.div-inline-item h4 {
	padding-bottom: 1em;
}

.div-inline-item div {
	padding-left: 1em;
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
						</h3>
					</div>
					<jma:notEmpty name="#error" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.mysql_error" />
								:
							</div>
							<div class="group-widget group-normal">
								<p style="color: red;">
									<m:print key="err.user_not_have_access" />
								</p>
							</div>
						</div>
					</jma:notEmpty>
					<jma:notEmpty name="#user_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="token"
								value="${requestScope.command.token}">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.assign_rights_user" />
								</div>
								<div class="group-widget group-content">
									<div class="form-input" style="width: 250px;">
										<label>Select User</label> <select class="form-control"
											id="user-select" name="user">
											<jma:notEmpty name="#user_list" scope="command">
												<jma:forLoop items="#user_list" name="user" scope="command">
													<option value="${user}">${user}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="div-inline-item">
										<h4>
											<input type="checkbox" class="obj-check-all">
											<m:print key="lbl.object_rights" />
										</h4>
										<jma:forLoop items="#privilege_obj_list" name="obj_priv"
											scope="command" index="_obj_priv_index">
											<div>
												<input type="checkbox" name="privileges" value="${obj_priv}">
												<label>${obj_priv}</label>
											</div>
										</jma:forLoop>
									</div>
									<div class="div-inline-item">
										<h4>
											<input type="checkbox" class="ddl-check-all">
											<m:print key="lbl.ddl_rights" />
										</h4>
										<jma:forLoop items="#privilege_ddl_list" name="ddl_priv"
											scope="command" index="_ddl_priv_index">
											<div>
												<input type="checkbox" name="privileges" value="${ddl_priv}">
												<label>${ddl_priv}</label>
											</div>
										</jma:forLoop>
									</div>
									<div class="div-inline-item">
										<h4>
											<input type="checkbox" class="other-check-all">
											<m:print key="lbl.other_rights" />
										</h4>
										<jma:forLoop items="#privilege_admn_list" name="other_priv"
											scope="command" index="_other_priv_index">
											<div>
												<input type="checkbox" name="privileges"
													value="${other_priv}"> <label>${other_priv}</label>
											</div>
										</jma:forLoop>
									</div>
								</div>
								<div class="group-widget group-footer">
									<button type="button" class="btn btn-go-table"
										onclick="callPostForm();">
										<m:print key="lbl.go_table_privileges" />
									</button>
									<button type="button" class="btn btn-go-table"
										onclick="callPostFormR();">
										<m:print key="lbl.go_procudere_function_privileges" />
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
					<div class="group">
						<div class="group-widget group-header">
							<m:print key="lbl.users_privileges" />
						</div>
						<div class="group-widget group-content">
							<jma:notEmpty name="#user_info_list" scope="command">
								<jma:forLoop items="#user_info_list" name="info" scope="command">
									<form
										action="${pageContext.request.contextPath}/database_privileges.html"
										method="post" accept-charset="utf-8">
										<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
										<input type="hidden" name="token"
											value="${requestScope.command.token}"> <input
											type="hidden" name="user" value="${info.user}">
										<div class="group">
											<div class="group-widget group-header">${info.user}
												<jma:switch name="#info.type" scope="page">
													<jma:case value="1"></jma:case>
													<jma:case value="2"></jma:case>
													<jma:case value="3"></jma:case>
													<jma:default> - <m:print
															key="lbl.database_specific_user" />
													</jma:default>
												</jma:switch>
											</div>
											<jma:switch name="#info.type" scope="page">
												<jma:case value="1">
													<div class="group-widget group-content">
														<m:print key="lbl.global_privileged_user" />
													</div>
												</jma:case>
												<jma:case value="2">
													<div class="group-widget group-content">
														<m:print key="lbl.table_privileged_user" />
													</div>
													<div class="group-widget group-footer">
														<button type="button" class="btn btn-go-priv"
															onclick="callGetForm('${info.token}')">
															<m:print key="lbl.go_table_privileges" />
														</button>
														<button type="button" class="btn btn-go-priv"
															onclick="callGetFormR('${info.token}')">
															<m:print key="lbl.go_procudere_function_privileges" />
														</button>
													</div>
												</jma:case>
												<jma:case value="3">
													<div class="group-widget group-content">
														<m:print key="lbl.column_privileged_user" />
													</div>
													<div class="group-widget group-footer">
														<button type="button" class="btn btn-go-priv"
															onclick="callGetForm('${info.token}')">
															<m:print key="lbl.go_table_privileges" />
														</button>
														<button type="button" class="btn btn-go-priv"
															onclick="callGetFormR('${info.token}')">
															<m:print key="lbl.go_procudere_function_privileges" />
														</button>
													</div>
												</jma:case>
												<jma:default>
													<div class="group-widget group-content">
														<div class="div-inline-item">
															<h4>
																<input type="checkbox" class="obj-check-all">
																<m:print key="lbl.object_rights" />
															</h4>
															<jma:forLoop items="#privilege_obj_list" name="obj_priv"
																scope="command" index="_obj_priv_index">
																<div>
																	<jma:fetch index="#_obj_priv_index"
																		name="obj_rights_val" key="obj_rights_val"
																		object="info" scope="page" />
																	<jma:switch name="#obj_rights_val" scope="page">
																		<jma:case value="1">
																			<input type="checkbox" name="privileges"
																				value="${obj_priv}" checked="checked">
																		</jma:case>
																		<jma:default>
																			<input type="checkbox" name="privileges"
																				value="${obj_priv}">
																		</jma:default>

																	</jma:switch>
																	<label>${obj_priv}</label>
																</div>
															</jma:forLoop>
														</div>
														<div class="div-inline-item">
															<h4>
																<input type="checkbox" class="ddl-check-all">
																<m:print key="lbl.ddl_rights" />
															</h4>
															<jma:forLoop items="#privilege_ddl_list" name="ddl_priv"
																scope="command" index="_ddl_priv_index">
																<div>
																	<jma:fetch index="#_ddl_priv_index"
																		name="ddl_rights_val" key="ddl_rights_val"
																		object="info" scope="page" />
																	<jma:switch name="#ddl_rights_val" scope="page">
																		<jma:case value="1">
																			<input type="checkbox" name="privileges"
																				value="${ddl_priv}" checked="checked">
																		</jma:case>
																		<jma:default>
																			<input type="checkbox" name="privileges"
																				value="${ddl_priv}">
																		</jma:default>

																	</jma:switch>
																	<label>${ddl_priv}</label>
																</div>
															</jma:forLoop>
														</div>
														<div class="div-inline-item">
															<h4>
																<input type="checkbox" class="other-check-all">
																<m:print key="lbl.other_rights" />
															</h4>
															<jma:forLoop items="#privilege_admn_list"
																name="other_priv" scope="command"
																index="_other_priv_index">
																<div>
																	<jma:fetch index="#_other_priv_index"
																		name="other_rights_val" key="other_rights_val"
																		object="info" scope="page" />
																	<jma:switch name="#other_rights_val" scope="page">
																		<jma:case value="1">
																			<input type="checkbox" name="privileges"
																				value="${other_priv}" checked="checked">
																		</jma:case>
																		<jma:default>
																			<input type="checkbox" name="privileges"
																				value="${other_priv}">
																		</jma:default>

																	</jma:switch>
																	<label>${other_priv}</label>
																</div>
															</jma:forLoop>
														</div>
													</div>
													<div class="group-widget group-footer">
														<button type="button" class="btn btn-select-all">
															<m:print key="lbl.show_all" />
														</button>
														<button type="button" class="btn btn-unselect-all">
															<m:print key="lbl.unselect_all" />
														</button>
														<button type="button" class="btn btn-delete">
															<m:print key="lbl.revoke_all_privileges" />
														</button>
														<button type="submit" class="btn btn-run">
															<m:print key="lbl.run" />
														</button>
													</div>
												</jma:default>
											</jma:switch>
										</div>
									</form>
								</jma:forLoop>
							</jma:notEmpty>
						</div>
					</div>
				</div>
			</div>
			<div id="footer">
				<jsp:include page="../../includes/Footer.jsp" />
			</div>
		</div>
	</div>
	<div class="dialog" id="confirm-dialog" style="display: none;">
		<div class="dialog-widget dialog-normal">
			<div class="close" id="confirm-close">&#10005;</div>
			<div class="dialog-header">
				<m:print key="lbl.alert" />
			</div>
			<div class="dialog-content">
				<p id="confirm-content">
					<m:print key="msg.revoke_all_db_permission_alert" />
				</p>
			</div>
			<div class="dialog-footer">
				<button type="button" class="btn" id="yes_btn">
					<m:print key="lbl.yes" />
				</button>
				<button type="button" class="btn" id="no_btn">
					<m:print key="lbl.no" />
				</button>
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
		<form
			action="${pageContext.request.contextPath}/database_table_privileges.html"
			method="get" id="table-form-get">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token">
		</form>
		<form
			action="${pageContext.request.contextPath}/database_table_privileges.html"
			method="post" id="table-form-post">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token"
				value="${requestScope.command.token}"> <input type="hidden"
				name="user"> <input type="hidden" name="fetch" value="2">
		</form>
		<form
			action="${pageContext.request.contextPath}/database_routine_privileges.html"
			method="get" id="routine-form-get">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token">
		</form>
		<form
			action="${pageContext.request.contextPath}/database_routine_privileges.html"
			method="post" id="routine-form-post">
			<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
			<input type="hidden" name="token"
				value="${requestScope.command.token}"> <input type="hidden"
				name="user">
		</form>

	</div>
	<script type="text/javascript">
		$("#header-menu li:nth-child(10)").addClass('active');

		function callGetForm(token) {
			$('#table-form-get').find('input[name="token"]').val(token);
			$('#table-form-get').submit();
		}

		function callPostForm() {
			$('#table-form-post').find('input[name="user"]').val($('#user-select').val());
			$('#table-form-post').submit();
		}

		function callGetFormR(token) {
			$('#routine-form-get').find('input[name="token"]').val(token);
			$('#routine-form-get').submit();
		}

		function callPostFormR() {
			$('#routine-form-post').find('input[name="user"]').val($('#user-select').val());
			$('#routine-form-post').submit();
		}

		var formElement;
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

			$('.obj-check-all,.ddl-check-all,.other-check-all').change(function() {
				$(this).parent().parent().find('input[name="privileges"]').prop('checked', $(this).is(':checked'));
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

			$('#yes_btn').click(function() {
				$(formElement).find('input[name="privileges"]').prop('checked', false);
				$(formElement).submit();
			});

			$('#confirm-close').click(function() {
				$('#confirm-dialog').hide();
			});

			$('#no_btn').click(function() {
				$('#confirm-dialog').hide();
			});

			$('.btn-delete').click(function() {
				formElement = $(this).parent().parent().parent();
				$('#confirm-dialog').show();
			});
		});
	</script>
</body>
</html>
