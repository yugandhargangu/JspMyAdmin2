<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../includes/Head.jsp" />
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
							User Information
							<button type="button" class="btn" id="btn-back"
								style="float: right;">Go Back</button>
						</h3>
					</div>
					<form
						action="${pageContext.request.contextPath}/server_user_info.html"
						id="user-form" method="post" accept-charset="utf-8">
						<input type="hidden" name="token"
							value="${requestScope.command.token}"> <input
							type="hidden" name="user" value="${requestScope.command.user}">
						<input type="hidden" name="old_user"
							value="${requestScope.command.old_user}"> <input
							type="hidden" name="old_host"
							value="${requestScope.command.old_host}">
						<div class="group">
							<div class="group-widget group-header">Login Information</div>
							<div class="group-widget group-content">
								<div class="form-input">
									<label>Login Name</label> <input type="text"
										class="form-control" name="login_name" id="login-name"
										value="${requestScope.command.login_name}">
								</div>
								<div class="form-input">
									<label>Host Name</label> <input type="text"
										class="form-control" name="host_name" id="host-name"
										value="${requestScope.command.host_name}">
								</div>
								<div class="form-input">
									<label>Password</label> <input type="password"
										class="form-control" name="password"
										value="${requestScope.command.password}">
								</div>
								<div class="form-input">
									<label>Confirm Password</label> <input type="password"
										class="form-control" name="password_confirm"
										value="${requestScope.command.password_confirm}">
								</div>
							</div>
							<div class="group-widget group-footer">
								<button type="button" class="btn" id="btn-run">Run</button>
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
	<div class="dialog" id="error-dialog" style="display: none;">
		<div class="dialog-widget dialog-error">
			<div class="close" id="error-close">&#10005;</div>
			<div class="dialog-header">
				<m:print key="lbl.errors" />
			</div>
			<div class="dialog-content">
				<p id="error-content"></p>
			</div>
		</div>
	</div>
	<form action="${pageContext.request.contextPath}/server_users.html"
		method="get" id="back-form"></form>
	<script type="text/javascript">
		$("#header-menu li:nth-child(4)").addClass('active');

		$(function() {

			$('#error-close2').click(function() {
				$(this).parent().parent().empty().remove();
			});

			$('#btn-run').click(function() {
				if ($('#login-name').val().trim() == '') {
					$('#error-content').text('Login Name is Blank.');
					$('#error-dialog').show();
					return;
				}
				if ($('#host-name').val().trim() == '') {
					$('#error-content').text('Host Name is Blank.');
					$('#error-dialog').show();
					return;
				}
				$('#user-form').submit();
			});
			$('#btn-back').click(function() {
				showWaiting();
				$('#back-form').submit();
			});
			$('#error-close').click(function() {
				$('#error-content').text('');
				$('#error-dialog').hide();
			});
		});
	</script>
</body>
</html>
