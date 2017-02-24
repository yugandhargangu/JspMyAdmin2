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
					page="../../server/Header.jsp" /></div>
			<div id="main-body">
				<div style="padding: 0.2em 2em;">
					<div class="page-head">
						<h3>
							<m:print key="lbl.schema_privileges" />
							&#40;${requestScope.command.user}&#41;
							<button type="button" class="btn" id="btn-back"
								style="float: right;">
								<m:print key="lbl.go_back" />
							</button>
						</h3>
					</div>
					<jma:notEmpty name="#schema_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/server_schema_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.add_new_entry" />
								</div>
								<div class="group-widget group-content">
									<div class="form-input" style="width: 250px;">
										<label><m:print key="lbl.select_database" /> </label> <select
											class="form-control" name="database">
											<jma:notEmpty name="#schema_list" scope="command">
												<jma:forLoop items="#schema_list" name="schema"
													scope="command">
													<option value="${schema}">${schema}</option>
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
					<jma:notEmpty name="#privilege_list" scope="command">
						<div class="group">
							<div class="group-widget group-header">
								<m:print key="lbl.schema_privileges" />
							</div>
							<div class="group-widget group-content">

								<jma:notEmpty name="#privilege_list" scope="command">
									<jma:forLoop items="#privilege_list" name="info"
										scope="command">
										<form
											action="${pageContext.request.contextPath}/server_schema_privileges.html"
											method="post" accept-charset="utf-8">
											<input type="hidden" name="token"
												value="${requestScope.command.token}"> <input
												type="hidden" name="user"
												value="${requestScope.command.user}"> <input
												type="hidden" name="database" value="${info.schema}">
											<div class="group">
												<div class="group-widget group-header">${info.schema}</div>
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
														<m:print key="lbl.select_all" />
													</button>
													<button type="button" class="btn btn-unselect-all">
														<m:print key="lbl.unselect_all" />
													</button>
													<button type="button" class="btn btn-delete">
														<m:print key="lbl.delete_entry" />
													</button>
													<button type="submit" class="btn btn-run">
														<m:print key="lbl.run" />
													</button>
												</div>
											</div>
										</form>
									</jma:forLoop>
								</jma:notEmpty>
							</div>
						</div>
					</jma:notEmpty>
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
					<m:print key="msg.user_privilege_delete_alert" />
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
	<form action="${pageContext.request.contextPath}/server_users.html"
		method="get" id="back-form"></form>
	<script type="text/javascript">
		$("#header-menu li:nth-child(4)").addClass('active');
		applyEvenOdd('#privilege-list');
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
			$('#btn-back').click(function() {
				showWaiting();
				$('#back-form').submit();
			});
		});
	</script>
</body>
</html>
