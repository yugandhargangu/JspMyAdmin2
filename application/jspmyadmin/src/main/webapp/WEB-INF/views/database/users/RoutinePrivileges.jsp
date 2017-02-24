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
	vertical-align: top;
	margin: 0.2em;
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
					<jma:notEmpty name="#procedure_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_routine_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<input type="hidden" name="fetch">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.assign_rights_user" />
								</div>
								<div class="group-widget group-content">
									<div class="form-input" style="width: 250px;">
										<label><m:print key="lbl.select_procedures" /> </label> <select
											class="form-control" name="procedures" multiple="multiple"
											size="15">
											<jma:notEmpty name="#procedure_list" scope="command">
												<jma:forLoop items="#procedure_list" name="procedure"
													scope="command">
													<option value="${procedure}">${procedure}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="div-inline-item">
										<h4>
											<m:print key="lbl.privileges" />
										</h4>
										<jma:forLoop items="#privilege_routine_list" name="obj_priv"
											scope="command" index="_obj_priv_index">
											<div class="div-item">
												<label><input type="checkbox"
													name="procedure_privileges" value="${obj_priv}">
													${obj_priv}</label>
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
									<button type="button" class="btn btn-grant">
										<m:print key="lbl.grant" />
									</button>
									<button type="button" class="btn btn-revoke">
										<m:print key="lbl.revoke" />
									</button>
								</div>
							</div>
						</form>
					</jma:notEmpty>
					<jma:notEmpty name="#function_list" scope="command">
						<form
							action="${pageContext.request.contextPath}/database_routine_privileges.html"
							method="post" accept-charset="utf-8">
							<input type="hidden" name="request_db" value="${requestScope.command.request_db}">
							<input type="hidden" name="token"
								value="${requestScope.command.token}"> <input
								type="hidden" name="user" value="${requestScope.command.user}">
							<input type="hidden" name="fetch">
							<div class="group">
								<div class="group-widget group-header">
									<m:print key="lbl.assign_rights_user" />
								</div>
								<div class="group-widget group-content">
									<div class="form-input" style="width: 250px;">
										<label><m:print key="lbl.select_functions" /> </label> <select
											class="form-control" name="functions" multiple="multiple"
											size="15">
											<jma:notEmpty name="#function_list" scope="command">
												<jma:forLoop items="#function_list" name="function"
													scope="command">
													<option value="${function}">${function}</option>
												</jma:forLoop>
											</jma:notEmpty>
										</select>
									</div>
									<div class="div-inline-item">
										<h4>
											<m:print key="lbl.privileges" />
										</h4>
										<jma:forLoop items="#privilege_routine_list" name="obj_priv"
											scope="command" index="_obj_priv_index">
											<div class="div-item">
												<label><input type="checkbox"
													name="function_privileges" value="${obj_priv}">
													${obj_priv}</label>
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
									<button type="button" class="btn btn-grant">
										<m:print key="lbl.grant" />
									</button>
									<button type="button" class="btn btn-revoke">
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

			$('.btn-select-all').click(function() {
				$(this).parent().parent().find('input[name="procedure_privileges"]').prop('checked', true);
				$(this).parent().parent().find('input[name="function_privileges"]').prop('checked', true);
			});

			$('.btn-unselect-all').click(function() {
				$(this).parent().parent().find('input[name="procedure_privileges"]').prop('checked', false);
				$(this).parent().parent().find('input[name="function_privileges"]').prop('checked', true);
			});

			$('.btn-grant').click(function() {
				$('input[name="fetch"]').val('1');
				$(this).prop('type', 'submit');
				$(this).click();
			});

			$('.btn-revoke').click(function() {
				$('input[name="fetch"]').val('2');
				$(this).prop('type', 'submit');
				$(this).click();
			});

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
