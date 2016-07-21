<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<style type="text/css">
html {
	font-size: ${sessionScope.fontsize}%;
}
</style>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<link rel="icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/components/images/favicon.ico">
<title><m:print key="title" /></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/components/jma/jspmyadmin.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/components/jma/jquery.js"></script>
</head>
<body>
	<div style="width: 100%; text-align: center; margin-top: 50px;">
		<img alt="Logo" height="80"
			src="${pageContext.request.contextPath}/components/images/jspmyadmin.jpg">
	</div>
	<h2 align="center">
		<m:print key="lbl.welcome_to_jspmyadmin" />
	</h2>
	<div class="group" style="width: 400px;">
		<div class="group-widget group-header">
			<h3>
				<m:print key="lbl.language" />
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/language.html"
			id="language-form" method="post" accept-charset="UTF-8">
			<input type="hidden" name="token"
				value="${requestScope.command.token}"> <input type="hidden"
				name="url" value="${pageContext.request.requestURL}">
			<div class="group-widget group-content">
				<select name="language" id="language" style="width: 90%;">
					<option value=""><m:print key="lbl.select_language" /></option>
					<option value="en">English</option>
				</select>
			</div>
		</form>
	</div>
	<div class="group" style="width: 400px;">
		<div class="group-widget group-header">
			<h3>
				<m:print key="lbl.login" />
			</h3>
		</div>
		<form action="${pageContext.request.contextPath}/login.html"
			method="post" id="login-form" accept-charset="UTF-8">
			<input type="hidden" name="token"
				value="${requestScope.command.token}">
			<div class="group-widget group-content">
				<div class="form-input">
					<label><m:print key="lbl.hostname" /></label> <input type="text"
						name="hostname" id="hostname" class="form-control" maxlength="50"
						value="${requestScope.command.hostname}">
				</div>
				<div class="form-input">
					<label><m:print key="lbl.port" /></label> <input type="text"
						name="portnumber" id="portnumber" class="form-control"
						maxlength="50" value="${requestScope.command.portnumber}">
				</div>
				<div class="form-input">
					<label><m:print key="lbl.user" /></label> <input type="text"
						name="username" id="username" class="form-control" maxlength="50"
						value="${requestScope.command.username}">
				</div>
				<div class="form-input">
					<label><m:print key="lbl.password" /></label> <input
						type="password" name="password" maxlength="50" id="password"
						class="form-control">
				</div>
			</div>
			<div class="group-widget group-footer">
				<button type="button" class="btn" id="go_btn">
					<m:print key="btn.go" />
				</button>
			</div>
		</form>
	</div>

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
	<m:store name="err_hostname_is_blank" key="err.hostname_is_blank" />
	<m:store name="err_port_is_blank" key="err.port_is_blank" />
	<m:store name="err_user_is_blank" key="err.user_is_blank" />
	<script type="text/javascript">
		$(function() {
			$('#go_btn').click(function() {
				var isValid = true;
				if ($('#hostname').val().trim() == '') {
					$('#error-content').text('${err_hostname_is_blank}');
					$('#error-dialog').show();
					isValid = false;
				}
				if ($('#portnumber').val().trim() == '') {
					$('#error-content').text('${err_port_is_blank}');
					$('#error-dialog').show();
					isValid = false;
				}
				if ($('#username').val().trim() == '') {
					$('#error-content').text('${err_user_is_blank}');
					$('#error-dialog').show();
					isValid = false;
				}
				if (isValid) {
					$('#login-form').submit();
				}
			});

			$('body').keypress(function(e) {
				if (e.which == 13) {
					$('#go_btn').click();
				}
			});
			$('#error-close').click(function() {
				$('#error-dialog').hide();
			});
			$('#language').change(function() {
				$('#language-form').submit();
			});
			$('#error-close1').click(function() {
				$(this).parent().parent().empty().remove();
			});
		});
	</script>
</body>
</html>